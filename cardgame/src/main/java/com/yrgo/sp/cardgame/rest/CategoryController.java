package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yrgo.sp.cardgame.data.CategoryRepository;
import com.yrgo.sp.cardgame.domain.Category;
import com.yrgo.sp.exception.CategoryNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryData;

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> categories() {
		List<Category> categories = categoryData.findAll();
		if(categories.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@GetMapping(value = "/categories/{category}")
	public ResponseEntity<Category> findCategory(@PathVariable String category) {
		Category foundCat = categoryData.findByCategory(category);
		
	if(!foundCat.getCategory().equals(category)) {
		throw new CategoryNotFoundException();
	}
		return new ResponseEntity<>(foundCat, HttpStatus.OK);
	}

	@PostMapping("/newCategory")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		Category newCategory = categoryData.save(category);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCategory.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable Long id) {
		Optional<Category> c = categoryData.findById(id);
		if (c.isEmpty()) {
			throw new CategoryNotFoundException();
		}

		Category catToUpdate = c.get();
		category.setId(catToUpdate.getId());
		categoryData.save(category);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
		categoryData.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
