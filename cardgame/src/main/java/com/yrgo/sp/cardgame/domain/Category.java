package com.yrgo.sp.cardgame.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author elske 
 * Entity that saves the different categories in the database
 */
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false)
	private String category;

	
	/**
	 * Empty contructor to create a category object
	 */
	public Category() {
	}

	
	/**
	 * Constructor that takes a category name as parameter.
	 * @param category
	 */
	public Category(String category) {
		this.category = category;
	}

	/**
	 * ToString method
	 */
	@Override
	public String toString() {
		return "Category [category=" + category + "]";
	}

	// Getter and Setter methods
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
