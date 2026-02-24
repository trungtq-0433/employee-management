package com.example.employee_management.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
			errors.put(error.getField(), error.getDefaultMessage())
		);

		ErrorResponse errorRes = new ErrorResponse(
			HttpStatus.BAD_REQUEST.value(),
			"Data invalid",
			errors
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
		ErrorResponse errorRes = new ErrorResponse(
			HttpStatus.NOT_FOUND.value(),
			ex.getMessage(),
			null
		);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRes);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {

		ErrorResponse errorRes = new ErrorResponse(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			"Internal Server Error!",
			null
		);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRes);
	}
}
