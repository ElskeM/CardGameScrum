package com.yrgo.sp.cardgame.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CardControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetAllCards() throws Exception {
		this.mockMvc.perform(get("/allCards")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void testGetCardById() throws Exception {
		this.mockMvc.perform(get("/card/2")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Blandkost"));
	}
    
	@Test
	public void testCreateCard() throws Exception {
		this.mockMvc.perform(post("/newCard").contentType(MediaType.APPLICATION_JSON).content("{\"title\": \"TestCard\", \"score\":100}").with(csrf())
		)
		.andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());	
	}
	
	

}
