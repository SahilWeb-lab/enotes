package com.test.service;

import java.util.List;

import com.test.dto.TodoDTO;

public interface TodoService {
	
	public Boolean saveTodo(TodoDTO todoDTO);
	
	public TodoDTO getTodoById(Integer todoId) throws Exception;
	
	public List<TodoDTO> getTodoByUser();
	
}
