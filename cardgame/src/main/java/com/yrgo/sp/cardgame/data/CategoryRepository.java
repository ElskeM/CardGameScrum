package com.yrgo.sp.cardgame.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findByCategory(String category);
}
