package org.jsp.DA.exceptionClasses;

public class NOUserFoundException extends RuntimeException{

	private String message;
	
	NOUserFoundException(){
		
	}
	public NOUserFoundException(String message) {
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}
}
