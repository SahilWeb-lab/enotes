package com.test.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.test.dto.NotesDTO;
import com.test.dto.NotesDTO.CategoryDTO;
import com.test.exception.ExistDataException;
import com.test.exception.ResourceNotFoundException;
import com.test.model.Notes;
import com.test.repository.CategoryRepository;
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
	
	@Override
	public Boolean saveNotes(NotesDTO notesDTO) throws Exception  {
		
//		Validation:
		validation.notesValidation(notesDTO);
		
//		Check notes already exist
		Boolean existsByTitle = notesRepository.existsByTitle(notesDTO.getTitle().trim());
		
		if(existsByTitle) {
			throw new ExistDataException("Notes ["+ notesDTO.getTitle() +"] already exists!");
		}
		
		CategoryDTO categoryDTO = notesDTO.getCategory();
		checkCategoryExist(categoryDTO);
		
		Notes notes = modelMapper.map(notesDTO, Notes.class);
		
		Notes saveNotes = notesRepository.save(notes);
		
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		
		return false;
	}


	//	Create a method to check category is exists or not?
	private void checkCategoryExist(CategoryDTO category) throws Exception {
		categoryRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id ["+ category.getId() +"] is invalid!"));
	}
	
	@Override
	public List<NotesDTO> getAllNotes() {
		return notesRepository.findAll()
				.stream()
				.map(note -> modelMapper.map(note, NotesDTO.class)).toList();
	}
	
}
