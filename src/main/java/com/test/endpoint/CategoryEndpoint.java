package com.test.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.test.util.Constants.ROLE_ADMIN;
import static com.test.util.Constants.ROLE_USER_ADMIN;

import com.test.dto.CategoryDTO;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {
		
	@PostMapping("/save-category")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO category);
	
	@GetMapping("/categories")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategories();
	
	@GetMapping("/active-categories")
	@PreAuthorize(ROLE_USER_ADMIN)
	public ResponseEntity<?> getActiveCategories();
	
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id);
	
}
