package com.yrgo.sp.cardgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Category;

/**
 * @author elske
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findByCategory(String category);

	public List<Category> findAll();
}
