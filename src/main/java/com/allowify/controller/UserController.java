package com.allowify.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import com.allowify.CloudSdpMessageException;
import com.allowify.config.MongoDBConfig;
import com.allowify.model.Code;
import com.allowify.model.Friend;
import com.allowify.model.GPlace;
import com.allowify.model.Gps;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.model.UserActivity;
import com.allowify.model.UserPoint;
import com.allowify.repository.CardRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.DeviceRepositoryCustom;
import com.allowify.repository.FriendRepositoryCustom;
import com.allowify.repository.GpsRepositoryCustom;
import com.allowify.repository.PointRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.UserDetailsResponse;
import com.allowify.response.UserResponse;
import com.zippi.exceptions.CustomUserAlreadyExistsException;
import com.zippi.exceptions.WrongPasswordException;
import com.zippi.places.GooglePlaces.Place;
import com.zippi.places.GooglePlaces.PlacesRequest;
import com.zippi.places.GooglePlaces.PlacesResponse;
import com.zippi.places.GooglePlaces.UserAccessRequest;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private final UserRepositoryCustom userRepository;
	
	@Autowired
	private final CodeRepositoryCustom codeRepository;
	
	@Autowired
	private final ShaPasswordEncoder passwordEncoder;
	
	@Autowired
	private PointRepositoryCustom pointRepositoryCustom;
	
	@Autowired
	private FriendRepositoryCustom friendRepositoryCustom;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;

	private String alertMessage = "";

	private String notificationMessage = "";

	private String alertTitle = "";
	
	private final static Logger logger = Logger.getLogger(UserController.class.getName()); 
	
	private Double distanceAllowed = 1000.0;
	
	private static final Integer USER_REFERENCE_POINTS = 50;
	
	private static final String OAUTH_ACCESS_TOKEN = "oauth_access_token";
	
	static int codeCounter=0;
	
	static int spclCounter=0;
	
	int totalPoints=0;
	
	@Autowired
	public UserController(UserRepositoryCustom userRepository,
			GpsRepositoryCustom gpsRepository, CardRepositoryCustom cardRepository,
			DeviceRepositoryCustom deviceRepository, CodeRepositoryCustom codeRepository, ShaPasswordEncoder passwordEncoder) {
		Assert.notNull(gpsRepository, "Repository must not be null!");
		Assert.notNull(userRepository, "Repository must not be null!");
		Assert.notNull(cardRepository, "Repository must not be null!");
		Assert.notNull(deviceRepository, "Repository must not be null!");		
		this.userRepository = userRepository;
		this.codeRepository = codeRepository;
		this.passwordEncoder = passwordEncoder;
	}
	

	

	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public User create(@RequestBody @Valid User user) throws CustomUserAlreadyExistsException {
				
		String encPassword = passwordEncoder.encodePassword(user.getNewPassword(), null);
		
		User requestingUser = userRepository.findByEmail(user.getEmail());
		
		if(requestingUser != null){
			throw new CustomUserAlreadyExistsException("User Already Existd with this Email address");
		}
		user.setPassword(encPassword);
		
		// TODO Update the role functionality  later
		user.setRole(1);
		
		User userObj = new User(user.getEmail(), user.getEmail(), user.getFirstName(),
				user.getLastName(), user.getPhoneNumber(), 
				user.getPassword(), user.getRole());
		
		userObj.setCreated(new Date());
		
		mongoOperations.save(userObj, "user");
		String putReferenceCode = generateReferencedCode(userObj);
		userObj.setReferenceCode(putReferenceCode);
		userObj.setAddressId(user.getAddressId());
		mongoOperations.save(userObj, "user");
		
		if(user.getReferenceCode() != null) {
			
			User checkUserIFHaveReferecedCode = 
					userRepository.findByReferenceCode(user.getReferenceCode());
			if(checkUserIFHaveReferecedCode != null) {
				UserPoint point = new UserPoint();
		
				point.setUserId(checkUserIFHaveReferecedCode.getId());
				point.setPoints(USER_REFERENCE_POINTS);
				point.setTransactionType(StatusConfiguration.CREDIT);
				point.setCreated(new Date());
				point.setUpdated(new Date());
				point.setReason(StatusConfiguration.REFERENCED);
			
				mongoOperations.save(point,"point");
			
			}
		
		}
				
		return userObj;
	}
	
	//@RolesAllowed({ "ROLE_USER" })	
	@RequestMapping(value = "/authorize", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public UserResponse getUser(@RequestBody @Valid UserAccessRequest user) {
		
		User requestingUser = userRepository.findByUserName(user.getUserName());
		
		String encPassword = passwordEncoder.encodePassword(user.getPassword(), null);
		
		if (requestingUser==null || requestingUser.getPassword() == null
				|| !requestingUser.getPassword().equals(encPassword)) {
			throw new UsernameNotFoundException("Wrong user");
		}
		return new UserResponse(requestingUser);
	}
	
	private String generateReferencedCode(User previousUser) {
		
		char[] charHolder = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
			
		String generateCode = previousUser.getId().toString();
	
		char[] generateCodeInChar = generateCode.toCharArray();
		char[] chrArray = new char[8];
		int midValue = generateCodeInChar.length /2;
		
		chrArray[0] = generateCodeInChar[0];
		chrArray[1] = generateCodeInChar[1];
		chrArray[2] = generateCodeInChar[midValue];
		chrArray[3] = generateCodeInChar[midValue+1];
		chrArray[4] = generateCodeInChar[(generateCodeInChar.length)-2];
		chrArray[5] = generateCodeInChar[(generateCodeInChar.length)-1];
		
		if(codeCounter == 10){
			codeCounter = 0;
		}
		char c = (char) (codeCounter);
		chrArray[6] = generateCodeInChar[c];
		codeCounter++;
		
		if(spclCounter == 36){
			spclCounter = 0;
		}
		chrArray[7] = charHolder[spclCounter];
		spclCounter++;
						
		return new String(chrArray);
		
	}
	
	
	@RequestMapping(value = "/codes/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Code> getUserCodes(@PathVariable(value = "userId") String userId) {
		
		List<Code> codes = codeRepository.findByUserId(userId);
		
		return codes;
	}
	
	
	@RequestMapping("/resetPassword")
	public @ResponseBody String resetPassword(
			@RequestParam("password") String password,
			@RequestParam("newPassword") String newPassword) {
		
		User user = loadUserFromSecurityContext();
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		String hashedOldPassword = getHashPassword(password);
		// Do null check for id
		if (user.getPassword().equals(hashedOldPassword)) {
			user.setPassword(getHashPassword(newPassword));
			mongoOperation.save(user, "user");
			return user.toString();

		} else {
			return null;
		}
	}

	/*
	 * This method is used to get all users list
	 */
	@RequestMapping("/getUsers")
	public @ResponseBody List<User> getUsers() {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		List<User> user = mongoOperation.findAll(User.class, "user");

		return user;
	}
	

	/*
	 * This method is used to put all code list
	 
	@RequestMapping("/putNewCodes")
	public @ResponseBody List<Code> putNewCodes() {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		int i, j, k,l,m;
		int checkCount = 0;
		char[] charArray={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		String[] codeArray = new String[500];
		
		List<Code> listArray=new ArrayList<Code>();
		Code code = new Code();
		
		for (i=0; i<charArray.length; i++)
		{
			for (j=0; j<charArray.length; j++)
			{
				for (k=0; k<charArray.length; k++){
					for(l=0;l<charArray.length;l++){
						for(m=0;m<charArray.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArray[i])+String.valueOf(charArray[j])
									+String.valueOf(charArray[k])
									+String.valueOf(charArray[l])+String.valueOf(charArray[m]);
							
							//System.out.println(codeGeneratesStr);
							
							if(checkCount < 500) {
								codeArray[checkCount] = codeGeneratesStr;
								code.setCode(codeGeneratesStr);
								listArray.add(code);
								mongoOperation.save(listArray.get(checkCount));
								checkCount++;
							}
							else{
								checkCount=0;
								listArray.clear();
								codeArray = new String[0];
								codeArray = new String[500];
																
							}
							
														
							count++;
						}
					}
					
				}
			}

		
		}
		System.out.println("counting ="+ count);
		

		return null;
	}
	

	

	 
	@RequestMapping(value="/getNewCodes",method =RequestMethod.GET)
	public @ResponseBody List<Code> getNewCodes() {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		List<Code> codeList = mongoOperation.findAll(Code.class, "code");

		return codeList;
		
	}*/
	
	 
	@RequestMapping(value="/deleteCodes",method =RequestMethod.DELETE)
	public @ResponseBody List<Code> deleteCodes() {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
				
		mongoOperation.findAllAndRemove(new Query(), Code.class);
		
		return null;
		
	}
	
	
	@RequestMapping(value="/putUserActivity")
	public @ResponseBody List<UserActivity> putUserActivity() {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		List<UserActivity> listUser = new ArrayList<UserActivity>();
		UserActivity userActivity = new UserActivity();
		userActivity.setTitle("Test");
		userActivity.setCreateDate(new Date());
		
		User user = new User();
		user.setUserName("a@gmail.com");
		user.setFirstName("test");
		mongoOperation.save(user);
		
		Code code = new Code();
		code.setCode("0001");
		code.setStatus(1);
		mongoOperation.save(code);
		
		userActivity.setUser(user);
		userActivity.setCode(code);
		
		listUser.add(userActivity);
		
		mongoOperation.save(userActivity);
		
		return listUser;
		
	}

	
	private double[] getDLatLong(double lat, double lon, double dn, double de, int direction){
		//Position, decimal degrees
		 
		 //Earth’s radius, sphere
		double R=6378137;

		 //offsets in meters
//		 dn = 100;
//		 de = 100;
		 System.out.print(dn+","+de+" = ");
		 double Pi =  3.14159;
		 
		 
		 //Coordinate offsets in radians
		 double dLat = dn/R;
		 double dLon = de/(R*Math.cos(Pi*lat/180));

		 //OffsetPosition, decimal degrees
		 double[] latLong = new double[2];
		 
		/* if(direction==1){
			 latLong[0] = lat + dLat * 180/Pi;
			 latLong[1] = lon + dLon * 180/Pi;
		 }else if(direction==2){
			 latLong[0] = lat + dLat * 180/Pi;
			 latLong[1] = lon - dLon * 180/Pi;
		 }else if(direction==3){
			 latLong[0] = lat - dLat * 180/Pi;
			 latLong[1] = lon - dLon * 180/Pi;
		 }else if(direction==4){
			 latLong[0] = lat - dLat * 180/Pi;
			 latLong[1] = lon + dLon * 180/Pi;
		 }*/
		 System.out.print(lat+","+dLat+" = ");
		 latLong[0] = lat + dLat * 180/Pi;
		 latLong[1] = lon + dLon * 180/Pi;
		 
		return latLong;
		
	}
	
	@RequestMapping(value = "/getPlaces", method = RequestMethod.POST )
	public @ResponseBody List<Place> getPlaces(@RequestBody PlacesRequest placesRequest){
		
		
		int noc = 3;
		 double[] latLong = new double[2];
		 
		 double radius2 = 2*Double.parseDouble(placesRequest.getRadius());
		 
		 latLong[0] = Double.parseDouble(placesRequest.getLat());
		 
		 latLong[1] = Double.parseDouble(placesRequest.getLng());
		 
		 List<Place> places = getNewPlaces(placesRequest, null);
		
		 boolean onetime = false;
		 
		for(int i=1; i<=noc; i++){
			
			latLong = getDLatLong(latLong[0], latLong[1], (onetime)?0:radius2, radius2, 1);
			placesRequest.setLat(String.valueOf(latLong[0]));
			placesRequest.setLng(String.valueOf(latLong[1]));
			getNewPlaces(placesRequest, null);
			onetime = true;
			
			int dir = 1;
			int dirFlag = 0;
			
	/*		System.out.print(dir);
			System.out.print("==");
			System.out.print(dirFlag);
			System.out.print("==");
			System.out.print(latLong[0]);
			System.out.print(',');
			System.out.print(latLong[1]);
			System.out.print(" = \n ");*/
			
			for(int j=1; j<(i*8); j++){
			
				//System.out.print((noc*8)/4);
				if(dirFlag==(i*8)/4){
					//System.out.print("in");
					dirFlag = 0;
					dir++;
				}
				
				dirFlag++;
				
				if(dirFlag<=((i*8)/4)/2){
					
					 if(dir==1){
						 latLong = getDLatLong(latLong[0], latLong[1], radius2, 0, dir);
					 }else if(dir==2){
						 latLong = getDLatLong(latLong[0], latLong[1], 0, -radius2, dir);
					 }else if(dir==3){
						 latLong = getDLatLong(latLong[0], latLong[1], -radius2, 0, dir);
					 }else if(dir==4){
						 latLong = getDLatLong(latLong[0], latLong[1], 0, radius2, dir);
					 }
				}else{
					
					 if(dir==1){
						 latLong = getDLatLong(latLong[0], latLong[1], 0, -radius2, dir);
					 }else if(dir==2){
						 latLong = getDLatLong(latLong[0], latLong[1], -radius2, 0, dir);
					 }else if(dir==3){
						 latLong = getDLatLong(latLong[0], latLong[1], 0, radius2, dir);
					 }else if(dir==4){
						 latLong = getDLatLong(latLong[0], latLong[1], radius2, 0, dir);
					 }
					 
				}
				
				placesRequest.setLat(String.valueOf(latLong[0]));
				placesRequest.setLng(String.valueOf(latLong[1]));
				getNewPlaces(placesRequest, null);
				
				System.out.print(dir);
				System.out.print("==");
				System.out.print(dirFlag);
				System.out.print("==");
				System.out.print(latLong[0]);
				System.out.print(',');
				System.out.print(latLong[1]);
				System.out.print(" = \n ");
			}
		}
		
		
		
		
		
		/*
RestTemplate restTemplate = new RestTemplate();
		
		//String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyA2ZYKqNUTIrFuCsC_qjsnMDAiNOxmMlrM&location=26.9918484,75.7778346&radius=5000&types=atm";
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyA2ZYKqNUTIrFuCsC_qjsnMDAiNOxmMlrM&location=" + placesRequest.getLat() +
				","+ placesRequest.getLng() +"&radius=" + placesRequest.getRadius() + "&types="+placesRequest.getTypes()+ "&pagetoken=CoQC-AAAAE6UPo_Cia7kQ1dOLPXf7ea11-7TG9VNV5QwBBeQ2xaRTGbNuH5_EyGmsZr24psrQBBDZhHuCk513O1LXbZAlDfKbXxyqNjDqGdohb6W9AN3kWwKBcyASGuEsDR161Worx1DHfHGpn4LoKaJEBqKQbP6nOQt2iAFlQuH05hAOtDmawiENd86Rxgtrr9tMZQyRhRWxPT7LFqli4odxpQvAuL9ktX-3iY-WBsVCEHrFGRjUIJrBbkxyAlY8zSwhaReYXLwoPc7T3KySm6z22YKvuGrhFKF-66YuDuhzMNreL46k5xJMY0npNLntwfPR2dShp0zSepT8sqLeF_x35ncIo0SEO27ScxtU0WubjkHwJsHStYaFPz8oxyhP_vj0zUG5GC9uPo5dkOj";
	
		System.out.print(url);
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
		messageConverter.setObjectMapper(mapper);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(messageConverter);
	
		restTemplate.setMessageConverters(messageConverters);
		
		PlacesResponse placesResponsea = restTemplate.getForObject(url, PlacesResponse.class);
		
		System.out.print(placesResponsea.getNext_page_token());
		*/
		
		
		
		return places;
		
	}
	
	private List<Place> getNewPlaces(PlacesRequest placesRequest, String nextPageToken){
		
		
		List<Place> placesList = new ArrayList<Place>();
//		List<GPlace> gPlacesList = new ArrayList<GPlace>();
	
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
			MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
			
		RestTemplate restTemplate = new RestTemplate();
		
		//String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyA2ZYKqNUTIrFuCsC_qjsnMDAiNOxmMlrM&location=26.9918484,75.7778346&radius=5000&types=atm";
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyA2ZYKqNUTIrFuCsC_qjsnMDAiNOxmMlrM&location=" + placesRequest.getLat() +
				","+ placesRequest.getLng() +"&radius=" + placesRequest.getRadius() + "&types="+placesRequest.getTypes();
	
		if(nextPageToken != null){
			url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyA2ZYKqNUTIrFuCsC_qjsnMDAiNOxmMlrM&location=" + placesRequest.getLat() +
					","+ placesRequest.getLng() +"&radius=" + placesRequest.getRadius() + "&types="+placesRequest.getTypes()+ "&pagetoken=" + nextPageToken ;
		
		}
		System.out.print(url);
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
		messageConverter.setObjectMapper(mapper);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(messageConverter);
	
		restTemplate.setMessageConverters(messageConverters);
		
		PlacesResponse placesResponse = restTemplate.getForObject(url, PlacesResponse.class);
		
		System.out.print(placesResponse.getNext_page_token());
		
		for( Place place : placesResponse.getResult()){
			placesList.add(place);
			
			mongoOperation.save(new GPlace(place.getName(), 
					place.getIcon(), place.getPlaceId(),
					place.getVicinity(),
					place.getGeometry().getLocation().getLat(),
					place.getGeometry().getLocation().getLng(), 
					place.getTypes()));
		}
		
		
		
		if(placesResponse.getNext_page_token() != null){
			
			try {
			    Thread.sleep(10000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			List<Place> newplaces = getNewPlaces(placesRequest, placesResponse.getNext_page_token());
			System.out.print("in");
			
			/*for( Place place : newplaces){
				placesList.add(place);
			}*/
			
		}
		
		return placesList;
	}
	/*
	 * This method is used to get all users list
	 */
	@RequestMapping("/putCodeass")
	public @ResponseBody void putCodes() {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		String[] database = {"A","B","C", "D", "E","F", "G", "H", "I", "J", 
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
				"0", "1","2", "3", "4", "5", "6","7", "8", "9"};
		
		String[] result = getAllLists(database, 5);
        for(int j=0; j<result.length; j++){
            System.out.println(result[j]);
        }
        System.out.println(result.length);
	    /*for(int i=1; i<=database.length; i++){
	        
	    }*/
		
	}
	
	public static String[] getAllLists(String[] elements, int lengthOfList)
	{
	    //initialize our returned list with the number of elements calculated above
	    String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];

	    //lists of length 1 are just the original elements
	    if(lengthOfList == 1) return elements; 
	    else {
	        //the recursion--get all lists of length 3, length 2, all the way up to 1
	        String[] allSublists = getAllLists(elements, lengthOfList - 1);

	        //append the sublists to each element
	        int arrayIndex = 0;

	        for(int i = 0; i < elements.length; i++){
	            for(int j = 0; j < allSublists.length; j++){
	                //add the newly appended combination to the list
	                allLists[arrayIndex] = elements[i] + allSublists[j];
	                arrayIndex++;
	            }
	        }
	        return allLists;
	    }
	}

	/*
	 * This method is used to get logged in User details by OAuth2
	 * Authentication
	 */
	@RolesAllowed({ "ROLE_USER" })	
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public UserResponse getUser() {
		User requestingUser = loadUserFromSecurityContext();
		if (requestingUser == null) {
			throw new UsernameNotFoundException("Wrong user");
		}
		return new UserResponse(requestingUser);
	}

	protected User loadUserFromSecurityContext()
			throws CloudSdpMessageException {
		
		Authentication principal = SecurityContextHolder.getContext()
				.getAuthentication();

		if (principal == null) {
			throw new CloudSdpMessageException("User is not authenticated.");
		}

		User user = null;
		if (principal instanceof User) {
			user = (User) principal;
		} else {
			user = userRepository.findByUserName(principal.getName());

		}

		return user;
	}

	/*
	 * This method is used to add user by GPS Location
	 */
	@RequestMapping(value = "addUserByGPS", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public Gps addUserByGPS(@Valid @RequestBody Gps gpsJsonObject) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		if (StringUtils.isEmpty(gpsJsonObject)) {
			return null;
		}

		Gps gpsObj = new Gps(gpsJsonObject.getUserName(), gpsJsonObject.getLocation(), gpsJsonObject.getTimestamp());
			
		//gpsObj.setUserName("abc@gmail.com");
		mongoOperation.save(gpsObj, "gps");

		return gpsObj;
	}
	
	/*
	 * This method is used to add user by GPS Location
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "near", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<Code> near(@RequestBody double[] latLong) {

		return codeRepository.findByLatLongNear(new Point(latLong[0],latLong[1]), new Distance(10));
	}
	
	
	@RequestMapping(value = "{userId}", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE } )
	@ResponseBody
	public User update(@RequestBody User user) {
		
		User preUser = userRepository.findOne(user.getId());
		
		if(user.getEmail()!=null)
		preUser.setEmail(user.getEmail());
		preUser.setUserName(preUser.getEmail());
		//preUser.setRole(1);
		//preUser.setCreated(preUser.getCreated());
		preUser.setUpdated(new Date());
		if(user.getFirstName()!=null)
		preUser.setFirstName(user.getFirstName());
		if(user.getLastName()!=null)
		preUser.setLastName(user.getLastName());
		if(user.getPhoneNumber()!=null)
		preUser.setPhoneNumber(user.getPhoneNumber());
		if(Boolean.getBoolean(String.valueOf(user.getStatus())))
		preUser.setStatus(user.getStatus());
		if(user.getProfilePicture()!=null)
		preUser.setProfilePicture(user.getProfilePicture());
		
		return userRepository.save(preUser);
		
	}
	
	/*
	 * This method is used to encode the password in hash code
	 * Spring recommends to use BCrypt BCryptPasswordEncoder, a stronger hashing algorithm with randomly generated salt.
	 */
	public String getHashPassword(String password){
		
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String hashPwd = passwordEncoder.encode(password);
    	
    	return hashPwd;
    }
	
	@RequestMapping("/testSecurity")
	public @ResponseBody String testSecurity() {
		System.out.println("Principal " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		System.out.println("Username " + ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserName());
		System.out.println("Name " + SecurityContextHolder.getContext().getAuthentication().getName());
		
//		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//		HttpServletRequest req = sra.getRequest();  
//		BearerTokenExtractor bte = new BearerTokenExtractor();
//		Authentication at = bte.extract(req);
//		System.out.println("Name: " + at.getName());
//		System.out.println("Principal " + at.getPrincipal());
//		
//		Principal principal = req.getUserPrincipal();
//		System.out.println("principal" + principal);
		
		
		return "security";
	}

	@RequestMapping("/updateNotificationPreference")
	public @ResponseBody String updateNotificationPreference(
			@RequestParam("notificationType") Integer notificationType) {

		User user = loadUserFromSecurityContext();

		if(user != null) {
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
			MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

			//user.setNotificationType(notificationType);
			mongoOperation.save(user, "user");
		}

		return "success";
	
	}
	
	@RequestMapping(value="/updatePassword",method =RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody void updateUserPassword (@RequestParam("oldPassword") String oldPassword,
										@RequestParam("newPassword") String newPassword,
										@RequestParam("userId") String userId) throws WrongPasswordException {
		
		User user = userRepository.findOne(userId);
		if(user.getId() != null) {
			
			String encPassword = passwordEncoder.encodePassword(oldPassword, null);
			if(user.getPassword().equals(encPassword)) {
				user.setPassword(passwordEncoder.encodePassword(newPassword, null));
				userRepository.save(user);
			
			} else {
				throw new WrongPasswordException("Password did not Match");
			}
		
		} else {
			throw new UsernameNotFoundException("User Not Found");
		}
		
	}
	
	@RequestMapping(value="/detail/{userId}/{anotherUserId}", method = RequestMethod.GET)
	@ResponseBody
	public UserDetailsResponse getUserDetails(@PathVariable("userId") String userId,
			@PathVariable("anotherUserId") String anotherUserId) {
		List<Friend> friend = friendRepositoryCustom.findByUserIdOrAnotherUserId(userId, anotherUserId);
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		
		if(!friend.isEmpty()) {
			userDetailsResponse.setIsFollowing(true);
		} else {
			userDetailsResponse.setIsFollowing(false);
		}
		List<Friend> numOfFollowers = friendRepositoryCustom.findByUserId(userId);
		if(!numOfFollowers.isEmpty()){
			userDetailsResponse.setNumOfFollowers(numOfFollowers.size());
		}
		List<Friend> numOfFollowing = friendRepositoryCustom.findByAnotherUserId(anotherUserId);
		if(!numOfFollowing.isEmpty()) {
			userDetailsResponse.setNumOfFollowing(numOfFollowing.size());
		}
		
		
		return userDetailsResponse;
		
	}

	@RequestMapping(value="/get-by-email", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public User getUserDetailByEmail(@RequestParam("email") String email) {
		User user = userRepository.findByEmail(email);
		if(user != null) {
			return user;
		}
		return null;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable(value="id")String id) {	
		
		User user = userRepository.findOne(id);
		
		userRepository.delete(user);
		
		return id;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public @ResponseBody
	Page<User> findAll(Pageable pageable,
					   @RequestParam(required = false) String query) {
		
		return userRepository.findAll(pageable);
    }

	
}
