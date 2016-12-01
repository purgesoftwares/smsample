package com.allowify.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.allowify.model.CodeEvent;
import com.allowify.model.EventInterested;
import com.allowify.model.Like;
import com.allowify.model.Share;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.EventInterestedRepositoryCustom;
import com.allowify.repository.LikeRepositoryCustom;
import com.allowify.repository.ShareRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.EventIntersetedResponse;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateEventOnCode;

@Controller
@RequestMapping("/event")
public class CodeEventController {
	
	@Autowired
	private CodeEventRepositoryCustom codeEventRepositoryCustom;
	
	@Autowired
	private LikeRepositoryCustom likeRepository;
	
	@Autowired
	private EventInterestedRepositoryCustom eventInterestedRepositoryCustom;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@Autowired
    @Qualifier("mongoTemplate")
	private MongoOperations mongoOperations;
	
	@Autowired
	private ActivityService service;
	
	@Autowired
	private ShareRepositoryCustom shareRepositoryCustom;
		
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody CodeEvent createCodeEvent(@RequestBody CodeEvent codeEvent) {
		
		codeEvent.setCode(codeEvent.getCode());
		codeEvent.setDescription(codeEvent.getDescription());
		codeEvent.setRepeatType(codeEvent.getRepeatType());
		codeEvent.setStartDate(codeEvent.getStartDate());
		codeEvent.setEndDate(codeEvent.getEndDate());
		codeEvent.setEventStartTime(codeEvent.getEventStartTime());
		codeEvent.setEventEndTime(codeEvent.getEventEndTime());
		codeEvent.setUserId(codeEvent.getUserId());
		codeEvent.setCreateDate(new Date());
		codeEvent.setUpdateDate(new Date());
		codeEvent.setPhoneNumber(codeEvent.getPhoneNumber());
				
		if(codeEvent.getRepeatType().equalsIgnoreCase(StatusConfiguration.DAILY)){
			codeEvent = createDailyEvents(codeEvent);	
		
		} else if(codeEvent.getRepeatType().equalsIgnoreCase(StatusConfiguration.WEEKLY)){
			codeEvent = createWeeklyEvents(codeEvent);
			
		} else if(codeEvent.getRepeatType().equalsIgnoreCase(StatusConfiguration.MONTHLY)){
			codeEvent = createMonthlyEvents(codeEvent);
		}
				
		return codeEvent;
	
	}
	
	
	private CodeEvent createDailyEvents(CodeEvent event) {
			
		Calendar start = Calendar.getInstance();
		start.setTime(event.getStartDate());
		Calendar end = Calendar.getInstance();
		end.setTime(event.getEndDate());

		for (Date date = start.getTime(); start.before(end) || start.equals(end); start.add(Calendar.DATE, 1),  
				date = start.getTime()) {

		    CodeEvent newEvent = new CodeEvent();
			
			newEvent.setCode(event.getCode());
			newEvent.setDescription(event.getDescription());
			newEvent.setRepeatType(event.getRepeatType());
			newEvent.setStartDate(date);
			newEvent.setEndDate(event.getEndDate());
			newEvent.setEventStartTime(event.getEventStartTime());
			newEvent.setEventEndTime(event.getEventEndTime());
			newEvent.setUserId(event.getUserId());
			newEvent.setCreateDate(new Date());
			newEvent.setUpdateDate(new Date());
			newEvent.setPhoneNumber(event.getPhoneNumber());
			newEvent = codeEventRepositoryCustom.save(newEvent);
			User user = userRepositoryCustom.findOne(event.getUserId());
			
			OnCreateEventOnCode onCreateEventOnCode = new OnCreateEventOnCode(newEvent, user);
			service.requestUserAcivity(onCreateEventOnCode);
			eventPublisher.publishEvent(onCreateEventOnCode);
			
			return newEvent;
			
		}
		return null;
		
	}


	private CodeEvent createWeeklyEvents(CodeEvent event) {
		Calendar start = Calendar.getInstance();
		start.setTime(event.getStartDate());
		Calendar end = Calendar.getInstance();
		end.setTime(event.getEndDate());
	 	int[] getRepeatedWeekDays = event.getRepeatValuesOfWeek();
		CodeEvent newEvent =null;
					
		for (Date date = start.getTime(); start.before(end) || start.equals(end);start.add(Calendar.DATE, 7),   
				date = start.getTime()) {
								
			for(int i=0; i< getRepeatedWeekDays.length; i++) {
				
			if(date.getDate() <= getRepeatedWeekDays[i]){
									
				newEvent = new CodeEvent();
				newEvent.setCode(event.getCode());
				newEvent.setDescription(event.getDescription());
				newEvent.setRepeatType(event.getRepeatType());
				newEvent.setStartDate(start.getTime());
				newEvent.setEndDate(event.getEndDate());
				newEvent.setEventStartTime(event.getEventStartTime());
				newEvent.setEventEndTime(event.getEventEndTime());
				newEvent.setUserId(event.getUserId());
				newEvent.setCreateDate(new Date());
				newEvent.setUpdateDate(new Date());
				newEvent.setPhoneNumber(event.getPhoneNumber());
				newEvent = codeEventRepositoryCustom.save(newEvent);
				User user = userRepositoryCustom.findOne(event.getUserId());
				OnCreateEventOnCode onCreateEventOnCode = new OnCreateEventOnCode(newEvent, user);
				service.requestUserAcivity(onCreateEventOnCode);
				eventPublisher.publishEvent(onCreateEventOnCode);
				return newEvent;
					
			}
				 
			
		}
				
	 }
		return null;
		
	}
	
