package com.allowify.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allowify.config.MongoDBConfig;
import com.allowify.model.Bookmark;
import com.allowify.model.CheckIn;
import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.GPlace;
import com.allowify.model.Like;
import com.allowify.model.Review;
import com.allowify.model.Share;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.model.UserAddress;
import com.allowify.model.VIPCode;
import com.allowify.repository.BookmarkRepositoryCustom;
import com.allowify.repository.CheckInRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.CommentRepositoryCustom;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.LikeRepositoryCustom;
import com.allowify.repository.ReviewRepositoryCustom;
import com.allowify.repository.ShareRepositoryCustom;
import com.allowify.repository.UserAddressRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.repository.VIPCodeRepositoryCustom;
import com.allowify.response.ByCodeDetailsResponse;
import com.allowify.response.CodeDetailsResponse;
import com.allowify.response.CodeNearWithCodeEventResponse;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateCodeEvent;
import com.zippi.places.GooglePlaces.NearRequest;

@Controller
@RequestMapping("/code")
public class CodeController {
	
	public static int count=0;

	private final VIPCodeRepositoryCustom vipRepository;
	
	private final CodeRepositoryCustom codeRepository;
	
	private final UserRepositoryCustom userRepository;
	
	@Autowired
	private final LikeRepositoryCustom likeRepository;
	
	@Autowired
	private final BookmarkRepositoryCustom bookmarkRepository;
	
	@Autowired
	private final CommentRepositoryCustom commentRepositoryCustom;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private CheckInRepositoryCustom checkInRepositoryCustom;
	
	@Autowired
	private CodeEventRepositoryCustom eventRepositoryCustom;
	
	@Autowired
	private ReviewRepositoryCustom reviewsRepositoryCustom;
	
	@Autowired
	private UserAddressRepositoryCustom userAddressRepositoryCustom;
	
	@Autowired
	private ActivityService service;
	
	@Autowired
	private ConcurrentTaskExecutor task;
	
	@Autowired
	private ShareRepositoryCustom shareRepositoryCustom;
	
	public static final Double KILOMETER = 111.0d;
	
	public static final Double MILE = 69.0d;
	
	@Autowired
	public CodeController(VIPCodeRepositoryCustom vipRepostitory, 
			CodeRepositoryCustom codeRepository, 
			UserRepositoryCustom userRepository,
			LikeRepositoryCustom likeRepository,
			BookmarkRepositoryCustom bookmarkRepository,CommentRepositoryCustom commentRepositoryCustom) {
		this.vipRepository = vipRepostitory;
		this.codeRepository = codeRepository;
		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
		this.bookmarkRepository = bookmarkRepository;
		this.commentRepositoryCustom = commentRepositoryCustom;
	}
	
