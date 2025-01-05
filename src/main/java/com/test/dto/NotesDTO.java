package com.test.dto;

import java.util.Date;

import com.test.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesDTO {

	private int id;

	private String title;

	private String description;

	private CategoryDTO category;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	private FilesDTO file;

	private Boolean isDeleted;

	private Date deletedOn;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FilesDTO {
		private int id;

		private String originalFileName;

		private String displayFileName;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDTO {
		private Integer id;

		private String name;
	}
}
