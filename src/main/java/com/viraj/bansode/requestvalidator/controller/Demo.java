package com.viraj.bansode.requestvalidator.controller;

import java.io.Serializable;

public class Demo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5390662110673750020L;
	
	private String name;
	private Integer id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Demo [name=" + name + ", id=" + id + "]";
	}
	
	
}
