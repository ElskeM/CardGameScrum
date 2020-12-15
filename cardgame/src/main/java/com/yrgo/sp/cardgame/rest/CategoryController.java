package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryRepository categoryData;

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> findAllCategories() {
		LOG.info("Method findAllCategories called");
		List<Category> categories = categoryData.findAll();
		
		if (categories.isEmpty()) {
			LOG.info("CategoryList is empty, returning no content status");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		LOG.info("Returning List of Categories to Client");
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@GetMapping(value = "/categories/{category}")
	public ResponseEntity<Category> findCategory(@PathVariable String category) {
		LOG.info("Method findCategory called with following parameter: " + category);
		Category foundCat = categoryData.findByCategory(category);

		if (!foundCat.getCategory().equals(category)) {
			LOG.info("Invalid parameter, casting CategoryNotFoundException");
			throw new CategoryNotFoundException();
		}
		LOG.info("Category found successfully and returned to Client");
		return new ResponseEntity<>(foundCat, HttpStatus.OK);
	}

	@PostMapping("/newCategory")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		LOG.info("Method createCategory called with following parameter: " + category.toString());
		
		Category newCategory = categoryData.save(category);
		LOG.info("Saving new Category in Repository");

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCategory.getId()).toUri();
		LOG.info("URI to new Category created and returned to Client");
		
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable Long id) {
		LOG.info("Method updateCategory called for Category with id: " + id);
		Optional<Category> c = categoryData.findById(id);
		
		LOG.info("Check if ID is valid and Category exists");
		if (c.isEmpty()) {
			LOG.info("Invalid parameter, casting CategoryNotFoundException");
			throw new CategoryNotFoundException();
		}

		LOG.info("Category successfully found, fetching CategoryEntity");
		Category catToUpdate = c.get();
		
		LOG.info("Updating Category with new data");
		category.setId(catToUpdate.getId());
		
		LOG.info("Saving updated Category in Repository");
		categoryData.save(category);
		
		LOG.info("Updated Category successfully saved in Repository and returned to Client");
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
		LOG.info("Method deleteCategory called for Category with id: " + id);
		categoryData.deleteById(id);
		
		LOG.info("Category successfully deleted, no content status returned to Client");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
