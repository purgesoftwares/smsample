package com.allowify.response;

import java.util.List;

import com.allowify.model.Code;
import com.allowify.model.CodeEvent;

public class CodeNearWithCodeEventResponse {
	
	private final List<Code> codes;
	
	private final List<CodeEvent> events;
	
	public CodeNearWithCodeEventResponse(List<Code> codes, List<CodeEvent> events) {
		
		this.codes = codes;
		this.events = events;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public List<CodeEvent> getEvents() {
		return events;
	}

}
