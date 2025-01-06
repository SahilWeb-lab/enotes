package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.test.model.FileDetails;
import com.test.service.NotesService;
import com.test.util.CommonUtils;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/save-notes")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception {
		Boolean saveNotes = notesService.saveNotes(notes, file);
		
		if(saveNotes) {
			return CommonUtils.createBuildResponseMessage("Notes saved successfully!", HttpStatus.CREATED);
		}
		
		return CommonUtils.createErrorResponseMessage("Notes not saved!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getNotes() {
		List<NotesDTO> allNotes = notesService.getAllNotes();
		
		if(ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		
		String contentType = CommonUtils.getContentType(fileDetails.getOriginalFileName());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(
				@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
				@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
			) {
		Integer userId = 2;
		NotesResponse allNotesByUser = notesService.getAllNotesByUser(userId, pageNo, pageSize);
		
		return CommonUtils.createBuildResponse(allNotesByUser, HttpStatus.OK);
	}
	
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {
		notesService.softDeleteNotes(id);
		return CommonUtils.createBuildResponseMessage("Notes deleted successfully!", HttpStatus.OK);
	}
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
		notesService.restoreNotes(id);
		return CommonUtils.createBuildResponseMessage("Notes restored successfully!", HttpStatus.OK);
	}
	
	@GetMapping("/recycle-notes")
	public ResponseEntity<?> getUserRecycleBinNotes() {
		Integer userId = 2;
		List<NotesDTO> binNotes = notesService.getUserRecycleBinNotes(userId);
		
		if(ObjectUtils.isEmpty(binNotes)) {
			return CommonUtils.createBuildResponseMessage("Recycle bin is empty!", HttpStatus.OK);
		}
		
		return CommonUtils.createBuildResponse(binNotes, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
		notesService.hardDelete(id);
		return CommonUtils.createBuildResponseMessage("Notes deleted successfully!", HttpStatus.OK);
	}
	
//	Create a handler to empty the recycle bin:
	@DeleteMapping("/delete")
	public ResponseEntity<?> emptyRecycleBin() {
		Integer userId = 2;
		notesService.emptyRecycleBin(userId);
		
		return CommonUtils.createBuildResponseMessage("Recycle bin is empty!", HttpStatus.OK);
	}
	
	@GetMapping("/fav/{noteId}")
	public ResponseEntity<?> addFavourite(@PathVariable Integer noteId) throws Exception {
		notesService.favouriteNotes(noteId);
		return CommonUtils.createBuildResponseMessage("Notes added to favourite!", HttpStatus.OK);
	}
	
	@DeleteMapping("/fav/{favNoteId}")
	public ResponseEntity<?> removeFavourite(@PathVariable Integer favNoteId) throws Exception {
		notesService.unFavouriteNotes(favNoteId);
		return CommonUtils.createBuildResponseMessage("Notes removed from favourite!", HttpStatus.OK);
	}
	
	@GetMapping("/fav-notes")
	public ResponseEntity<?> getFavouriteNotes() throws Exception {
		List<FavouriteNoteDTO> favouriteNotes = notesService.getUserFavouriteNotes();
		
		if(CollectionUtils.isEmpty(favouriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtils.createBuildResponse(favouriteNotes, HttpStatus.OK);
	}
	
	@GetMapping("/copy/{noteId}")
	public ResponseEntity<?> copyNotes(@PathVariable Integer noteId) throws Exception {
		Boolean copyNotes = notesService.copyNotes(noteId);
		
		if(copyNotes) {			
			return CommonUtils.createBuildResponseMessage("Notes copied success!", HttpStatus.OK);
		}
		
		return CommonUtils.createBuildResponseMessage("Failed to copy!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
