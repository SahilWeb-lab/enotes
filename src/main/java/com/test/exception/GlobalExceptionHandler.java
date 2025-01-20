package com.test.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.util.CommonUtils;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<?> handleException(Exception exception) {
		return CommonUtils.createErrorResponseMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(exception = NullPointerException.class)
	public ResponseEntity<?> nullPointerException(Exception exception) {
		System.out.println("NullPointerException us Running....");
		return CommonUtils.createErrorResponseMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(exception = ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception) {
		return CommonUtils.createErrorResponseMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
//	public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//		
//		List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
//		
//		Map<String, Object> errors = new LinkedHashMap<>();
//		
//		allErrors.stream().forEach(e -> {
//			String msg = e.getDefaultMessage();
//			String field = ((FieldError) (e)).getField();
//			errors.put(field, msg);
//		});
//		
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(exception = ValidationException.class)
	public ResponseEntity<?> ValidationException(ValidationException exception) {
		return CommonUtils.createErrorResponse(exception.getErrors(), HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(exception.getErrors(), HttpStatus.BAD_REQUEST);
	}
	
//	Already Exist Exception Handler:
	@ExceptionHandler(exception = ExistDataException.class)
	public ResponseEntity<?> ExistDataException(ExistDataException exception) {
		return CommonUtils.createErrorResponseMessage(exception.getMessage(), HttpStatus.CONFLICT);
//		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(exception = HttpMessageNotReadableException.class)
	public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		return CommonUtils.createErrorResponseMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = FileNotFoundException.class)
	public ResponseEntity<?> FileNotFoundException(FileNotFoundException exception) {
		return CommonUtils.createErrorResponseMessage(exception.getMessage(), HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(exception = IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception) {
		return CommonUtils.createErrorResponseMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);

	}
	
}
