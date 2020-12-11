package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yrgo.sp.cardgame.data.PlayerRepository;
import com.yrgo.sp.cardgame.domain.Player;
import com.yrgo.sp.exception.PlayerNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class PlayerController {

	@Autowired
	private PlayerRepository playerData;

	@GetMapping("/player/{userName}")
	public ResponseEntity<Player> findByUserName(@PathVariable String userName) {
		Player playerByUN = playerData.findByUserName(userName);
		if (!playerByUN.getUserName().equals(userName)) {
			throw new PlayerNotFoundException();
		}
		return new ResponseEntity<>(playerByUN, HttpStatus.OK);
	}

	@GetMapping("/player")
	public ResponseEntity<Player> findByEmail(@RequestParam String email) {
		Player playerByEmail = playerData.findByEmail(email);
		if (!playerByEmail.getEmail().equals(email)) {
			throw new PlayerNotFoundException();
		}
		return new ResponseEntity<>(playerByEmail, HttpStatus.OK);

	}
	

	@PostMapping("/newPlayer")
	public ResponseEntity<Object> createPlayer(@RequestBody Player player) {
		Player newPlayer = playerData.save(player);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newPlayer.getClass()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/player/{id}")
	public ResponseEntity<Object> updatePlayer(@RequestBody Player player, @PathVariable Long id) {
		Optional<Player> p = playerData.findById(id);
		if (p.isEmpty()) {
			throw new PlayerNotFoundException();
		}
		Player playerToUpdate = p.get();
		player.setId(playerToUpdate.getId());
		playerData.save(player);
		return new ResponseEntity<>(player, HttpStatus.OK);
	}

	@DeleteMapping("/player/{id}")
	public ResponseEntity<HttpStatus> deletePlayer(@PathVariable Long id) {
		playerData.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
