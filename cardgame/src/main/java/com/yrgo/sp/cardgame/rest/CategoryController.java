package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger LOG = LogManager.getLogger(CategoryController.class);

	@Autowired
	private CategoryRepository categoryData;

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> findAllCategories() {
		LOG.trace("Method findAllCategories called");
		List<Category> categories = categoryData.findAll();
		
		if (categories.isEmpty()) {
			LOG.trace("CategoryList is empty, returning no content status");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		LOG.trace("Returning List of Categories to Client");
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@GetMapping(value = "/categories/{category}")
	public ResponseEntity<Category> findCategory(@PathVariable String category) {
		LOG.trace("Method findCategory called with following parameter: " + category);
		Category foundCat = categoryData.findByCategory(category);

		if (!foundCat.getCategory().equals(category)) {
			LOG.trace("Invalid parameter, casting CategoryNotFoundException");
			throw new CategoryNotFoundException();
		}
		LOG.trace("Category found successfully and returned to Client");
		return new ResponseEntity<>(foundCat, HttpStatus.OK);
	}

	@PostMapping("/newCategory")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		LOG.trace("Method createCategory called with following parameter: " + category.toString());
		
		Category newCategory = categoryData.save(category);
		LOG.trace("Saving new Category in Repository");

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCategory.getId()).toUri();
		LOG.trace("URI to new Category created and returned to Client");
		
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable Long id) {
		LOG.trace("Method updateCategory called for Category with id: " + id);
		Optional<Category> c = categoryData.findById(id);
		
		LOG.trace("Check if ID is valid and Category exists");
		if (c.isEmpty()) {
			LOG.trace("Invalid parameter, casting CategoryNotFoundException");
			throw new CategoryNotFoundException();
		}

		LOG.trace("Category successfully found, fetching CategoryEntity");
		Category catToUpdate = c.get();
		
		LOG.trace("Updating Category with new data");
		category.setId(catToUpdate.getId());
		
		LOG.trace("Saving updated Category in Repository");
		categoryData.save(category);
		
		LOG.trace("Updated Category successfully saved in Repository and returned to Client");
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
		LOG.trace("Method deleteCategory called for Category with id: " + id);
		categoryData.deleteById(id);
		
		LOG.trace("Category successfully deleted, no content status returned to Client");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
