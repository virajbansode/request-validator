package com.viraj.bansode.requestvalidator.Exception;

import java.util.List;

public class Error {
	
	private String id;
	private String description; 	
	private List<ErrorDetails> errors;
	
	public String getId() {
		return id;
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
	
	public List<ErrorDetails> getErrors() {
		return errors;
	}
	public void setErrors(List<ErrorDetails> errors) {
		this.errors = errors;
	}
}
