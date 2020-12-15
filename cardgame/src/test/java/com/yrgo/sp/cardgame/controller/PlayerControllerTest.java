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

import com.yrgo.sp.cardgame.data.PlayerRepository;
import com.yrgo.sp.cardgame.domain.Card;
import com.yrgo.sp.cardgame.domain.Player;
import com.yrgo.sp.cardgame.rest.PlayerController;

@WebMvcTest(PlayerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PlayerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlayerRepository playerData;

	@Test
	public void testGetPlayerByUsername() throws Exception {
		Player p = new Player("tp", "tp@tp.se", "password");
		when(playerData.findByUserName("tp")).thenReturn(p);
		this.mockMvc.perform(get("/player/tp")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.userName").value("tp"));
		
	}

	@Test
	public void testGetPlayerByEmail() throws Exception {
		Player p = new Player("tp", "tp@tp.se", "password");
		when(playerData.findByEmail("tp@tp.se")).thenReturn(p);
		this.mockMvc.perform(get("/player?email=tp@tp.se")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.email").value("tp@tp.se"));
		
	}
	
	@Test
	public void testCreatePlayer() throws Exception {
		when(playerData.save(ArgumentMatchers.any(Player.class))).thenAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Player p = (Player) invocation.getArgument(0);
				p.setId((long)new Random().nextInt(100));
				return p;
			}
		});
		this.mockMvc.perform(post("/newPlayer").contentType(MediaType.APPLICATION_JSON).content("{\"username\": \"TP\", \"email\":\"tp@tp.se\", \"password\":\"secret\"}").with(csrf())
		)
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	@Test
	public void testUpdatePlayer() throws Exception {
		Player p = new Player("tp", "tp@tp.se", "password");
		p.setId(1L);
		Optional<Player> opP = Optional.of(p);
		when(playerData.findById(1L)).thenReturn(opP);
		when(playerData.save(ArgumentMatchers.any(Player.class))).thenAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Player p = (Player) invocation.getArgument(0);
				return p;
			}
		});
		
        this.mockMvc.perform(put("/player/1").contentType(MediaType.APPLICATION_JSON).content("{\"username\": \"testPlayer\", \"email\":\"testplayer@tp.se\", \"password\":\"secret\"}").with(csrf())
        		)
        		.andDo(print())
        		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeletePlayer() throws Exception {
		Player p = new Player("PlayerToDelete", "tp@tp.se", "password");
		p.setId(1L);
		when(playerData.save(ArgumentMatchers.any(Player.class))).thenAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Player p = (Player) invocation.getArgument(0);
				return p;
			}
		});
		this.mockMvc.perform(delete("/player/1")
	        		)
	        		.andDo(print())
	        		.andExpect(status().isNoContent());
		verify(playerData).deleteById(1L);
		
	}
}
