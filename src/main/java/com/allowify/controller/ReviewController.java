package com.allowify.controller;

import java.util.Date;

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

import com.allowify.model.Review;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.User;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.ReviewRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;
import com.allowify.service.ActivityService;
import com.zippi.events.code.OnCreateReviewEvent;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewRepositoryCustom reviewsRepositoryCustom;
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;

	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Review createReviews(@RequestBody Review reviews) {
		
		reviews.setTitle(reviews.getTitle());
		reviews.setDescription(reviews.getDescription());
		reviews.setUserId(reviews.getUserId());
		reviews.setCode(reviews.getCode());
		reviews.setStatus(StatusConfiguration.ENABLE);
		reviews.setRating(reviews.getRating());
		reviews.setCreated(new Date());
		reviews.setUpdated(new Date());
		reviews = reviewsRepositoryCustom.save(reviews);
		User user = userRepositoryCustom.findOne(reviews.getUserId());
		
		OnCreateReviewEvent onCreateReviewEvent =  new OnCreateReviewEvent(reviews, user);
		activityService.requestUserAcivity(onCreateReviewEvent);
		eventPublisher.publishEvent(onCreateReviewEvent);
		return reviews;
	}
	

	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public  @ResponseBody Review updateReview(@PathVariable("id") String id,
								 @RequestBody Review reviews) {
		
		Review preReview = reviewsRepositoryCustom.findById(id);
		
		if(preReview != null) {
			
			preReview.setCode(reviews.getCode());
			preReview.setDescription(reviews.getDescription());
			preReview.setTitle(reviews.getTitle());
			preReview.setRating(reviews.getRating());
			preReview.setStatus(StatusConfiguration.EDITED);
			preReview.setUpdated(new Date());
			
			reviewsRepositoryCustom.save(preReview);
			
			return preReview;
		}
		
		return null;
	}
}
