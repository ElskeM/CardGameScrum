package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryData;

	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/categories")
	public CategoryList categories() {
		List<Category> categories = categoryData.findAll();

		return new CategoryList(categories);
	}

	@RequestMapping(value = "/categories/{category}")
	public Category findCategory(@PathVariable String category) {
		Category foundCat = categoryData.findByCategory(category);
		return foundCat;
	}

	@PostMapping("/categories/")
	public ResponseEntity<Object> createCategory(@RequestBody Category category) {
		Category newCategory = categoryData.save(category);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCategory.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/categories/{id}")
	public String updateCategory() {
		return "Todo: Implement";
	}

	@DeleteMapping("/categories/{id}")
	public String deleteCategory() {
		return "Todo: Implement!";
	}

}
