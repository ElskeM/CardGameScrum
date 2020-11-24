package com.yrgo.sp.cardgame.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author elske
 *Entity that saves the different categories in the database
 */
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String category;

	public Category() {
	}


	public Category(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Category [category=" + category + "]";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}
}