	/*
	 * This method is used to post all Random code
	 */
	@RequestMapping(value="/putNewCodes", method=RequestMethod.POST)
	public @ResponseBody List<VIPCode> putNewCodes() {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		int i, j, k,l,m;
		int checkCount = 0;
		char[] charArray={'0','1','2','3','4','5','6' ,'7','8','9','A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		String[] codeArray = new String[500];
		
		List<VIPCode> listArray=new ArrayList<VIPCode>();
		
		
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
								VIPCode code = new VIPCode();
								code.setCode(codeGeneratesStr);
								code.setStatus(StatusConfiguration.NORMAL);
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
	
	/*
	 * This method is used to get all Random code
	 */
	@RequestMapping(value="/get-new-code",method =RequestMethod.GET)
	public @ResponseBody List<VIPCode> getNewCodes() {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		BasicQuery query = new BasicQuery("{ status : '" + StatusConfiguration.NORMAL  + "' }");
		query.limit(1);
		List<VIPCode> code = mongoOperation.find(query, VIPCode.class);
		
		return code;
		
	}
	
	public List<VIPCode> getNewCode() {
		
		BasicQuery query = new BasicQuery("{ status : '" + StatusConfiguration.NORMAL  + "' }");
		query.limit(1);
		List<VIPCode> code = mongoOperations.find(query, VIPCode.class);
		
		return code;
		
	}
	
	
	/*
	 * This method is used to get all Random code
	 */
	@RequestMapping(value="/add-places-to-code",method =RequestMethod.GET)
	public @ResponseBody void addPlacesToCode() {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		List<GPlace> places = mongoOperation.find(new Query(), GPlace.class);
		
		
		
		for( GPlace place : places){
			
			
			double[] latLong = new double[2];
			//GPlace place;
			latLong[0] = Double.valueOf(place.getLat());
			latLong[1] = Double.valueOf(place.getLng());
			List<Code> codes = mongoOperation.find(new Query(Criteria.where("title").is(place.getName()).and("latLong").is(latLong)), Code.class);
			
			if(codes.isEmpty()){
				Code code = new Code();
				
				List<VIPCode> vipCodes = getNewCode();
				
				String[] address =place.getVicinity().split(", ");
				
				code.setCode(vipCodes.get(0).getCode());
				code.setTitle(place.getName());
				code.setIsPrivate(0);
				code.setStatus(1);
				code.setCountry("India");
				code.setState("Rajasthan");
				code.setLatLong(latLong);
				code.setCity(address[address.length - 1]);
				if(address.length>2)
				code.setAddress2(address[address.length - 2]);
				
				if(address.length==2)
					code.setAddress1(address[address.length - 2]);
					
				if(address.length==3)
					code.setAddress1(address[address.length - 3]);
				
				if(address.length==4)
					code.setAddress1(address[address.length - 4] + ", " +address[address.length - 3]);
				
				mongoOperation.save(code);
				
				VIPCode vipCode = vipCodes.get(0);
				vipCode.setStatus(StatusConfiguration.BOOKED);
				mongoOperation.save(vipCode);
				
				try {
				    Thread.sleep(1000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
			
			
		}
		
		
	}
	
	/*
	 * This method is used to add user by GPS Location
	 */
	//@SuppressWarnings("deprecation")
	@RequestMapping(value = "near1", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<Code> near(@RequestBody NearRequest nearRequest) {

		System.out.print(nearRequest.getDistance() + " = " + nearRequest.getLatLong()[0]+ ", " + nearRequest.getLatLong()[1]);
		//Point point;
		List<Code> codes = codeRepository.findByLatLongNear(
				new Point(nearRequest.getLatLong()[0] ,nearRequest.getLatLong()[1]), 
				new Distance(nearRequest.getDistance(), Metrics.KILOMETERS));

		return codes;
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Code update(@RequestBody Code code) {
		
		List<Code> preCode = codeRepository.findByCode(code.getCode());
		
		if(!preCode.isEmpty()){
			code = codeRepository.save(code);
		}
		
		return code;
		
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable(value = "code") String code) {
		
		List<Code> preCode = codeRepository.findByCode(code);
		
		if(preCode.size() > 0){
			codeRepository.delete(preCode);
			VIPCode vipCode = vipRepository.findByCode(code);
			vipCode.setStatus(StatusConfiguration.NORMAL);
			vipRepository.save(vipCode);
//			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	@ResponseBody
	public Code get(@PathVariable(value = "code") String code) {
		
		List<Code> preCode = codeRepository.findByCode(code);
		
		return preCode.get(0);
	}
	
	
	@RequestMapping(value = "/suggestions/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Page<VIPCode> suggestions(@PathVariable(value = "userId") String userId, Pageable pageable) {
		
		
		User user = userRepository.findOne(userId);
		UserAddress userAddr = null;
		String fl = null, sugCode = null, sugCodeByPhone = null, sugCodeByPhoneTwoDig = null, firstN = null;
		String sugCodeByAddr = null, sugCodeByAddrCity = null;
		BasicQuery query = null;
		
		Criteria criteria = Criteria.where("status").is(StatusConfiguration.NORMAL);
		firstN = String.valueOf(user.getFirstName().charAt(0)) 
				+ String.valueOf(user.getFirstName().charAt(1));
			
		//get suggested code by user first name or last name characters & phone numbers & address bases
		if(user != null) {
			if(user.getAddressId() != null) {
							
				if(user.getLastName() != null) {
					fl = String.valueOf(user.getFirstName().charAt(0)) + String.valueOf(user.getLastName().charAt(0));
					
					sugCode = String.valueOf(user.getFirstName().charAt(0)) 
							+ String.valueOf(user.getFirstName().charAt(1))
							+ String.valueOf(user.getLastName().charAt(0))
							+ String.valueOf(user.getLastName().charAt(1));
				}
			
				if(user.getPhoneNumber() != null) {
					sugCodeByPhone = String.valueOf(user.getPhoneNumber().charAt(0)) 
							+ String.valueOf(user.getPhoneNumber().charAt(1));
					
					sugCodeByPhoneTwoDig = String.valueOf(user.getPhoneNumber().charAt(0))
							+ String.valueOf(user.getPhoneNumber().charAt(1))
							+ String.valueOf(user.getPhoneNumber().charAt(2))
							+ String.valueOf(user.getPhoneNumber().charAt(3));
				}
				
				
				userAddr = userAddressRepositoryCustom.findOne(user.getAddressId());
				
				if(userAddr.getAddress1() != null && userAddr.getAddress2() != null) {
					sugCodeByAddr = String.valueOf(userAddr.getAddress1().charAt(0))
							+ String.valueOf(userAddr.getAddress1().charAt(1))
							+ String.valueOf(userAddr.getAddress2().charAt(0))
							+ String.valueOf(userAddr.getAddress2().charAt(1));
				}
				
				if(userAddr.getCity() != null && userAddr.getPinCode() != null) {
					sugCodeByAddrCity = String.valueOf(userAddr.getCity().charAt(0))
							+ String.valueOf(userAddr.getCity().charAt(1))
							+ String.valueOf(userAddr.getPinCode().charAt(0))
							+ String.valueOf(userAddr.getPinCode().charAt(1));
				}
				
				
				if(user.getLastName() != null && user.getPhoneNumber() != null 
						&& userAddr.getAddress1() != null && userAddr.getAddress2() != null 
						&& userAddr.getCity() != null && userAddr.getPinCode() != null) {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + fl 
							+ "','$options' : 'i'} }, {\"code\": {$regex : '" + 
							sugCode + "','$options' : 'i'} }, "
									+ "{\"code\": {$regex : '" + 
							sugCodeByPhone + "','$options' : 'i'} }, " 
									+ "{\"code\": {$regex : '" + 
							sugCodeByPhoneTwoDig +"','$options' : 'i'} },"
									+ "{\"code\": {$regex : '" + 
							sugCodeByAddr + "','$options' : 'i'} },"
									+ "{\"code\": {$regex : '" + 
							sugCodeByAddrCity + "','$options' : 'i'} }]}");
					
				} else if(userAddr.getAddress1() != null && userAddr.getAddress2() != null 
						&& userAddr.getPinCode() != null && userAddr.getCity() != null 
						&& user.getLastName() != null)  { 
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + fl 
							+ "','$options' : 'i'} },"  
							+ "{\"code\": {$regex : '" 
							+ sugCode + "','$options' : 'i'} }, "
									+ "{\"code\": {$regex : '" + 
							sugCodeByAddr + "','$options' : 'i'} },"
									+ "{\"code\": {$regex : '" + 
							sugCodeByAddrCity + "','$options' : 'i'} }]}");
					
				} else if(userAddr.getAddress1() != null && userAddr.getAddress2() != null 
						&& userAddr.getPinCode() != null && userAddr.getCity() != null)  { 
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + firstN 
							+ "','$options' : 'i'} },"  
									+ "{\"code\": {$regex : '" + 
							sugCodeByAddr + "','$options' : 'i'} },"
									+ "{\"code\": {$regex : '" + 
							sugCodeByAddrCity + "','$options' : 'i'} }]}");
					
				} else if(user.getLastName() != null) {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + fl 
							+ "','$options' : 'i'} }, {\"code\": {$regex : '" + 
							sugCode + "','$options' : 'i'} } ]}");
					
				} else if(user.getPhoneNumber() != null) {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + firstN 
							+ "','$options' : 'i'} }, "
									+ "{\"code\": {$regex : '" + 
							sugCodeByPhoneTwoDig + "','$options' : 'i'} }]}");
				} else {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + firstN 
							+ "','$options' : 'i'} } ]}");
				}
				
					
								
			} else {
				// get suggested code by  user name and phone number bases
				
				if(user.getLastName() != null && user.getPhoneNumber() != null) {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + fl 
							+ "','$options' : 'i'} }, {\"code\": {$regex : '" + 
							sugCode + "','$options' : 'i'} }, "
									+ "{\"code\": {$regex : '" + 
							sugCodeByPhone + "','$options' : 'i'} }, "
									+ "{\"code\": {$regex : '" + 
							sugCodeByPhoneTwoDig + "','$options' : 'i'} }]}");
				
				} else if(user.getLastName() != null) {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + fl 
							+ "','$options' : 'i'} }, {\"code\": {$regex : '" + 
							sugCode + "','$options' : 'i'} } ]}");
					
				} else if(user.getPhoneNumber() != null) {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + firstN 
							+ "','$options' : 'i'} }, "
									+ "{\"code\": {$regex : '" + 
							sugCodeByPhoneTwoDig + "','$options' : 'i'} }]}");
				} else {
					query = new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + firstN 
							+ "','$options' : 'i'} } ]}");
				}
							
			}
					
		}
		
