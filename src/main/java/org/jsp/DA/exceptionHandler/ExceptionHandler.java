package org.jsp.DA.exceptionHandler;

import org.jsp.DA.entity.User;
import org.jsp.DA.exceptionClasses.DuplicateEntryException;
import org.jsp.DA.exceptionClasses.NOUserFoundException;
import org.jsp.DAutil.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<ResponseStructure<User>> duplicataDataEntry(DuplicateEntryException e) {

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.BAD_REQUEST.value())
				.message("The given email is already registered").body(e.getMessage()).build();
		
		ResponseEntity re = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rs);
		
		return re;
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NOUserFoundException.class)
	public ResponseEntity<ResponseStructure<User>> noUserFoundException(NOUserFoundException e) {

		ResponseStructure rs = ResponseStructure.builder().status(HttpStatus.BAD_REQUEST.value())
				.message("No User Found").body(e.getMessage()).build();
		
		ResponseEntity re = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rs);
		
		return re;
	}
}
