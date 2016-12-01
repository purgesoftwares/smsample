package com.allowify.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.MainAcitivity;
import com.allowify.model.UserActivity;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.MainAcitivityCustomRepository;
import com.allowify.repository.UserActivityRepositoryCustom;
import com.allowify.response.CodeNearWithCodeEventResponse;
import com.allowify.response.CustomUserActivityResponse;
import com.zippi.places.GooglePlaces.NearRequest;

@Controller
@RequestMapping("/activities")
public class UserActivityController {
	
	@Autowired
	private UserActivityRepositoryCustom userActivityRepositoryCustom;
	
	public static final Double KILOMETER = 111.0d;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
	private CodeEventRepositoryCustom eventRepositoryCustom;
	
	@Autowired
	private MainAcitivityCustomRepository mainAcitivityCustomRepository;
	
	public static final int LIMIT = 20;
	
	@RequestMapping(value="/user/{userId}",method =RequestMethod.GET)
	public @ResponseBody Page<UserActivity> getUserActivity(@PathVariable(value = "userId") String userId,
			Pageable pageable) {
		
		
		Page<UserActivity> userActivity = userActivityRepositoryCustom.findByUserId(userId,
				new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "createDate")) );
		
		return userActivity;
			
	}

	 private Double getInKilometer(Double maxdistance) {
	        return maxdistance / KILOMETER;
	 }
	 
	 @RequestMapping(value="/search/" , method = RequestMethod.POST, consumes = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} )
	 public @ResponseBody Page<MainAcitivity> getMainActivity(@RequestBody NearRequest nearRequest,
			 Pageable pageable) {
		 	
		 Criteria criteria = new Criteria("latLong").near(new Point(nearRequest.getLatLong()[0], nearRequest.getLatLong()[1])).
			        maxDistance(getInKilometer(nearRequest.getDistance()));
		 Criteria criteria1 = Criteria.where("isPrivate").is(0).and("status").is(1);
		 
	     List<Order> orders = new ArrayList<Order>();
	        orders.add(
	        		new Order(
	        				Direction.DESC, "createDate"
	        				)
	        		);
	               
		 List<MainAcitivity> mainAcitivities = mongoOperations.find(
	        		new Query(criteria).addCriteria(criteria1).limit(nearRequest.getLimit())
	        		.with(new org.springframework.data.domain.Sort(orders)),
	        		MainAcitivity.class);
		 
		 PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
    			 new Sort(Sort.Direction.DESC, "createDate"));
    	 Page<MainAcitivity> pageImpianto = 
    			 new PageImpl<MainAcitivity>(mainAcitivities, pageRequest, mainAcitivities.size()); 
		 
	    return pageImpianto;
	}

}
