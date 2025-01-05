package com.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.model.FavouriteNote;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote, Integer> {
	
//	Create a method to get favourite notes by userId:
	public List<FavouriteNote> findByUserId(Integer userId);
	
}
