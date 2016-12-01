package com.allowify.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allowify.CloudSdpMessageException;
import com.allowify.config.MongoDBConfig;
import com.allowify.model.Alert;
import com.allowify.model.User;
import com.allowify.repository.AlertRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.AlertsResponse;

/**
 * 
 * @author tonyacunar
 *
 */
@Controller
@RequestMapping("/alerts")
public class AlertController {

	private final UserRepositoryCustom userRepository;
	private final AlertRepositoryCustom alertRepository;
	
	@Autowired
	public AlertController(UserRepositoryCustom userRepository,
			AlertRepositoryCustom alertRepository) {
		this.userRepository = userRepository;
		this.alertRepository = alertRepository;
	
	}
	
	@RequestMapping("/getAlerts")
	public @ResponseBody AlertsResponse getAlerts() {
		
		User requestingUser = loadUserFromSecurityContext();		
		List<Alert> alertList =  
				alertRepository.findByUserId(requestingUser.getId().toString(), new Sort(Sort.Direction.DESC, "timestamp"));

		
		
		return new AlertsResponse(alertList);
	}
	
	/**
	 * delete all alerts in one go
	 * @return
	 */
	@RequestMapping("/deleteAllAlerts")
	public @ResponseBody AlertsResponse deleteAllAlerts() {
		
		User requestingUser = loadUserFromSecurityContext();		
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		mongoOperation.remove(new Query(Criteria.where("id").is(requestingUser.getId().toString())), Alert.class);
		
		//empty list
		List<Alert> alertList = new ArrayList<Alert>(0);
		return new AlertsResponse(alertList);
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
	
}
