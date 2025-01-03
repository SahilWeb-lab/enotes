package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
	public Boolean existsByTitle(String title);
}