		query.addCriteria(criteria);
		query.limit(30);
		List<VIPCode> codes = mongoOperations.find(query, VIPCode.class);
		
		PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), null);
		Page<VIPCode> pageImpianto = 
   			 new PageImpl<VIPCode>(codes, pageRequest, codes.size()); 
		
		return pageImpianto;
	}
	
	@RequestMapping(value = "/details/{code}/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public CodeDetailsResponse isLiked(@PathVariable(value = "code") String code, 
			@PathVariable(value = "userId") String userId, Pageable pageable) {
		
		Like like = likeRepository.findByReferenceIdAndUserId(code, userId);
		
		List<Code> codeId = codeRepository.findByCode(code);
		
		CodeDetailsResponse codeDetailsResponse = new CodeDetailsResponse();
		
		if(like != null){
			codeDetailsResponse.setIsLiked(true);
		}else{
			codeDetailsResponse.setIsLiked(false);
		}
		
		Bookmark bookmark = bookmarkRepository.findByUserId(userId);
		if(bookmark != null){
			Code bookmarkedCode = bookmark.getCode(code);
			
			if(bookmarkedCode.equals(null)){
				codeDetailsResponse.setBookmarked(true);
			}else{
				codeDetailsResponse.setBookmarked(false);
			}
		}else{
			codeDetailsResponse.setBookmarked(false);
		}
		
		List<Like> codelikes = likeRepository.findByReferenceIdAndType(code, 1);
		
		codeDetailsResponse.setLikesCount(codelikes.size());
		
		List<CheckIn> checkIn = checkInRepositoryCustom.findByUserIdAndCode(userId, code);
		
		List<Code> codeCheck = codeRepository.findByCode(code);
		
		List<Review> reviews = reviewsRepositoryCustom.findByCode(code);
	
		if(codeCheck != null) {
			codeDetailsResponse.setCheckInCount(codeCheck.get(0).getCheckInCount());
			
		}
		if(reviews != null) {
			codeDetailsResponse.setReviewsCount(reviews.size());
		}
		if(!checkIn.isEmpty()) {
			codeDetailsResponse.setCheckedIn(true);
		}
		
		Page<CodeEvent> event = eventRepositoryCustom.findByCode(code, pageable);
		if(event != null){
			codeDetailsResponse.setEventsCount(event.getNumberOfElements());
		}
		
		List<Review> reviewsOfUser = reviewsRepositoryCustom.findByUserId(userId);
		if(!reviewsOfUser.isEmpty()) {
			codeDetailsResponse.setIsReviewed(true);
		}
		
		List<Review> reviewsOnCode = reviewsRepositoryCustom.findByCode(code);
		double addNumOf = 0;
		for(Review reviewModel : reviewsOnCode) {
			addNumOf = addNumOf + reviewModel.getRating();
		}
		
		codeDetailsResponse.setAvgRating(addNumOf/reviewsOnCode.size());
		
		List<Share> shareCounts = shareRepositoryCustom.findByReferenceId(codeId.get(0).getId());
		codeDetailsResponse.setShareCounts(shareCounts.size());
		
		return codeDetailsResponse;
	}
	
	@RequestMapping(value = "/details/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ByCodeDetailsResponse codeDetails(@PathVariable(value = "code") String code,
											 Pageable pageable) {
		
		ByCodeDetailsResponse byCodeDetailsResponse = new ByCodeDetailsResponse();
		List<Code> codeCheck = codeRepository.findByCode(code);
		List<Review> reviews = reviewsRepositoryCustom.findByCode(code);
		List<Code> codeId = codeRepository.findByCode(code);
		if(codeCheck != null) {
			byCodeDetailsResponse.setCheckInCount(codeCheck.get(0).getCheckInCount());
			
		}
		if(reviews != null) {
			byCodeDetailsResponse.setReviewsCount(reviews.size());
		}
		Page<CodeEvent> event = eventRepositoryCustom.findByCode(code, pageable);
		if(event != null){
			byCodeDetailsResponse.setEventsCount(event.getNumberOfElements());
		}
		List<Like> codelikes = likeRepository.findByReferenceIdAndType(code, 1);
		byCodeDetailsResponse.setLikesCount(codelikes.size());
		
		List<Share> shareCounts = shareRepositoryCustom.findByReferenceId(codeId.get(0).getId());
		byCodeDetailsResponse.setShareCounts(shareCounts.size());
		
		return byCodeDetailsResponse;
		
	}
	
	

	@RequestMapping(value="/add-code",method =RequestMethod.POST)
	public @ResponseBody Code addCode(@RequestBody Code code) 
			throws ClassNotFoundException {
		
		List<VIPCode> vipCodes = getNewCode();
		
		code.setCode(vipCodes.get(0).getCode());
		code.setCreateDate(new Date());
		code.setUpdateDate(new Date());
		
		User user = userRepository.findOne(code.getUserId());
		
		if(user == null){
			throw new ClassNotFoundException("User is not Valid.");
		}
		codeRepository.save(code);
		
		VIPCode vipCode = vipCodes.get(0);
		
		if(vipCode.getCode().equalsIgnoreCase(code.getCode())){
			vipCode.setStatus(StatusConfiguration.BOOKED);
			vipRepository.save(vipCode);
		}
		
		OnCreateCodeEvent createCodeEvent = new OnCreateCodeEvent(user, code);
		service.requestUserAcivity(createCodeEvent);
		// publish the create code event to do tasks
		eventPublisher.publishEvent(createCodeEvent);
		
		return code;
		
	}

	
	@RequestMapping(value="/book-code",method =RequestMethod.POST)
	public @ResponseBody Code bookCode(@RequestBody Code code) {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
			
		mongoOperation.save(code);
		
		VIPCode vipCode = vipRepository.findByCode(code.getCode());
		
		if(vipCode.getCode().equalsIgnoreCase(code.getCode())){
			vipCode.setStatus(StatusConfiguration.BOOKED);
			mongoOperation.save(vipCode);
		}
		
		return code;
		
	}
	
    /**
     * The current implementation of near assumes an idealized model of a flat
     * earth, meaning that an arcdegree
     * of latitude (y) and longitude (x) represent the same distance everywhere.
     * This is only true at the equator where they are both about equal to 69 miles
     * or 111km. Therefore you must divide the
     * distance you want by 111 for kilometer and 69 for miles.
     *
     * @param maxdistance The distance around a point.
     * @return The calcuated distance in kilometer.
     */
    private Double getInKilometer(Double maxdistance) {
        return maxdistance / KILOMETER;
    }
    
    private Double getInMiles(Double maxdistance) {
        return maxdistance / MILE;
    }
    
	@RequestMapping(value = "near-codes", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public
    @ResponseBody
    List<Code> getByLocation(@RequestBody NearRequest nearRequest)
            throws Exception {
		Criteria criteria = new Criteria("latLong").near(new Point(nearRequest.getLatLong()[0], nearRequest.getLatLong()[1])).
				        maxDistance(getInKilometer(nearRequest.getDistance()));
		Criteria criteria1 = Criteria.where("isPrivate").is(0).and("status").is(1);
        
        List<Order> orders = new ArrayList<Order>();
        orders.add(
        		new Order(
        				Direction.DESC, "totalReach"
        				)
        		);
        
        if(nearRequest.getLimit() == 0){
        	nearRequest.setLimit(100);
        }
        
        List<Code> codes = mongoOperations.find(
        		new Query(criteria).addCriteria(criteria1)
        		.limit(nearRequest.getLimit()).with(new org.springframework.data.domain.Sort(orders)),
        		Code.class);
        
        return codes;
    }
    
	@RequestMapping(value = "/near", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public
    @ResponseBody
    CodeNearWithCodeEventResponse getByLocationCodeWithEvents(@RequestBody NearRequest nearRequest)
            throws Exception {
		Criteria criteria = new Criteria("latLong").near(new Point(nearRequest.getLatLong()[0], nearRequest.getLatLong()[1])).
				        maxDistance(getInKilometer(nearRequest.getDistance()));
		Criteria criteria1 = Criteria.where("isPrivate").is(0).and("status").is(1);
        
        List<Order> orders = new ArrayList<Order>();
        orders.add(
        		new Order(
        				Direction.DESC, "totalReach"
        				)
        		);
        
        if(nearRequest.getLimit() == 0){
        	nearRequest.setLimit(100);
        }
        
        List<Code> codes = mongoOperations.find(
        		new Query(criteria).addCriteria(criteria1)
        		.limit(nearRequest.getLimit()).with(new org.springframework.data.domain.Sort(orders)),
        		Code.class);
        
          
        List<String> listOfCodes = new ArrayList<String>();
        for(int i=0; i<codes.size(); i++) {
        	listOfCodes.add(codes.get(i).getCode()); 
        }
        
    	List<CodeEvent> codeEvents = eventRepositoryCustom.findByCodes(listOfCodes, 
    			new Date(), new Sort(Sort.Direction.ASC, "eventStartTime"));
    	        
    	CodeNearWithCodeEventResponse locationWithCodeResponse = new CodeNearWithCodeEventResponse(codes, codeEvents);
        return locationWithCodeResponse;
        
    }
    
    
	
	@RequestMapping(value="/deleteCodes",method =RequestMethod.DELETE)
	public @ResponseBody List<Code> deleteCodes() {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
				
		mongoOperation.findAllAndRemove(new Query(), Code.class);
				
		return null;
		
	}
	
	@RequestMapping(value="/deleteVipCodes",method =RequestMethod.DELETE)
	public @ResponseBody List<VIPCode> deleteVipCodes() {
		ApplicationContext ctx= new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
				
		mongoOperation.findAllAndRemove(new Query(), VIPCode.class);
				
		return null;
		
	}
	
	@RequestMapping("/search")
	public @ResponseBody List<Code> search(@RequestBody String keyword) {
		
		BasicQuery query = 
			new BasicQuery("{\"$or\":[{\"code\": {$regex : '" + 
		keyword + "','$options' : 'i'} }, {\"title\": {$regex : '" + 
					keyword + "','$options' : 'i'} }, {\"address1\": {$regex : '" + 
		keyword + "','$options' : 'i'} }, {\"address2\": {$regex : '" + keyword + "','$options' : 'i'} }]}");
		
		query.limit(20);
		
		List<Code> codes = mongoOperations.find(query, Code.class);
		return codes;
	}
	
	
}
