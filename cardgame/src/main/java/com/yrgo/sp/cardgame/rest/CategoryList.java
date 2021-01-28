package com.yrgo.sp.cardgame.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.yrgo.sp.cardgame.domain.Category;


/**
 * @author elske
 * CategoryList Entity
 */
@JsonRootName(value = "categories")
public class CategoryList {

	private List<Category> categories;

	/**
	 * Empty constructor
	 */
	public CategoryList() {
	}

	
	/**
	 * Constructor for the categorylist class
	 * @param categories
	 */
	public CategoryList(List<Category> categories) {
		this.categories = categories;
	}

	// Getter and Setter methods
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
