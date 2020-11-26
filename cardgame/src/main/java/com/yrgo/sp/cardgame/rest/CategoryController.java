package com.yrgo.sp.cardgame.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yrgo.sp.cardgame.data.CategoryRepository;
import com.yrgo.sp.cardgame.domain.Category;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryData;

	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("categories")
	public CategoryList categories() {
		List<Category> categories = categoryData.findAll();

		return new CategoryList(categories);
	}
	
	@RequestMapping(value = "/categories/{category}")
	public Category category(String category) {
		Category foundCat = categoryData.findByCategory(category);
		return foundCat;
	}

}
