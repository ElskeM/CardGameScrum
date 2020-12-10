package com.yrgo.sp.cardgame.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.data.CategoryRepository;
import com.yrgo.sp.cardgame.domain.Card;
import com.yrgo.sp.cardgame.rest.CardController;
import com.yrgo.sp.cardgame.rest.CardList;


@WebMvcTest(CardController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CardControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CardRepository cardData;
	
	@MockBean
	private CategoryRepository categoryData;
	
	
	@Test
	public void testGetAllCards() throws Exception {
		List<Card> allCards = new ArrayList<>();
		Card c = new Card("testcard",1000);
		allCards.add(c);
		when(cardData.findAll()).thenReturn(allCards);
		this.mockMvc.perform(get("/allCards")).andDo(print()).andExpect(status().isOk());
	}
	

	@Test
	public void testGetCardById() throws Exception {
		Card c = new Card("testCard", 1250);
		c.setId(2L);
		Optional<Card> oC = Optional.of(c);
		when(cardData.findById(2L)).thenReturn(oC);
		this.mockMvc.perform(get("/card/2")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("testCard"));
		
	}
    
	@Test
	public void testCreateCard() throws Exception {
		when(cardData.save(ArgumentMatchers.any(Card.class))).thenAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Card c = (Card) invocation.getArgument(0);
				c.setId((long)new Random().nextInt(100));
				return c;
			}
		});
		this.mockMvc.perform(post("/newCard").contentType(MediaType.APPLICATION_JSON).content("{\"title\": \"TestCard\", \"score\":100}").with(csrf())
		)
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	

}
