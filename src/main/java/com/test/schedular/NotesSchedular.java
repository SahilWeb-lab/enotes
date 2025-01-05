package com.test.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.model.Notes;
import com.test.repository.NotesRepository;

@Component
public class NotesSchedular {
	
	@Autowired
	private NotesRepository notesRepository;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNotesSchedular() {
		System.out.println("Running....");
		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
		List<Notes> notes = notesRepository.findByIsDeletedTrueAndDeletedOnBefore(cutOffDate);
		notesRepository.deleteAll(notes);
	}
}
