package com.viraj.bansode.requestvalidator.Exception;

public class RequestResponseCommon {
	
	private String Id;
	private String type;
	private Object attribute;
	
	public RequestResponseCommon(String id, String type, Object attribute) {
		super();
		Id = id;
		this.type = type;
		this.attribute = attribute;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getAttribute() {
		return attribute;
	}
	public void setAttribute(Object attribute) {
		this.attribute = attribute;
	}
	

}
