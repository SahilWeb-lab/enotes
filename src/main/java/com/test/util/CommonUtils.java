package com.test.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.test.handle.GenericResponse;

public class CommonUtils {
	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
		System.out.println("Error message is called!");
		GenericResponse response = GenericResponse.builder().message("success").data(data).responseStatus(status)
				.status("success").build();

		return response.create();
	}

	public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().message(message).responseStatus(status).status("success")
				.build();

		return response.create();
	}

	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().message("Failed").status("Failed").data(data)
				.responseStatus(status).build();

		return response.create();
	}

	public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().message(message).responseStatus(status).status("Failed")
				.build();

		return response.create();
	}

	public static String getContentType(String originalFileName) {

		String extension = FilenameUtils.getExtension(originalFileName);

		switch (extension) {
		case "pdf":
			return "application/pdf";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
		case "txt":
			return "text/plan";
		case "png":
			return "image/png";
		case "jpeg":
			return "image/jpeg";
		default:
			return "application/octet-stream";
		}
	}
}
