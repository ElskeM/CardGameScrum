package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);
			
	@Autowired
	private PlayerRepository playerData;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/player/{userName}")
	public ResponseEntity<Player> findByUserName(@PathVariable String userName) {
		LOG.info("Method FindByUserName called with following parameter: " + userName);
		Player playerByUN = playerData.findByUserName(userName).get();
		
		if (!playerByUN.getUserName().equals(userName)) {
			LOG.info("Invalid parameter, casting PlayerNotFoundException");
			throw new PlayerNotFoundException();
		}
		LOG.info("Player successfully found and returned to Client");
		return new ResponseEntity<>(playerByUN, HttpStatus.OK);
	}

	@GetMapping("/player")
	public ResponseEntity<Player> findByEmail(@RequestParam String email) {
		LOG.info("Method FindByEmail called with following parameter: " + email);
		Player playerByEmail = playerData.findByEmail(email);
		
		if (!playerByEmail.getEmail().equals(email)) {
			LOG.info("Invalid parameter, casting PlayerNotFoundException");
			throw new PlayerNotFoundException();
		}
		LOG.info("Player successfully found and returned to Client");
		return new ResponseEntity<>(playerByEmail, HttpStatus.OK);
	}
	

	@PostMapping("/newPlayer")
	public ResponseEntity<Object> createPlayer(@RequestBody Player player) {
		LOG.info("Method CreatePlayer called with following parameter: " + player.toString());
		
		if(player.getPassword().isEmpty()) {
			throw new IllegalArgumentException("password must not be empty!");
		}
		player.setPassword(passwordEncoder.encode(player.getPassword()));
		
		if(player.getEmail().isBlank()) {
			player.setEmail(null);			
		}
		
		Player newPlayer = playerData.save(player);
		LOG.info("Saving new Player in Repository");

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newPlayer.getId()).toUri();
		LOG.info("URI to Player created and returned to Client");
		
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/player/{id}")
	public ResponseEntity<Object> updatePlayer(@RequestBody Player player, @PathVariable Long id) {
		LOG.info("Metod UpdatePlayer called for Player with following Id: " + id);
		Optional<Player> p = playerData.findById(id);
		LOG.info("Check if ID is valid and Player exists");
		if (p.isEmpty()) {
			LOG.info("Invalid parameter, casting PlayerNotFoundException");
			throw new PlayerNotFoundException();
		}
		LOG.info("Player successfully found, fetching PlayerEntity");
		Player playerToUpdate = p.get();
		
		LOG.info("Updating PlayerEntity with new data");
		player.setId(playerToUpdate.getId());
		
		LOG.info("Save updated Player in Repository");
		playerData.save(player);
		
		LOG.info("Updated PlayerEntity successfully saved and returned to Client");
		return new ResponseEntity<>(player, HttpStatus.OK);
	}

	@DeleteMapping("/player/{id}")
	public ResponseEntity<HttpStatus> deletePlayer(@PathVariable Long id) {
		LOG.info("Method deletePlayer called for Player with id: " + id);
		playerData.deleteById(id);
		
		LOG.info("Player deleted from Repository, no content status returned to client");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
