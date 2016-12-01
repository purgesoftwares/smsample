package com.allowify.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.allowify.model.CheckIn;
import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.User;
import com.allowify.repository.CheckInRepositoryCustom;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateCheckInEvent;

@Controller
@RequestMapping("/check-in")
public class CheckInController {
	
	@Autowired
	private CheckInRepositoryCustom checkInRepositoryCustom;
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	@Autowired
	private CodeEventRepositoryCustom codeEventRepositoryCustom;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
	private ActivityService service;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	

	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public @ResponseBody CheckIn createCheckIn(@RequestBody CheckIn checkIn) {
				
		checkIn.setCode(checkIn.getCode());
		checkIn.setUserId(checkIn.getUserId());
		checkIn.setDate(new Date());
		checkInRepositoryCustom.save(checkIn); 
		User user = userRepositoryCustom.findOne(checkIn.getUserId());
		
		List<Code> code = codeRepositoryCustom.findByCode(checkIn.getCode());
		if(!code.isEmpty()) {
			code.get(0).setCheckInCount(code.get(0).getCheckInCount() + 1);
			codeRepositoryCustom.save(code.get(0));
			
			OnCreateCheckInEvent onCreateCheckInEvent = new OnCreateCheckInEvent(checkIn,null,code.get(0), user);
			service.requestUserAcivity(onCreateCheckInEvent);
			eventPublisher.publishEvent(onCreateCheckInEvent);
			
			return checkIn;
		}
		CodeEvent codeEvent = codeEventRepositoryCustom.findOne(checkIn.getCode());
		if(codeEvent != null) {
			codeEvent.setCheckInCount(codeEvent.getCheckInCount() + 1);
			codeEventRepositoryCustom.save(codeEvent);
			OnCreateCheckInEvent onCreateCheckInEvent = new OnCreateCheckInEvent(checkIn,codeEvent,null, user);
			service.requestUserAcivity(onCreateCheckInEvent);
			eventPublisher.publishEvent(onCreateCheckInEvent);
			
			return checkIn;
		}
		return null;
		
	}


	@RequestMapping("/{id}")
	public @ResponseBody CheckIn updateCheckIn(@PathVariable("id") String id,
											   @RequestBody CheckIn checkIn) {
		
		CheckIn preCheckIn = checkInRepositoryCustom.findById(id);
		if(preCheckIn != null) {
			preCheckIn.setCode(checkIn.getCode());
			preCheckIn.setDate(new Date());
			
			return checkInRepositoryCustom.save(preCheckIn);
		}
		
		return preCheckIn;
	}
	

}
