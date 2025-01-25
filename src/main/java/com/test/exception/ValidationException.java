package com.test.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {
	private Map<String, Object> errors;

	public ValidationException(Map<String, Object> errors) {
		super("Validation Failed!");
		this.errors = errors;
	}

	public Map<String, Object> getErrors() {
		return errors;
	}	
	
}
