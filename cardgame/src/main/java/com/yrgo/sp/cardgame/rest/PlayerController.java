package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yrgo.sp.cardgame.data.PlayerRepository;
import com.yrgo.sp.cardgame.domain.Player;

@RestController
public class PlayerController {

	@Autowired
	private PlayerRepository playerData;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/player/{userName}")
	public Player findByUserName(@PathVariable String userName) {
		Player playerByUN= playerData.findByUserName(userName);
		return playerByUN;
	}
	
	@GetMapping("/player/{email}")
	public Player findByEmail(@PathVariable String email) {
		Player playerByEmail = playerData.findByUserName(email);
		return playerByEmail;
	}
	
	@PostMapping("/player")
	public ResponseEntity<Object> createPlayer(@RequestBody Player player) {
		Player newPlayer = playerData.save(player);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlayer.getClass()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/player/{id}")
	public ResponseEntity<Object> updatePlayer(@RequestBody Player player, @PathVariable Long id) {
		Optional<Player> p = playerData.findById(id);
		if (!p.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Player playerToUpdate = p.get();
		player.setId(playerToUpdate.getId());
		playerData.save(player);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/player/{id}")
	public void deletePlayer(@PathVariable Long id) {
		playerData.deleteById(id);
	}
}
