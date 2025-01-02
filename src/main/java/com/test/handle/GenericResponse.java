package com.test.handle;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse {
	
	private HttpStatus responseStatus; // For Status Code
	
	private String status;	// Success or Failure
	
	private String message; // Printing message
	
	private Object data;	// Displaying data
	
	public ResponseEntity<?> create() {
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", status);
		map.put("message", message);
		
		if(!ObjectUtils.isEmpty(data)) {
			map.put("data", data);
		}
		
		return new ResponseEntity<>(map, responseStatus);
	}
}
