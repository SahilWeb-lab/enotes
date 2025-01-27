package com.test.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import static com.test.util.Constants.ROLE_ADMIN;
import static com.test.util.Constants.ROLE_USER;
import static com.test.util.Constants.ROLE_USER_ADMIN;
import static com.test.util.Constants.DEFAULT_PAGE_NO;
import static com.test.util.Constants.DEFAULT_PAGE_SIZE;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {
	
	@PostMapping("/save-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getNotes();
	
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_USER_ADMIN)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(
				@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
				@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
			);
	
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/recycle-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserRecycleBinNotes();
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBin();
	
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> addFavourite(@PathVariable Integer noteId) throws Exception;
	
	@DeleteMapping("/fav/{favNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> removeFavourite(@PathVariable Integer favNoteId) throws Exception;
	
	@GetMapping("/fav-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getFavouriteNotes() throws Exception;
	
	@GetMapping("/copy/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNotes(@PathVariable Integer noteId) throws Exception;
	
	@GetMapping("/search-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> searchNotes(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
}
