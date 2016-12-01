package com.zippi.test.bookmark;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.allowify.model.Bookmark;
import com.allowify.model.Code;
import com.allowify.model.User;
import com.allowify.repository.BookmarkRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.zippi.test.ZippiTestSuite;

public class BookmarkControllerTest extends ZippiTestSuite {
	
	@Autowired
	private BookmarkRepositoryCustom bookmarkRepositoryCustom;
	
	@Autowired
	private CodeRepositoryCustom codeRepositoryCustom;
	
		
	@Test
	public void testBookmarkAdd() throws Exception {
		
		User user = new User();
		
		Bookmark addBookmark = new Bookmark();
		addBookmark.setUserId(user.getId());
		
		List<Code> code = codeRepositoryCustom.findByCode("00CXE");
		Code responseBookmark = restTemplate.postForObject
				(bookmarkURL.toString()+ "/add"+"/"+addBookmark.getUserId()+"/"+code.get(0).getCode(),
						addBookmark, Code.class);
		
		assertNotNull(responseBookmark);
		
	}
	
	@Test
	public void testGetBookmark() throws Exception {
		User user = new User();
		
		Bookmark addBookmark = new Bookmark();
		addBookmark.setUserId(user.getId());
		List<Code> code = codeRepositoryCustom.findByCode("00CXE");
		Code responseBookmarkk = restTemplate.postForObject
				(bookmarkURL.toString()+ "/add"+"/"+addBookmark.getUserId()+"/"+code.get(0).getCode(),
						addBookmark, Code.class);
		
					
		Bookmark responseBookmark = bookmarkRepositoryCustom.findByUserId(responseBookmarkk.getId());
		
		assertNotNull(responseBookmark);
		
	}
	
	@Test
	public void testRemoveBookmark() throws Exception {
		User user = new User();
		
		Bookmark addBookmark = new Bookmark();
		addBookmark.setUserId(user.getId());
		List<Code> code = codeRepositoryCustom.findByCode("00CXE");
		Code responseBookmarkk = restTemplate.postForObject
				(bookmarkURL.toString()+ "/add"+"/"+addBookmark.getUserId()+"/"+code.get(0).getCode(),
						addBookmark, Code.class);
		
		System.out.println(responseBookmarkk.getUserId());
		
		restTemplate.delete(bookmarkURL.toString()+"/remove"+"/"+addBookmark.getUserId()
				+"/"+responseBookmarkk.getCode());
		
		Bookmark responseBookmark = bookmarkRepositoryCustom.findByUserId(addBookmark.getUserId());
		assertNull(responseBookmark);
		
	}
	
	
}