	private CodeEvent createMonthlyEvents(CodeEvent event) {
			
			Calendar start = Calendar.getInstance();
			start.setTime(event.getStartDate());
			Calendar end = Calendar.getInstance();
			end.setTime(event.getEndDate());
		 	int[] getRepeatedDates = event.getRepeatValuesOfMonth();
			CodeEvent newEvent =null;
					
			for (Date date = start.getTime(); start.before(end) || start.equals(end);start.add(Calendar.MONTH, 1),   
					date = start.getTime()) {
																		
				for(int i=0; i<getRepeatedDates.length; i++) {
					
					if(date.getDate() <= getRepeatedDates[i] && start.before(end)){
						newEvent = new CodeEvent();
						newEvent.setCode(event.getCode());
						newEvent.setDescription(event.getDescription());
						newEvent.setRepeatType(event.getRepeatType());
						start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), 
									getRepeatedDates[i]);
						newEvent.setStartDate(start.getTime());
						newEvent.setEndDate(event.getEndDate());
						newEvent.setEventStartTime(event.getEventStartTime());
						newEvent.setEventEndTime(event.getEventEndTime());
						newEvent.setUserId(event.getUserId());
						newEvent.setCreateDate(new Date());
						newEvent.setUpdateDate(new Date());
						newEvent.setPhoneNumber(event.getPhoneNumber());
						newEvent = codeEventRepositoryCustom.save(newEvent);
						User user = userRepositoryCustom.findOne(event.getUserId());
						OnCreateEventOnCode onCreateEventOnCode = new OnCreateEventOnCode(newEvent, user);
						service.requestUserAcivity(onCreateEventOnCode);
						eventPublisher.publishEvent(onCreateEventOnCode);
						return newEvent;
								
					} 
				
				}
				
				start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), 
						event.getStartDate().getDate());	
		 	}
			return null;
		
	}


	@RequestMapping("/{id}")
	public @ResponseBody CodeEvent updateCodeEvent(@PathVariable("id") String id,
												    @RequestBody CodeEvent codeEvent) {
		CodeEvent codeEvents = codeEventRepositoryCustom.findById(id);
		
		codeEvents.setCode(codeEvent.getCode());
		codeEvents.setDescription(codeEvent.getDescription());
		codeEvents.setRepeatType(codeEvent.getRepeatType());
		codeEvents.setStartDate(codeEvent.getStartDate());
		codeEvents.setEndDate(codeEvent.getEndDate());
		codeEvents.setEventStartTime(codeEvent.getEventStartTime());
		codeEvents.setEventEndTime(codeEvent.getEventEndTime());
		codeEvents.setUpdateDate(new Date());
		codeEvents.setPhoneNumber(codeEvent.getPhoneNumber());
					
		return codeEvents;
	}
	
	
	@RequestMapping(value = "/details/{eventId}/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public EventIntersetedResponse isLiked(@PathVariable(value = "eventId") String eventId,
										   @PathVariable(value = "userId") String userId) {
		
		Like like = likeRepository.findByReferenceIdAndUserIdAndType(
				eventId, userId, StatusConfiguration.LIKE_TYPE_EVENT_VALUE);
		EventIntersetedResponse eventInterestedResponse = new EventIntersetedResponse();
		
		if(like != null) {
			eventInterestedResponse.setIsLiked(true);
		} else { 
			eventInterestedResponse.setIsLiked(false);
		}
		
		List<Like> eventlikes = likeRepository.findByReferenceIdAndType(
				eventId, StatusConfiguration.LIKE_TYPE_EVENT_VALUE);
		eventInterestedResponse.setLikesCount(eventlikes.size());
		
		EventInterested eventInterset = eventInterestedRepositoryCustom.
									findByEventIdAndUserId(eventId, userId);
		if(eventInterset != null){
			eventInterestedResponse.setInterestedInEvent(true);
		} else {
			eventInterestedResponse.setInterestedInEvent(false);
		}
		
		List<EventInterested> eventInterest = eventInterestedRepositoryCustom.findByEventId(eventId);
		if(eventInterest != null) {
			eventInterestedResponse.setNumOfInterestedInEvent(eventInterest.size());
		}
		List<Share> shareCounts = shareRepositoryCustom.findByReferenceId(eventId);
		eventInterestedResponse.setShareCounts(shareCounts.size());
		
		return eventInterestedResponse;	
	}
	
	@RequestMapping(value="/events-of-user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Page<CodeEvent> getEventsOfUser(@PathVariable("userId") String userId, Pageable pageable) {
		
		Page<CodeEvent> listOfEvents = codeEventRepositoryCustom.findByUserId(userId,
				new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), 
						new Sort(Sort.Direction.DESC, "createDate")));
		
		
		return listOfEvents;
	}
	
	@RequestMapping(value="/events-of-code/{code}", method = RequestMethod.GET)
	@ResponseBody
	public Page<CodeEvent> getEventsOfCode(@PathVariable("code") String code, Pageable pageable) {
		
		Page<CodeEvent> listOfEvents = codeEventRepositoryCustom.findByCode(code, 
				new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), 
				new Sort(Sort.Direction.DESC, "createDate")));
			
		return listOfEvents;
	}
	
	@RequestMapping(value="/events-of/{code}/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Page<CodeEvent> getEventsOfCodeAndUserID(@PathVariable("code") String code,
			@PathVariable("userId") String userId, Pageable pageable) {
		
		Page<CodeEvent> listOfEvents = codeEventRepositoryCustom.findByCodeAndUserId(code, 
				userId, new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), 
						new Sort(Sort.Direction.ASC, "createDate")));
				
		return listOfEvents;
	}

}
