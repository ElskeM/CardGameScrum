package com.yrgo.sp.cardgame.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.yrgo.sp.cardgame.domain.Category;


/**
 * @author elske
 *
 */
@JsonRootName(value = "categories")
public class CategoryList {

	private List<Category> categories;

	public CategoryList() {
	}

	public CategoryList(List<Category> categories) {
		this.categories = categories;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
