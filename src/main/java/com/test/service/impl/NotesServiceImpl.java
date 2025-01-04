package com.test.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dto.NotesDTO;
import com.test.dto.NotesDTO.CategoryDTO;
import com.test.dto.NotesResponse;
import com.test.exception.ExistDataException;
import com.test.exception.ResourceNotFoundException;
import com.test.model.FileDetails;
import com.test.model.Notes;
import com.test.repository.CategoryRepository;
import com.test.repository.FileRepository;
import com.test.repository.NotesRepository;
import com.test.service.NotesService;
import com.test.util.Validation;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private Validation validation;

	@Autowired
	private FileRepository fileRepository;

	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		NotesDTO notesDTO = objectMapper.readValue(notes, NotesDTO.class);

//		Validation:
		validation.notesValidation(notesDTO);

//		Check notes already exist
		Boolean existsByTitle = notesRepository.existsByTitle(notesDTO.getTitle().trim());

		if (existsByTitle) {
			throw new ExistDataException("Notes [" + notesDTO.getTitle() + "] already exists!");
		}

		CategoryDTO categoryDTO = notesDTO.getCategory();
		checkCategoryExist(categoryDTO);

		Notes notesMap = modelMapper.map(notesDTO, Notes.class);

		FileDetails saveFileDetails = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(saveFileDetails)) {
			notesMap.setFile(saveFileDetails);
		} else {
			notesMap.setFile(null);
		}

		Notes saveNotes = notesRepository.save(notesMap);

		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}

		return false;
	}

//	Create a method to save file in notes:
	public FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if (!file.isEmpty()) {
//			Create a list of allowed file extension
			List<String> allowedExtensions = Arrays.asList("pdf", "xlsx", "png", "jpeg", "jpg", "txt");
			
			
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);

			if(!allowedExtensions.contains(extension)) {
				throw new IllegalArgumentException("Only .pdf, .xlsx, .jpg, .jpeg, .txt and .png files are accepted!");
			}
				
			String randomString = UUID.randomUUID().toString();
			String uploadFileName = randomString + "." + extension;

			File saveFile = new File(uploadPath);
			if (!saveFile.exists()) {
				saveFile.mkdir();
			}

			String storePath = uploadPath.concat(uploadFileName);

//			Upload file:
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));

			if (upload != 0) {
				FileDetails fileDetails = new FileDetails();
				fileDetails.setOriginalFileName(originalFilename);
				fileDetails.setDisplayFileName(getDisplayFileName(originalFilename));
				fileDetails.setUploadedFileName(uploadFileName);
				fileDetails.setFileSize(file.getSize());
				fileDetails.setPath(storePath);
				FileDetails saveFileDetails = fileRepository.save(fileDetails);
				return saveFileDetails;
			}
		}

		return null;
	}

//	Create a method to display the filename:
	private String getDisplayFileName(String originalFilename) {
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);

		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}

		fileName = fileName + "." + extension;

		return fileName;
	}

	// Create a method to check category is exists or not?
	private void checkCategoryExist(CategoryDTO category) throws Exception {
		categoryRepository.findById(category.getId())
				.orElseThrow(() -> new ResourceNotFoundException("category id [" + category.getId() + "] is invalid!"));
	}

	@Override
	public List<NotesDTO> getAllNotes() {
		return notesRepository.findAll().stream().map(note -> modelMapper.map(note, NotesDTO.class)).toList();
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		InputStream io = new FileInputStream(fileDetails.getPath());
		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDetails = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File is not available!"));
		
		return fileDetails;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Notes> notes = notesRepository.findByCreatedBy(userId, pageable);	
		
		List<NotesDTO> notesDTOs = notes.stream().map(note -> modelMapper.map(note, NotesDTO.class)).toList();
		
		NotesResponse notesResponse = NotesResponse.builder()
				.notes(notesDTOs)
				.pageNo(notes.getNumber() + 1)
				.pageSize(notes.getSize())
				.totalPages(notes.getTotalPages())
				.isFirst(notes.isFirst())
				.isLast(notes.isLast())
				.totalElements(notes.getTotalElements())
				.build();
		return notesResponse;
	}

}
