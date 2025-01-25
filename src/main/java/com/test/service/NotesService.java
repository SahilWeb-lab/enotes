package com.test.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.dto.FavouriteNoteDTO;
import com.test.dto.NotesDTO;
import com.test.dto.NotesResponse;
import com.test.exception.ResourceNotFoundException;
import com.test.model.FileDetails;

public interface NotesService {
	
//	public Boolean saveNotes(NotesDTO notesDTO) throws Exception;
	
	public List<NotesDTO> getAllNotes();

	public Boolean saveNotes(String notes, MultipartFile file) throws Exception; 
	
	public byte[] downloadFile(FileDetails fileDetails) throws Exception;
	
	public FileDetails getFileDetails(Integer id) throws Exception;
	
	public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize);
	
//	Create a method to search notes by user:
	public NotesResponse searchNotesByUser(Integer pageNo, Integer pageSize, String keyword);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDTO> getUserRecycleBinNotes();

	public void hardDelete(Integer id) throws Exception;

	public void emptyRecycleBin();
	
	public void favouriteNotes(Integer noteId) throws Exception;
	
	public void unFavouriteNotes(Integer noteId) throws Exception;
	
	public List<FavouriteNoteDTO> getUserFavouriteNotes();

	public Boolean copyNotes(Integer noteId) throws Exception;
	
}
