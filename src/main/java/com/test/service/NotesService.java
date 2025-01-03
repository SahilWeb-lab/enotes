package com.test.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.dto.NotesDTO;

public interface NotesService {
	
//	public Boolean saveNotes(NotesDTO notesDTO) throws Exception;
	
	public List<NotesDTO> getAllNotes();

	public Boolean saveNotes(String notes, MultipartFile file) throws Exception; 
	
}
