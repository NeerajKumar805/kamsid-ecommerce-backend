package com.example.ecommerce.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
		ApiResponse<Object> response = new ApiResponse<>(false, ex.getReason(), null);
		return new ResponseEntity<>(response, ex.getStatusCode());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		String message = "Duplicate value entered or data integrity violation";

		if (ex.getRootCause() != null) {
			String cause = ex.getRootCause().getMessage().toLowerCase();
			if (cause.contains("mobile") || cause.contains("mobile_no"))
				message = "Mobile number already exists";
			else if (cause.contains("email"))
				message = "Email already exists";
		}

		return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String name = ex.getName();
		String type = ex.getRequiredType().getSimpleName();
		String value = ex.getValue().toString();
		String message = String.format("'%s' should be of type %s. Invalid value: '%s'", name, type, value);
		return new ResponseEntity<>(new ApiResponse<>(false, message, null), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
		return new ResponseEntity<>(new ApiResponse<>(false, "Something went wrong", null),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
