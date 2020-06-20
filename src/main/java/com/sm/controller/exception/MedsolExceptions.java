package com.sm.controller.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sm.exception.InvalidUserNamePasswordException;
import com.sm.exception.UnautorizedAccess;
import com.sm.exception.UserNotFound;
import com.sm.util.ApiResponse;
import com.sm.util.Constants;

@ControllerAdvice
public class MedsolExceptions extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidUserNamePasswordException.class)
	public final ResponseEntity<Object> handleInvalidUserNameOrPAsswordException(InvalidUserNamePasswordException ex,
			WebRequest request) {
		ApiResponse<Object> error = new ApiResponse<>(400, Constants.INVALID_CREDENTIALS, ex.getMessage());
		return new ResponseEntity<Object>(error, HttpStatus.OK);
	}
	@ExceptionHandler(UnautorizedAccess.class)
	public final ResponseEntity<Object> handleUnauthorizedAccessException(UnautorizedAccess ex,
			WebRequest request) {
		ApiResponse<Object> error = new ApiResponse<>(401, Constants.UNAUTHORIZED, Constants.UNAUTHORIZED);
		return new ResponseEntity<Object>(error, HttpStatus.OK);
	}
	@ExceptionHandler(UserNotFound.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFound ex,
			WebRequest request) {
		ApiResponse<Object> error = new ApiResponse<>(404, Constants.USER_NOT_FOUND, Constants.USER_NOT_FOUND);
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}
	
	
	// Parent Exceptions
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ApiResponse<Object> response = new ApiResponse<>(500, Constants.INTERNAL_SERVER_ERROR, details);
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
