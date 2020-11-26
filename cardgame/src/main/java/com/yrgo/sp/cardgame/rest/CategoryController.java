package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

	@PostMapping("/categories")
	public ResponseEntity<Object> createCategory(@RequestBody Category category) {
		Category newCategory = categoryData.save(category);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCategory.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable Long id) {
		Optional<Category> c = categoryData.findById(id);
		if (!c.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Category catToUpdate = c.get();
		category.setId(catToUpdate.getId());
		categoryData.save(category);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable Long id) {
		categoryData.deleteById(id);
	}

	@GetMapping("/setUpCategories")
	public String setUpCategories() {
		Category livsMedel = new Category("Livsmedel");
		Category boende = new Category("Boende");
		Category resorTrans = new Category("Resor & Transport");
		Category ovrigt = new Category("Övrigt");
		
		categoryData.save(livsMedel);
		categoryData.save(boende);
		categoryData.save(resorTrans);
		categoryData.save(ovrigt);
		
		return "Categories successfully created and added to database";
		
	}
}
