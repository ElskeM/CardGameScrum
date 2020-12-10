package com.yrgo.sp.cardgame.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.yrgo.sp.cardgame.data.CategoryRepository;
import com.yrgo.sp.cardgame.domain.Category;
import com.yrgo.sp.cardgame.rest.CategoryController;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoryRepository categoryData;
	
	@Test
	public void testGetAllCategories() throws Exception {
		List<Category> allCategories = new ArrayList<>();
		Category c = new Category("TestCategory");
		allCategories.add(c);
		when(categoryData.findAll()).thenReturn(allCategories);
		this.mockMvc.perform(get("/categories")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void testFindCategory() throws Exception {
		Category c = new Category("CatToBeFound");
		when(categoryData.findByCategory("CatToBeFound")).thenReturn(c);
		this.mockMvc.perform(get("/categories/CatToBeFound"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.category").value("CatToBeFound"));
	}
	
}
