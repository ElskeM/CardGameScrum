package com.yrgo.sp.cardgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Category;

/**
 * @author elske
 * Repository interface for Categories 
 * Extends JpaRepository
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

	/**
	 * Method to find Category by name
	 * @param category
	 * @return category
	 */
	public Category findByCategory(String category);
	
	/**
	 * Method to find all Categories
	 * @return List of Categories 
	 */
	public List<Category> findAll();
}
