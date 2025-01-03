package com.test.service;

import java.util.List;

import com.test.dto.NotesDTO;

public interface NotesService {
	
	public Boolean saveNotes(NotesDTO notesDTO) throws Exception;
	
	public List<NotesDTO> getAllNotes(); 
	
}
