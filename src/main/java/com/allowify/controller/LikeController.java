package com.allowify.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.allowify.model.Code;
import com.allowify.model.CodeEvent;
import com.allowify.model.Comment;
import com.allowify.model.Like;
import com.allowify.model.MainAcitivity;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.model.UserActivity;
import com.allowify.repository.CodeEventRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.CommentRepositoryCustom;
import com.allowify.repository.LikeRepositoryCustom;
import com.allowify.repository.MainAcitivityCustomRepository;
import com.allowify.repository.UserActivityRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateLikeEvent;

@Controller
@RequestMapping("/like")
public class LikeController {
	
	@Autowired
	private final LikeRepositoryCustom likeRepository;

	@Autowired
	private final CodeRepositoryCustom codeRepository;
	
	@Autowired
	private final UserRepositoryCustom userRepository;
	
	@Autowired
	private  CommentRepositoryCustom commentRepositoryCustom;
	
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
	private UserActivityRepositoryCustom userActivityRepositoryCustom;
	
	@Autowired
	private MainAcitivityCustomRepository mainAcitivityCustomRepository;

	@Autowired
	public LikeController(UserRepositoryCustom userRepository, 
			CodeRepositoryCustom codeRepository,
			LikeRepositoryCustom likeRepository, TaskExecutor taskExecutor) {
		Assert.notNull(codeRepository, "Repository must not be null!");
		Assert.notNull(userRepository, "Repository must not be null!");	
		this.userRepository = userRepository;
		this.codeRepository = codeRepository;
		this.likeRepository = likeRepository;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public Like create(@RequestBody @Valid Like like) {
		
		Like prelike = likeRepository.findByReferenceIdAndUserIdAndType(like.getReferenceId(), like.getUserId(), like.getType());
		User user = userRepository.findOne(like.getUserId());
		 
		if(prelike == null){
			like.setCreateDate(new Date());
			like = likeRepository.save(like);
			OnCreateLikeEvent createLikeEvent = null;
						
			if(like.getType() == StatusConfiguration.LIKE_TYPE_COMMENT_VALUE){
				
				Comment comment = commentRepositoryCustom.findById(
						like.getReferenceId());
				if(comment != null){
					comment.setLikeCount(comment.getLikeCount() + 1);
					commentRepositoryCustom.save(comment);	
					
				}
			
			} else if(like.getType() == StatusConfiguration.LIKE_TYPE_EVENT_VALUE) {
				CodeEvent codeEvent = codeEventRepositoryCustom.findById(like.getReferenceId());
				codeEvent.setLikeCount(codeEvent.getLikeCount() + 1);
				codeEventRepositoryCustom.save(codeEvent);
				createLikeEvent = new OnCreateLikeEvent(like, user, codeEvent, null);
				service.requestUserAcivity(createLikeEvent);
				eventPublisher.publishEvent(createLikeEvent);
								
				
			} else if(like.getType() == StatusConfiguration.LIKE_TYPE_CODE_VALUE) {
				Code code =  codeRepository.findOne(like.getReferenceId());
				if(code != null) {
					code.setLikesCount(code.getLikesCount() + 1);
					codeRepository.save(code);
					createLikeEvent = new OnCreateLikeEvent(like, user, null, code);
					service.requestUserAcivity(createLikeEvent);
					eventPublisher.publishEvent(createLikeEvent);
					} 
				
			} else if(like.getType() == StatusConfiguration.LIKE_TYPE_ACTIVITY_VALUE) {
				UserActivity userActivity = userActivityRepositoryCustom.findOne(like.getReferenceId());
				if(userActivity != null) {
					userActivity.setLikesCount(userActivity.getLikesCount() + 1);
					userActivityRepositoryCustom.save(userActivity);
				}
				
			} else if(like.getType() == StatusConfiguration.LIKE_TYPE_CODE_AND_EVENT_ACTIVITY_VALUE) {
				MainAcitivity mainActivity = mainAcitivityCustomRepository.findOne(like.getReferenceId());
				if(mainActivity != null) {
					mainActivity.setLikesCount(mainActivity.getLikesCount() + 1);
					mainAcitivityCustomRepository.save(mainActivity);
				}
			}
			
			
			return like;
		}
	
		
		return prelike;
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Like unlike(@RequestBody @Valid Like like) {
		
		Like prelike = likeRepository.findByReferenceIdAndUserIdAndType(
				like.getReferenceId(), like.getUserId(), like.getType());
		 
		if(prelike != null){
			likeRepository.delete(prelike);
			
			if(like.getType() == StatusConfiguration.LIKE_TYPE_COMMENT_VALUE){
				
				Comment comment = commentRepositoryCustom.findById(
						like.getReferenceId());
				if(comment != null){
					comment.setLikeCount(comment.getLikeCount() - 1);
					commentRepositoryCustom.save(comment);
				} 
			} else if(like.getType() == StatusConfiguration.LIKE_TYPE_EVENT_VALUE) {
				
				CodeEvent codeEvent = codeEventRepositoryCustom.findById(like.getReferenceId());
				codeEvent.setLikeCount(codeEvent.getLikeCount() - 1);
				codeEventRepositoryCustom.save(codeEvent);
			} else if(like.getType() == StatusConfiguration.LIKE_TYPE_CODE_VALUE) {
				Code code =  codeRepository.findOne(like.getReferenceId());
				if(code != null) {
					code.setLikesCount(code.getLikesCount() - 1);
				}
			}else if(like.getType() == StatusConfiguration.LIKE_TYPE_CODE_AND_EVENT_ACTIVITY_VALUE) {
				MainAcitivity mainActivity = mainAcitivityCustomRepository.findOne(like.getReferenceId());
				if(mainActivity != null) {
					mainActivity.setLikesCount(mainActivity.getLikesCount() - 1);
					mainAcitivityCustomRepository.save(mainActivity);
				}
			}

			return like;
		}
		return prelike;
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable(value = "id") String id) {
		likeRepository.delete(id);
	}
	
}
