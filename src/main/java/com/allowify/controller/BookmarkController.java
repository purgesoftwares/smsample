package com.allowify.controller;

import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.allowify.model.Bookmark;
import com.allowify.model.Code;
import com.allowify.repository.BookmarkRepositoryCustom;
import com.allowify.repository.CodeRepositoryCustom;
import com.allowify.repository.UserRepositoryCustom;


@Controller
@RequestMapping("/bookmark")
public class BookmarkController {
	
	private final UserRepositoryCustom userRepository;
	
	@Autowired
	private final BookmarkRepositoryCustom bookmarkRepository;

	@Autowired
	private final CodeRepositoryCustom codeRepository;
	
	@Autowired
    @Qualifier("mongoTemplate")
    public MongoOperations mongoOperations;

	private final static Logger logger = Logger.getLogger(BookmarkController.class.getName()); 
	
	@Autowired
	public BookmarkController(UserRepositoryCustom userRepository,
			BookmarkRepositoryCustom bookmarkRepository, CodeRepositoryCustom codeRepository) {
		Assert.notNull(bookmarkRepository, "Repository must not be null!");
		Assert.notNull(userRepository, "Repository must not be null!");	
		this.userRepository = userRepository;
		this.bookmarkRepository = bookmarkRepository;
		this.codeRepository = codeRepository;
	}
	

	

	@RequestMapping(method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public Bookmark create(@RequestBody @Valid Bookmark bookmark) {
		
		
		return bookmark;
		
	}
	
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Bookmark get(@PathVariable(value = "userId") String userId) {
		
		Bookmark bookmarks = bookmarkRepository.findByUserId(userId);
		
		return bookmarks;
	}
	
	
	@RequestMapping(value = "/add/{userId}/{code}", method = RequestMethod.POST)
	@ResponseBody
	public Code add(
			@PathVariable(value = "userId") String userId, 
			@PathVariable(value = "code") String rcode)
					throws ClassNotFoundException {
		
		Bookmark bookmark = bookmarkRepository.findByUserId(userId);
		
		if(bookmark == null){
			bookmark = new Bookmark();
			bookmark.setUserId(userId);
		}
		
		List<Code> code = codeRepository.findByCode(rcode);
		
		if(!code.isEmpty()){
			Code preCode = bookmark.getCode(rcode);
			if(preCode==null){
				bookmark.addCode(code.get(0));
				bookmarkRepository.save(bookmark);
			}
			return code.get(0);
		}else{
			throw new ClassNotFoundException("Code not found");
		}
		
		
	}
	
	@RequestMapping(value = "/remove/{userId}/{code}", method = RequestMethod.DELETE)
	@ResponseBody
	public Code remove(
			@PathVariable(value = "userId") String userId, 
			@PathVariable(value = "code") String rcode) throws ClassNotFoundException {
		
		Bookmark bookmark = bookmarkRepository.findByUserId(userId);
		
		List<Code> code = codeRepository.findByCode(rcode);
		
		if(!code.isEmpty()){
			bookmark.removeCode(code.get(0).getId());
			bookmarkRepository.save(bookmark);
			return code.get(0);
		
		}else{
			throw new ClassNotFoundException("Code not found");
		}
		
	}
	
	
	
	@RequestMapping(value = "{userId}", method = RequestMethod.PUT, consumes = {
			MediaType.APPLICATION_JSON_VALUE } )
	@ResponseBody
	public Bookmark update(@PathVariable(value = "userId") String userId) {
		
		Bookmark bookmarks = bookmarkRepository.findByUserId(userId);
		
		return bookmarks;
		
	}
	
	
}
