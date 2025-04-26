package org.jsp.DA.exceptionClasses;

public class DuplicateEntryException extends RuntimeException {

	private String message;
	
	DuplicateEntryException(){
		
	}
	
	public DuplicateEntryException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
