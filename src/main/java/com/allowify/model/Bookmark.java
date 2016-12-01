package com.allowify.model;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bookmark {

	@Id 
	private String userId;
	
	@DBRef
	private Set<Code> codes = new HashSet<Code>();
	
	public Bookmark() {
		
    }
	
	public Bookmark(String userId, Set<Code> codes) {
        this.userId = userId;
        this.codes = codes;
    }
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<Code> getCodes() {
		return this.codes;
	}

	public void setCodes(Set<Code> codes) {
		this.codes = codes;
	}

	public Code getCode(String codeString) {
		
		Code code = null;
		Iterator<Code> codeIterator = codes.iterator();
		
		while(codeIterator.hasNext()) {
			Code tmpCode = codeIterator.next();
			if(codeString.equals(tmpCode.getCode())) {
				code = tmpCode;
				break;
			}
		}		
		return code;
	}
	
	public Code removeCode(String codeID) {
		Code code = null;
		Iterator<Code> codeIterator = codes.iterator();
		
		while(codeIterator.hasNext()) {
			Code tmpCode = codeIterator.next();
			if(codeID.equals(tmpCode.getId())) {
				code = tmpCode;
				codeIterator.remove();
				break;
			}
		}		
		return code;
	}
	
	public void addCode(Code code) {
		codes.add(code);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Card [userId=" + userId + "]";
	}

}
