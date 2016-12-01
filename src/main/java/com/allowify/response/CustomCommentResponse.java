/**
 * 
 */
package com.allowify.response;

import com.allowify.model.Comment;

/**
 * @author shankarp
 *	wrapper for comment
 */
public class CustomCommentResponse {

	private final Comment comment;
	
	private boolean isLiked; 

	public CustomCommentResponse(Comment comment) {
		this.comment = comment;
		this.isLiked = false;
	}
	
	public CustomCommentResponse(Comment comment, boolean isLiked) {
		this.comment = comment;
		this.isLiked = isLiked;
	}

	public Comment getComment() {
		return comment;
	}

	/**
	 * @return the isLiked
	 */
	public boolean isLiked() {
		return isLiked;
	}

	/**
	 * @param isLiked the isLiked to set
	 */
	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	
}
