package com.allowify.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allowify.model.Comment;
import com.allowify.model.Friend;
import com.allowify.model.Like;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.model.UserActivity;
import com.allowify.repository.FriendRepositoryCustom;
import com.allowify.repository.UserActivityRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.response.CustomCommentResponse;
import com.allowify.response.CustomUserActivityResponse;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateFriendEvent;

@Controller
@RequestMapping("/friend")
public class FriendController {
	
	@Autowired
	private FriendRepositoryCustom friendRepositoryCustom;
	
	@Autowired
	private UserActivityRepositoryCustom userActivityRepositoryCustom;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private ActivityService activityService ;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	@RequestMapping(method =RequestMethod.POST)
	public @ResponseBody Friend createFriend(@RequestBody Friend friend) {
			
		Friend preFriend = friendRepositoryCustom.findById(friend.getId());
		
		if(preFriend == null){
			friend.setUserId(friend.getUserId());
			friend.setAnotherUserId(friend.getAnotherUserId());
							
			friend = friendRepositoryCustom.save(friend);
			User user = userRepositoryCustom.findOne(friend.getUserId());
			
			OnCreateFriendEvent onCreateFollowEvent = new OnCreateFriendEvent(friend, user);
			activityService.requestUserAcivity(onCreateFollowEvent);
			eventPublisher.publishEvent(onCreateFollowEvent);
			
			return friend;
		}
		
		return preFriend;
		
	}


	@RequestMapping("/{userId}/activities")
    @ResponseBody
    public Page<CustomUserActivityResponse> userActivities(@PathVariable(value = "userId") String userId,
    		Pageable pageable) {
    	
    	List<Friend> friendList = friendRepositoryCustom.findByUserIdOrAnotherUserId(userId, userId);
    	List<String> userIds = new ArrayList<String>();
    	
    	for ( Friend friend : friendList){
    		if(!userIds.contains(friend.getUserId())){
    			userIds.add(friend.getUserId());
    		}
    		if(!userIds.contains(friend.getAnotherUserId())){
    			userIds.add(friend.getAnotherUserId());
    		}
    	}
    	if(!userIds.contains(userId)){
			userIds.add(userId);
		}
  
    	List<UserActivity> userActivities =  userActivityRepositoryCustom.findByUserIds(userIds);
    	
    	List<CustomUserActivityResponse> userActivitiesResponse = new ArrayList<CustomUserActivityResponse>();
    	
    	List<String> listCommentIds = new ArrayList<String>();
    	
    	for (UserActivity userActivity : userActivities){
    		
    		Set<Comment> activityComments = userActivity.getComments();
    		
    		for (Comment activityComment : activityComments){
    			listCommentIds.add(activityComment.getId());
    		}
    	}
    	
		List<Like> userLikes = 
				mongoOperations.find(
						new Query(
								Criteria.where("referenceId").in(listCommentIds)
								.and("userId").is(userId)
								.and("type").is(StatusConfiguration.LIKE_TYPE_COMMENT_VALUE)),
								Like.class);
		
    	
    	for (UserActivity userActivity : userActivities){
    		
    		List<CustomCommentResponse> comments = new ArrayList<CustomCommentResponse>();
    		
    		Set<Comment> activityComments = userActivity.getComments();
    		
    		for (Comment activityComment : activityComments){
    			
    			CustomCommentResponse comment = new CustomCommentResponse(activityComment);
    			
    			for (Like like : userLikes){
    				
    				if((like.getType() == StatusConfiguration.LIKE_TYPE_COMMENT_VALUE) 
    						&& (like.getReferenceId().equals(activityComment.getId()))){
    					comment.setLiked(true);
    				}
    			}

    			comments.add(comment);
    		}
    		
    		CustomUserActivityResponse userActivityResponse = 
    				new CustomUserActivityResponse(userActivity, comments); 
    		
    		userActivitiesResponse.add(userActivityResponse);
    	}
    	
    	 PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
    			 new Sort(Sort.Direction.DESC, "createDate"));
    	 Page<CustomUserActivityResponse> pageImpianto = 
    			 new PageImpl<CustomUserActivityResponse>(userActivitiesResponse, pageRequest, userActivitiesResponse.size()); 
    	
    	return pageImpianto;
    }

}
