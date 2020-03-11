package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.response.ExceptionResponse;
/**
 * Class for handle the Label Exception 
 * @author Srijan Kumar
 *
 */

@ControllerAdvice
public class LabelExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(LabelException.class)
	public final ResponseEntity<ExceptionResponse> allException(UserException ex) {

		ExceptionResponse exp = new ExceptionResponse();
		exp.setMessage(ex.getMessage());
		exp.setCode(ex.getHttpstatus());
		return ResponseEntity.status(exp.getCode()).body(new ExceptionResponse(exp.getMessage(), exp.getCode()));

	}

}
