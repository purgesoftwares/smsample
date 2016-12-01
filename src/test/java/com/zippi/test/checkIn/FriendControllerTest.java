package com.zippi.test.checkIn;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.allowify.model.Friend;
import com.allowify.repository.FriendRepositoryCustom;
import com.zippi.test.ZippiTestSuite;

public class FriendControllerTest extends ZippiTestSuite {
	
	@Autowired
	private FriendRepositoryCustom friendRepositoryCustom;
	
	@Test
	public void testAddFriend() throws Exception {
		Friend quriedFriend = addFriend();
		
		Friend responseFriend = friendRepositoryCustom.findById(quriedFriend.getId());
		
		assertEquals(quriedFriend.getId(), responseFriend.getId());
		
	}
	
	private Friend addFriend() {
		Friend friend = new Friend();
		friend.setUserId("56cec1e7e4b06aa16f6fc948");
		friend.setAnotherUserId("56cec1f0e4b06aa16f6fc94a");
		
		return restTemplate.postForObject(friendURL.toString()+ "/create", friend, Friend.class);
	}

}
