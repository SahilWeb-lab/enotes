package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.TodoDTO;
import com.test.service.TodoService;
import com.test.util.CommonUtils;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDTO todoDTO) {
		Boolean saveTodo = todoService.saveTodo(todoDTO);
		
		if(saveTodo) {
			return CommonUtils.createBuildResponseMessage("Todo saved successfully!", HttpStatus.OK);
		} else {
			return CommonUtils.createBuildResponseMessage("Failed to save!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTodo(@PathVariable Integer id) throws Exception {
		TodoDTO todoDTO = todoService.getTodoById(id);
		return CommonUtils.createBuildResponse(todoDTO, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllTodos() {
		List<TodoDTO> todoByUser = todoService.getTodoByUser();
		
		if(CollectionUtils.isEmpty(todoByUser)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtils.createBuildResponse(todoByUser, HttpStatus.OK);
		
	}

}
