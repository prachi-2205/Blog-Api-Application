package com.prachi.blog_api.exceptions;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String filedName;
	long filedValue;
	String uname=null;
	public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue)
	{
		super(String.format("%s not found with %s:%s", resourceName,fieldName,fieldValue));
		this.resourceName=resourceName;
		this.filedName=fieldName;
		this.filedValue=fieldValue;
	}
	public ResourceNotFoundException(String resourceName,String fieldName,String fieldValue)
	{
		super(String.format("%s not found with %s:%s", resourceName,fieldName,fieldValue));
		this.resourceName=resourceName;
		this.filedName=fieldName;
		this.uname=fieldValue;
	}
	
}
