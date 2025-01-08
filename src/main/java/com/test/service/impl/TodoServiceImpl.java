package com.test.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.dto.TodoDTO;
import com.test.dto.TodoDTO.StatusDTO;
import com.test.enums.TodoStatus;
import com.test.exception.ResourceNotFoundException;
import com.test.model.Todo;
import com.test.repository.TodoRepository;
import com.test.service.TodoService;
import com.test.util.Validation;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveTodo(TodoDTO todoDTO) {
		
//		Todo status validation:
		validation.todoValidation(todoDTO);
		
		Todo todo = modelMapper.map(todoDTO, Todo.class);
		todo.setStatus(todoDTO.getStatus().getId());
		Todo saveTodo = todoRepository.save(todo);
		
		if(ObjectUtils.isEmpty(saveTodo)) {
			return false;
		}
		
		return true;
	}

	@Override
	public TodoDTO getTodoById(Integer todoId) throws Exception {
		Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo with id ["+ todoId +"] not found!"));
		
		TodoDTO todoDTO = modelMapper.map(todo, TodoDTO.class);
		
//		Create a method to setStatus:
		setStatus(todoDTO, todo);
		
		return todoDTO;
	}
	
	private void setStatus(TodoDTO todoDto, Todo todo) {
		TodoStatus[] todoStatus = TodoStatus.values();
		
		for(TodoStatus st : todoStatus) {
			if(st.getId().equals(todo.getStatus())) {
				StatusDTO statusDTO = StatusDTO.builder()
						.id(st.getId())
						.status(st.getStatus())
						.build();
				
				todoDto.setStatus(statusDTO);
			}
		}
	}

	@Override
	public List<TodoDTO> getTodoByUser() {
		Integer userId = 2;
		
		List<Todo> todos = todoRepository.findByCreatedBy(userId);
		
		List<TodoDTO> todoDTOs = todos.stream().map(todo -> modelMapper.map(todo, TodoDTO.class)).toList();
		
		return todoDTOs;
	}

}
