package org.mindtree.practice.Hotel.Reviews.exceptions;

import org.mindtree.practice.Hotel.Reviews.beans.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class AllCustomException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(InvalidRestaurantIdException.class)
	public ResponseEntity<ErrorDetails> handleInvalidRestaurantIdException(InvalidRestaurantIdException exception, WebRequest request){
		System.out.println("handleInvalidRestaurantIdException sysout is ");
		logger.info("handleInvalidRestaurantIdException in AllCustomException");
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(exception.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
}
