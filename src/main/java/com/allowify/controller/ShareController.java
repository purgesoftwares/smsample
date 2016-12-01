package com.allowify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.Share;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.model.UserActivity;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.ShareRepositoryCustom;
import com.allowify.repository.UserActivityRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateShareEvent;

@Controller
@RequestMapping("/share")
public class ShareController {
	
	@Autowired
	private ShareRepositoryCustom shareRepositoryCustom;
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	@Autowired
	private CodeEventRepositoryCustom codeEventRepositoryCustom;
	
	@Autowired
	private ActivityService service;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@Autowired
	private UserActivityRepositoryCustom userActivityRepositoryCustom;
	
	@RequestMapping("/create")
	@ResponseBody
	public Share createShare(@RequestBody Share share) {
		
		shareRepositoryCustom.save(share);
		
		User user = userRepositoryCustom.findOne(share.getUserId());
		
		Code code = codeRepositoryCustom.findOne(share.getReferenceId());
						
		if(share.getType().equalsIgnoreCase(StatusConfiguration.ACTIVITY_TYPE_CODE_CREATED) 
				&& code != null) {
			
			OnCreateShareEvent onShareEvent = new OnCreateShareEvent(user, code, null, null);
			service.requestUserAcivity(onShareEvent);
			eventPublisher.publishEvent(onShareEvent);
			return share;
			
		}
		
		CodeEvent codeEvent = codeEventRepositoryCustom.findById(share.getReferenceId());
		if(share.getType().equalsIgnoreCase(StatusConfiguration.ACTIVITY_TYPE_EVENT_CREATED) 
				&& codeEvent != null) {
						
			OnCreateShareEvent onShareEvent = new OnCreateShareEvent(user, null, codeEvent, null);
			service.requestUserAcivity(onShareEvent);
			eventPublisher.publishEvent(onShareEvent);
			return share;
		}
		
		UserActivity activity = userActivityRepositoryCustom.findOne(share.getReferenceId());
		if(share.getType().equalsIgnoreCase(StatusConfiguration.ACTIVITY_TYPE_ACTIVITY_CREATED) 
				&& activity != null) {
			activity.setShareCounts(activity.getShareCounts() + 1);
			userActivityRepositoryCustom.save(activity);
			
			OnCreateShareEvent onShareEvent = new OnCreateShareEvent(user, null, null, activity);
			service.requestUserAcivity(onShareEvent);
			eventPublisher.publishEvent(onShareEvent);
			return share;
		}
		
		return null;
	}

}
