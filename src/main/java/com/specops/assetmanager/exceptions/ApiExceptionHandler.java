package com.specops.assetmanager.exceptions;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.hibernate.id.IdentifierGenerationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		StringBuilder message = new StringBuilder();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			
			message.append("<p>"+error.getDefaultMessage()+"</p>");
		});
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				message.toString(),
				HttpStatus.BAD_REQUEST,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		
		return new ResponseEntity<Object>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ApiRequestException.class)
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
		// 1. Create payload containing exception details
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		//2. Return response entity
		return new ResponseEntity<>(apiException, badRequest);
	}
	
	@ExceptionHandler(IllegalQueryFormatException.class)
	public ResponseEntity<Object> handleIllegalQueryFormatException(IllegalQueryFormatException e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, badRequest);
	}
	
	@ExceptionHandler(InvalidJwtException.class)
	public ResponseEntity<Object> handleInvalidJwtException(InvalidJwtException e){
System.out.println("555555555555555");
		HttpStatus unauthorizedRequest = HttpStatus.UNAUTHORIZED;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				unauthorizedRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, unauthorizedRequest);
	}
	
	@ExceptionHandler(NoOfficerFoundException.class)
	public ResponseEntity<Object> handleNoOfficerFoundException(NoOfficerFoundException e){
		HttpStatus notFound = HttpStatus.NOT_FOUND;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				notFound,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, notFound);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Object> handleSQLException(SQLException e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, badRequest);
	}
	
	@ExceptionHandler(IdentifierGenerationException.class)
	public ResponseEntity<Object> handleIdentifierGenerationException(IdentifierGenerationException e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, badRequest);
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				badRequest,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, badRequest);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e){
		HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
		
		ApiExceptionFormatter apiException = new ApiExceptionFormatter(
				e.getMessage(),
				unauthorized,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		
		return new ResponseEntity<>(apiException, unauthorized);
	}
	
}
