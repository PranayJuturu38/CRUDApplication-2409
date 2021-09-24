package com.crudapp.main.ApiResponse;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response {
	private Object results;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		if (results instanceof ArrayList<?> && ((ArrayList<?>) results).size() == 1) {
			results = ((ArrayList<?>) results).get(0);
		}
		this.results = results;
	}

	public Response() {
		super();
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}