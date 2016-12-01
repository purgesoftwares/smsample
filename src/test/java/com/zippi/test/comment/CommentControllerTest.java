package com.zippi.test.comment;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.allowify.model.Comment;
import com.allowify.repository.CommentRepositoryCustom;
import com.zippi.test.ZippiTestSuite;

public class CommentControllerTest extends ZippiTestSuite {
	
	@Autowired
	private CommentRepositoryCustom commentRepositoryCustom;
	
	@Test
	public void addTestComment() throws Exception {
		Comment responseComment = createComment();
		
		Comment queriedComment = commentRepositoryCustom.findByReferenceIdAndUserId(
				responseComment.getReferenceId(),responseComment.getUserId());
		
		assertEquals(responseComment.getId(), queriedComment.getId());
	}
	
	private Comment createComment() {
		Comment testComment = new Comment();
		testComment.setComment("Test comment");
		testComment.setUserId("56c714f9e4b0afcc62bc03df");
		testComment.setReferenceId("56afdf61");
		testComment.setCreateDate(new Date());
					
		return restTemplate.postForObject(commentURL.toString(), testComment, Comment.class);
	}
	
	@Test
	public void deleteTestComment() {
		Comment responseComment = createComment();
		restTemplate.delete(commentURL.toString()+"/"+responseComment.getId(), responseComment,Comment.class);
	
	}
	
	@Test
	public void findTestComment() {
		Comment responseComment = createComment();
		
		Comment queriedComment = commentRepositoryCustom.findByReferenceIdAndUserId(
				responseComment.getReferenceId(),responseComment.getUserId());
		
		assertNotNull(queriedComment);	
		
	}
	
	@Test
	public void findAllTestComment() {
		Comment responseTest1Comment = createComment();
		
		Comment responseTest2Comment = new Comment();
		responseTest2Comment.setComment("Test 2 Comment");
		responseTest2Comment.setUserId("56c7005de4b0445b9508a235");
		responseTest2Comment.setReferenceId("56443550");
		responseTest2Comment.setCreateDate(new Date());
		responseTest2Comment = restTemplate.postForObject
				(commentURL.toString(), responseTest2Comment, Comment.class);
		
		ArrayList<Comment> listOfcomments = new ArrayList<Comment>();
		listOfcomments.add(responseTest1Comment);
		listOfcomments.add(responseTest2Comment);
		
		for(int i=0; i<listOfcomments.size(); i++){
			Comment returnComment = commentRepositoryCustom.findByReferenceIdAndUserId
					(listOfcomments.get(i).getReferenceId(), listOfcomments.get(i).getUserId());
			
			assertNotNull(returnComment);
		}
	}
	
}
