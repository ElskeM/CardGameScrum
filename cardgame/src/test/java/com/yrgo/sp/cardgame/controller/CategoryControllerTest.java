package com.yrgo.sp.cardgame.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.yrgo.sp.cardgame.data.CategoryRepository;
import com.yrgo.sp.cardgame.domain.Category;
import com.yrgo.sp.cardgame.rest.CategoryController;

/**
 * @author elske
 * Tests for all methods in the CategoryController Class
 */
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
	
	@Test
	public void testCreateCategory() throws Exception {
		when(categoryData.save(ArgumentMatchers.any(Category.class))).thenAnswer(new Answer<>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Category c = (Category) invocation.getArgument(0);
				c.setId((long)new Random().nextInt(100));
				return c;
			}
		});
		this.mockMvc.perform(post("/newCategory").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content("{\"category\": \"TestCategory\"}").with(csrf())
		)
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	@Test
	public void testUpdateCategory() throws Exception {
		Category c = new Category("CatToUpdate");
		c.setId(1L);
		Optional<Category> opC = Optional.of(c);
		when(categoryData.findById(1L)).thenReturn(opC);
		when(categoryData.save(ArgumentMatchers.any(Category.class))).thenAnswer(new Answer<>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Category c = (Category) invocation.getArgument(0);
				return c;
			}
		});

        this.mockMvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content("{\"category\": \"UpdatedCategory\"}").with(csrf())
        		)
        		.andDo(print())
        		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteCategory() throws Exception {
		Category c = new Category("CatToDelete");
		c.setId(1L);
		when(categoryData.save(ArgumentMatchers.any(Category.class))).thenAnswer(new Answer<>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Category c = (Category) invocation.getArgument(0);
				return c;
			}
		});
		this.mockMvc.perform(delete("/categories/1")
	        		)
	        		.andDo(print())
	        		.andExpect(status().isNoContent());
		verify(categoryData).deleteById(1L);
		
	}
}
