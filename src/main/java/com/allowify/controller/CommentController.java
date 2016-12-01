package com.allowify.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

import com.allowify.model.Comment;
import com.allowify.model.Like;
import com.allowify.model.MainAcitivity;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.UserActivity;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.CommentRepositoryCustom;
import com.allowify.repository.LikeRepositoryCustom;
import com.allowify.repository.MainAcitivityCustomRepository;
import com.allowify.repository.UserActivityRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.CustomCommentResponse;
import com.zippi.events.code.OnCreateCommentEvent;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	@Autowired
	private LikeRepositoryCustom likeRepositoryCustom;
	
	@Autowired
	private CommentRepositoryCustom commentRepositoryCustom;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@Autowired
	private UserActivityRepositoryCustom userActivityRepositoryCustom;
	
	@Autowired
	private MainAcitivityCustomRepository mainAcitivityCustomRepository;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CommentController(CodeRepositoryCustom codeRepositoryCustom, LikeRepositoryCustom likeRepositoryCustom,
			CommentRepositoryCustom commentRepositoryCustom, UserRepositoryCustom userRepositoryCustom) {
		Assert.notNull(userRepositoryCustom, "Repository must not be null!");
		this.codeRepositoryCustom = codeRepositoryCustom;
		this.likeRepositoryCustom = likeRepositoryCustom;
		this.commentRepositoryCustom = commentRepositoryCustom;
		this.userRepositoryCustom = userRepositoryCustom;
			
	}
	
	
	@RequestMapping(method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public Comment create(@RequestBody @Valid Comment comment) {
		
			comment.setCreateDate(new Date());
			comment.setUpdateDate(new Date());
			comment = commentRepositoryCustom.save(comment);	
			
			if(comment.getParentType() == StatusConfiguration.COMMENT_PARENT_TYPE_ACTIVITY_VALUE){
			
				UserActivity userActivity = userActivityRepositoryCustom.findOne(comment.getReferenceId());
				userActivity.addComment(comment);
				userActivityRepositoryCustom.save(userActivity);
			
			} else if(comment.getParentType() == StatusConfiguration.COMMENT_CHILD_TYPE_CODE_EVENT_ACTIVITY_VALUE) {
				
				MainAcitivity mainActivity = mainAcitivityCustomRepository.findOne(comment.getReferenceId());
				mainActivity.addComment(comment);
				mainAcitivityCustomRepository.save(mainActivity);
			}
			
			OnCreateCommentEvent createCommnetEvent = new OnCreateCommentEvent(comment);
			
			//createCommentActivity(createCommnetEvent);
			
			// publish the create code event to do tasks
			eventPublisher.publishEvent(createCommnetEvent);
			
			return commentRepositoryCustom.save(comment);
	}
	
	// Create Comment activity 
    public void createCommentActivity(final OnCreateCommentEvent event) {
    	
        final UserActivity userActivity = new UserActivity();
      
        	userActivity.setTitle("Comment is Created : "
        			+event.getComment().getComment());
        	
        	userActivity.setDescription("For Comment : "
        			+"Number of Likes : "
        			+event.getComment().getLikeCount()+","
        			+"Type of like : "
        			+event.getComment().getParentType());
     
        userActivity.setCreateDate(new Date());
        
        userActivity.setUpdateDate(new Date());
        
        
        mongoOperations.save(userActivity);
        
        
    }
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Comment> getAllComments() {
		List<Comment> listcomments = commentRepositoryCustom.findAll();
		return listcomments;
	}
	
	@RequestMapping(value = "/{type}/{referenceId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Comment> getComments(@PathVariable(value = "type") String type,
			@PathVariable(value = "referenceId") String referenceId, Pageable pageable) {
		
		List<Comment> listcomments = commentRepositoryCustom.findByParentTypeAndReferenceId(type, referenceId, pageable);
		
		return listcomments;
	}
	
	@RequestMapping(value = "/{type}/{referenceId}/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomCommentResponse> getComments(@PathVariable(value = "type") String type,
			@PathVariable(value = "referenceId") String referenceId, 
			@PathVariable(value = "userId") String userId, 
			Pageable pageable) {
		
		List<Comment> listcomments = 
				commentRepositoryCustom.findByParentTypeAndReferenceId(type, referenceId, pageable);
		
		List<String> listCommentIds = new ArrayList<String>();
		
		for (Comment comment : listcomments){
			listCommentIds.add(comment.getId());
		}
		
		List<Like> userLikes = 
				mongoOperations.find(
						new Query(
								Criteria.where("referenceId").in(listCommentIds)
								.and("userId").is(userId).and("type").is(type)), Like.class);
		
		List<CustomCommentResponse> listCommentWrapper = new ArrayList<CustomCommentResponse>();
		
		for (Comment comment : listcomments){
			CustomCommentResponse customCommentListResponse = new CustomCommentResponse(comment); 
			
			for (Like like : userLikes){
				if(like.getType() == StatusConfiguration.LIKE_TYPE_COMMENT_VALUE 
						&& like.getReferenceId() == comment.getId()){
					customCommentListResponse.setLiked(true);
				}
			}
			
			listCommentWrapper.add(customCommentListResponse);
		}
		
		
		return listCommentWrapper;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable(value = "id") String id) {
		commentRepositoryCustom.delete(id);
	}
	

}
