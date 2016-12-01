package com.zippi.test.checkIn;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.allowify.model.Review;
import com.allowify.model.StatusConfiguration;
import com.allowify.repository.ReviewRepositoryCustom;
import com.zippi.test.ZippiTestSuite;

public class ReviewsControllerTest extends ZippiTestSuite {
	
	@Autowired
	private ReviewRepositoryCustom reviewsRepositoryCustom;
	
	@Test
	public void testAddReviews() throws Exception {
		Review addReviews = createReviews();
		
		Review quriedReviews = reviewsRepositoryCustom.findById(addReviews.getId());
		
		assertEquals(addReviews.getId(), quriedReviews.getId());
	}
	
	private Review createReviews() {
		Review reviews = new Review();
		reviews.setTitle("Test Title For Reviews");
		reviews.setDescription("Test Title Description");
		reviews.setUserId("56cefebbe4b03a5be76f4222");
		reviews.setCode("00CXW");
		reviews.setStatus(StatusConfiguration.ENABLE);
		reviews.setCreated(new Date());
		reviews.setUpdated(new Date());
		
		return restTemplate.postForObject(
				reviewsURL.toString()+"/create", reviews, Review.class);
	}
	
	@Test
	public void testUpdateReviews() throws Exception {
		Review addReviews = createReviews();
		
		Review quriedReviews = reviewsRepositoryCustom.findById(addReviews.getId());
		if(quriedReviews != null) {
			quriedReviews.setTitle("Test Title For Reviews Updated");
			quriedReviews.setDescription("Test Title Description Updated");
			quriedReviews.setUserId("56cefebbe4b03a5be76f4222");
			quriedReviews.setCode("00CXX");
			quriedReviews.setStatus(StatusConfiguration.EDITED);
			quriedReviews.setUpdated(new Date());
			quriedReviews = reviewsRepositoryCustom.save(quriedReviews);
		}
		
		assertNotEquals(addReviews.getCode(), quriedReviews.getCode());
		assertNotEquals(addReviews.getTitle(), quriedReviews.getTitle());
	}

}
