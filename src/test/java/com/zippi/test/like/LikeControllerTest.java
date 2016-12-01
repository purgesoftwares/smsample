package com.zippi.test.like;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.allowify.model.Like;
import com.allowify.repository.LikeRepositoryCustom;
import com.zippi.test.ZippiTestSuite;

public class LikeControllerTest extends ZippiTestSuite{
	
	@Autowired
	private LikeRepositoryCustom likeRepositoryCustom;
	
	@Test
	public void addTestLike() {
		Like addLike = addCreateLike();
		
		Like quriedLike = likeRepositoryCustom.findByReferenceIdAndUserIdAndType
				(addLike.getReferenceId(),addLike.getUserId(),addLike.getType()); 
		
		assertEquals(addLike.getId(), quriedLike.getId());
		
	}
	
	private Like addCreateLike() {
		Like addLike = new Like();
		addLike.setType(3);
		addLike.setReferenceId("56cbec22e4b0b8cb7145dda9");
		addLike.setUserId("56c714f9e4b0afcc62bc03df");
		
		return restTemplate.postForObject(likeURL.toString(), addLike, Like.class);
	}

	@Test
	public void deleteTestLike() {
		Like addLike = addCreateLike();
		restTemplate.delete(likeURL.toString()+"/"+addLike.getId(), addLike, Like.class);
	}
	
}
