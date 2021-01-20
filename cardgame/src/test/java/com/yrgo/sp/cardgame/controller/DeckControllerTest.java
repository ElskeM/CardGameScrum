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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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

import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.data.DeckRepository;
import com.yrgo.sp.cardgame.domain.Card;
import com.yrgo.sp.cardgame.domain.Deck;
import com.yrgo.sp.cardgame.rest.DeckController;

@WebMvcTest(DeckController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DeckControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeckRepository deckData;

	@MockBean
	private CardRepository cardData;

	@Test
	public void testGetAllDecks() throws Exception {
		List<Deck> allDecks = new ArrayList<>();
		Set<Card> cards = new HashSet<Card>();

		Card c1 = new Card("testcard", 1000);
		c1.setId(1L);
		Card c2 = new Card("testcard2", 500);
		c2.setId(2L);
		cards.add(c1);
		cards.add(c2);

		Deck d = new Deck("TestDeck", "TestAdmin", cards);
		allDecks.add(d);
		when(deckData.findAll()).thenReturn(allDecks);
		this.mockMvc.perform(get("/decks")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testGetDeckById() throws Exception {
		Set<Card> cards = new HashSet<Card>();

		Card c1 = new Card("testcard", 1000);
		c1.setId(1L);
		Card c2 = new Card("testcard2", 500);
		c2.setId(2L);
		cards.add(c1);
		cards.add(c2);

		Deck d = new Deck("TestDeck", "TestAdmin", cards);
		d.setId(2L);
		Optional<Deck> opD = Optional.of(d);
		when(deckData.findById(2L)).thenReturn(opD);
		this.mockMvc.perform(get("/decks/2")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("TestDeck"));

	}

	@Test
	public void testCreateDeck() throws Exception {
		when(deckData.save(ArgumentMatchers.any(Deck.class))).thenAnswer(new Answer<>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Deck d = (Deck) invocation.getArgument(0);
				d.setId((long) new Random().nextInt(100));
				return d;
			}
		});
		this.mockMvc.perform(post("/newDeck").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\":\"TestDeck\",\"creator\":\"TestAdmin\",\"cards\":[{\"id\":2,\"title\":\"testcard2\",\"subtitle\":null,\"description\":null,\"extraInfo\":null,\"score\":500,\"category\":null,\"author\":\"Admin\",\"frequence\":null,\"frontImage\":null,\"backImage\":null},{\"id\":1,\"title\":\"testcard\",\"subtitle\":null,\"description\":null,\"extraInfo\":null,\"score\":1000,\"category\":null,\"author\":\"Admin\",\"frequence\":null,\"frontImage\":null,\"backImage\":null}]}")
				.characterEncoding("UTF-8").with(csrf())).andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void testUpdateDeck() throws Exception {
		Set<Card> cards = new HashSet<Card>();

		Card c1 = new Card("testcard", 1000);
		c1.setId(1L);
		Card c2 = new Card("testcard2", 500);
		c2.setId(2L);
		cards.add(c1);
		cards.add(c2);

		Deck d = new Deck("DeckToUpdate", "TestAdmin", cards);
		d.setId(2L);
		Optional<Deck> opD = Optional.of(d);
		when(deckData.findById(2L)).thenReturn(opD);
		when(deckData.save(ArgumentMatchers.any(Deck.class))).thenAnswer(new Answer<>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Deck d = (Deck) invocation.getArgument(0);
				return d;
			}
		});

		this.mockMvc.perform(put("/decks/2").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\":\"UpdatedDeck\",\"creator\":\"TestAdmin\",\"cards\":[{\"id\":5,\"title\":\"testcard5\",\"subtitle\":null,\"description\":null,\"extraInfo\":null,\"score\":1500,\"category\":null,\"author\":\"Admin\",\"frequence\":null,\"frontImage\":null,\"backImage\":null},{\"id\":7,\"title\":\"testedCard\",\"subtitle\":null,\"description\":null,\"extraInfo\":null,\"score\":245,\"category\":null,\"author\":\"Admin\",\"frequence\":null,\"frontImage\":null,\"backImage\":null}]}")
				.characterEncoding("UTF-8").with(csrf())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testDeleteDeck() throws Exception {
		Set<Card> cards = new HashSet<Card>();

		Card c1 = new Card("testcard", 1000);
		c1.setId(1L);
		Card c2 = new Card("testcard2", 500);
		c2.setId(2L);
		cards.add(c1);
		cards.add(c2);

		Deck d = new Deck("DeckToDelete", "TestAdmin", cards);
		d.setId(2L);
		when(deckData.save(ArgumentMatchers.any(Deck.class))).thenAnswer(new Answer<>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Deck d = (Deck) invocation.getArgument(0);
				return d;
			}
		});
		this.mockMvc.perform(delete("/decks/2")).andDo(print()).andExpect(status().isNoContent());
		verify(deckData).deleteById(2L);

	}

}
