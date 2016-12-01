package com.allowify.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserActivity implements Serializable, Runnable {
	
	public UploadedFile getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(UploadedFile uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	private static final long serialVersionUID = 2L;
	
	@Id
	private String id;
	
	private String description;
	
	private String title;
	
	private Date createDate;
	
	private Date updateDate;
	
	private String userId;
		
	@DBRef
	private User user;
	
	@DBRef
	private Code code;
	
	@DBRef
	private Like like;
	
	@DBRef
	private Review review;
	
	@DBRef
	private CodeEvent codeEvent;
	
	@DBRef
	private CheckIn checkIn;
	
	@DBRef
	private EventInterested eventInterested;
	
	@DBRef
	private UploadedFile uploadedFiles;
	
	@DBRef
	private Friend friend;
	
	private int likesCount;
	
	private String type;
	
	@DBRef
	private UserActivity userActivity;
	
	private int shareCounts;
	
	public UserActivity getUserActivity() {
		return userActivity;
	}

	public void setUserActivity(UserActivity userActivity) {
		this.userActivity = userActivity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	public EventInterested getEventInterested() {
		return eventInterested;
	}

	public void setEventInterested(EventInterested eventInterested) {
		this.eventInterested = eventInterested;
	}

	public CheckIn getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(CheckIn checkIn) {
		this.checkIn = checkIn;
	}

	public CodeEvent getCodeEvent() {
		return codeEvent;
	}

	public void setCodeEvent(CodeEvent codeEvent) {
		this.codeEvent = codeEvent;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Like getLike() {
		return like;
	}

	public void setLike(Like like) {
		this.like = like;
	}

	@DBRef
	private Set<Comment> comments = new HashSet<Comment>();
	
	public String getId() {
		return id;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comment) {
		this.comments = comment;
	}
	
	public Comment getComment(String commentString) {
		
		Comment comment = null;
		Iterator<Comment> commentIterator = comments.iterator();
		
		while(commentIterator.hasNext()) {
			Comment tmpCode = commentIterator.next();
			if(commentString.equals(tmpCode.getComment())) {
				comment = tmpCode;
				break;
			}
		}		
		return comment;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the shareCounts
	 */
	public int getShareCounts() {
		return shareCounts;
	}

	/**
	 * @param shareCounts the shareCounts to set
	 */
	public void setShareCounts(int shareCounts) {
		this.shareCounts = shareCounts;
	}

	
	
}
