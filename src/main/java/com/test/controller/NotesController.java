package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.dto.FavouriteNoteDTO;
import com.test.dto.NotesDTO;
import com.test.dto.NotesResponse;
import com.test.endpoint.NotesEndpoint;
import com.test.model.FileDetails;
import com.test.service.NotesService;
import com.test.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NotesController implements NotesEndpoint {
	
	@Autowired
	private NotesService notesService;
	
	@Override
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception {
		log.info("NotesController : saveNotes() : Execution Start");
		Boolean saveNotes = notesService.saveNotes(notes, file);
		
		if(!saveNotes) {
			log.info("Error : Notes not saved!");
			return CommonUtils.createErrorResponseMessage("Notes not saved!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("NotesController : saveNotes() : Execution End");
		return CommonUtils.createBuildResponseMessage("Notes saved successfully!", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> getNotes() {
		List<NotesDTO> allNotes = notesService.getAllNotes();
		
		if(ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		
		String contentType = CommonUtils.getContentType(fileDetails.getOriginalFileName());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	@Override
	public ResponseEntity<?> getAllNotesByUser(
				@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
				@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
			) {
		NotesResponse allNotesByUser = notesService.getAllNotesByUser(pageNo, pageSize);
		
		return CommonUtils.createBuildResponse(allNotesByUser, HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {
		notesService.softDeleteNotes(id);
		return CommonUtils.createBuildResponseMessage("Notes deleted successfully!", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
		notesService.restoreNotes(id);
		return CommonUtils.createBuildResponseMessage("Notes restored successfully!", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() {
		Integer userId = 2;
		List<NotesDTO> binNotes = notesService.getUserRecycleBinNotes();
		
		if(ObjectUtils.isEmpty(binNotes)) {
			return CommonUtils.createBuildResponseMessage("Recycle bin is empty!", HttpStatus.OK);
		}
		
		return CommonUtils.createBuildResponse(binNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
		notesService.hardDelete(id);
		return CommonUtils.createBuildResponseMessage("Notes deleted successfully!", HttpStatus.OK);
	}
	
//	Create a handler to empty the recycle bin:
	@Override
	public ResponseEntity<?> emptyRecycleBin() {
		Integer userId = CommonUtils.getLoggedInUser().getId();
		notesService.emptyRecycleBin();
		
		return CommonUtils.createBuildResponseMessage("Recycle bin is empty!", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> addFavourite(@PathVariable Integer noteId) throws Exception {
		notesService.favouriteNotes(noteId);
		return CommonUtils.createBuildResponseMessage("Notes added to favourite!", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> removeFavourite(@PathVariable Integer favNoteId) throws Exception {
		notesService.unFavouriteNotes(favNoteId);
		return CommonUtils.createBuildResponseMessage("Notes removed from favourite!", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getFavouriteNotes() throws Exception {
		List<FavouriteNoteDTO> favouriteNotes = notesService.getUserFavouriteNotes();
		
		if(CollectionUtils.isEmpty(favouriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtils.createBuildResponse(favouriteNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> copyNotes(@PathVariable Integer noteId) throws Exception {
		Boolean copyNotes = notesService.copyNotes(noteId);
		
		if(copyNotes) {			
			return CommonUtils.createBuildResponseMessage("Notes copied success!", HttpStatus.OK);
		}
		
		return CommonUtils.createBuildResponseMessage("Failed to copy!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	Create a handler to search the notes:
	@Override
	public ResponseEntity<?> searchNotes(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		
		 NotesResponse notes = notesService.searchNotesByUser(pageNo, pageSize, keyword);
		 return CommonUtils.createBuildResponse(notes, HttpStatus.OK);
	}
	
}
