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
	
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDTO> getUserRecycleBinNotes(Integer userId);

	public void hardDelete(Integer id) throws Exception;

	public void emptyRecycleBin(Integer userId);
	
	public void favouriteNotes(Integer noteId) throws Exception;
	
	public void unFavouriteNotes(Integer noteId) throws Exception;
	
	public List<FavouriteNoteDTO> getUserFavouriteNotes();

	public Boolean copyNotes(Integer noteId) throws Exception;
	
}
