/*package org.mindtree.practice.Hotel.Reviews.exceptions;

import org.mindtree.practice.Hotel.Reviews.beans.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AllCustomException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDetails> handleInvalidRestaurantIdException(InvalidRestaurantIdException exception, WebRequest request){
		logger.info("handleInvalidRestaurantIdException in AllCustomException");
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(exception.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OtherException.class)
	public ResponseEntity<ErrorDetails> handleException(OtherException exception, WebRequest request){
		return new ResponseEntity<ErrorDetails>(new ErrorDetails(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
*/