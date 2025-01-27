package com.test.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.dto.TodoDTO;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	@PostMapping("/save")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDTO todoDTO);
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTodo(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllTodos();
	
}
