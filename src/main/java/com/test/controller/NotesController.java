package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.dto.NotesDTO;
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
	
}
