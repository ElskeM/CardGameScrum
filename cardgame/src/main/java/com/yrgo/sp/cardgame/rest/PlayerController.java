package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
import com.yrgo.sp.cardgame.data.SecurityRoleRepository;
import com.yrgo.sp.cardgame.data.UserRepository;
import com.yrgo.sp.cardgame.domain.user.Player;
import com.yrgo.sp.cardgame.domain.user.User;
import com.yrgo.sp.cardgame.exception.PlayerNotFoundException;
import com.yrgo.sp.cardgame.security.annotations.IsPlayer;

/**
 * @author elske
 * PlayerController entity that handles and maps all requests from the frontend project
 */
@DependsOn("SecurityRoleManager")
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class PlayerController {

	/**
	 * Logger to log all method calls and exceptions that are casted
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);
	
	/**
	 * An autowire to the player repository
	 */
	@Autowired
	private PlayerRepository playerData;
	
	/**
	 * An autowire to the user repository
	 */
	@Autowired
	private UserRepository userData;
	
	/**
	 * An autowire to the securityRole repository
	 */
	@Autowired
	private SecurityRoleRepository roleData;
	
	/**
	 * Hashset for the default roles
	 */
	private Set<? extends GrantedAuthority> defaultRoles;
	
	/**
	 * An autowire to the passwordEncoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Method to set the default role to player.
	 * 
	 */
	@PostConstruct
	private void initializeController() {
		defaultRoles = roleData.findByAuthority("PLAYER").stream().collect(Collectors.toSet());
	}
	
	/**
	 * Method that calls upon the find by username method in the repository
	 * if the method doesn't return a player a playerisnotfound exception is casted and details are logged
	 * @param username
	 * @return ResponseEntity and found player
	 */
	@IsPlayer
	@GetMapping("/player/{username}")
	public ResponseEntity<Player> findByUserName(@PathVariable String username) {
		LOG.info("Method FindByUserName called with following parameter: " + username);
		User userByUN = userData.findByUsername(username).get();
		Player playerByUN = playerData.findByUser(userByUN).get();
		
		if (!userByUN.getUsername().equals(username)) {
			LOG.info("Invalid parameter, casting PlayerNotFoundException");
			throw new PlayerNotFoundException();
		}
		LOG.info("Player successfully found and returned to Client");
		return new ResponseEntity<>(playerByUN, HttpStatus.OK);
	}

	/**
	 * Method that calls upon the find player by email method in the repository
	 * if the method doesn't return a player a playerisnotfound exception is casted and details are logged
	 * @param email
	 * @return ResponseEntity and found player
	 */
	@IsPlayer
	@GetMapping("/player")
	public ResponseEntity<Player> findByEmail(@RequestParam String email) {
		LOG.info("Method FindByEmail called with following parameter: " + email);
		User userByEmail = userData.findByEmail(email).get();
		Player playerByEmail = playerData.findByUser(userByEmail).get();
		
		if (!userByEmail.getEmail().equals(email)) {
			LOG.info("Invalid parameter, casting PlayerNotFoundException");
			throw new PlayerNotFoundException();
		}
		LOG.info("Player successfully found and returned to Client");
		return new ResponseEntity<>(playerByEmail, HttpStatus.OK);
	}
	

	/**
	 * Method to create a new player.
	 * If the password field is empty an IllegalArgument Exception is casted.
	 * @param user
	 * @return ResponseEntity to the created player
	 */
	@PostMapping("/newPlayer")
	@Transactional
	public ResponseEntity<Object> createPlayer(@RequestBody User user) {
		LOG.info("Method CreatePlayer called with following parameter: " + user.toString());
		
		if(user.getPassword().isEmpty()) {
			throw new IllegalArgumentException("password cannot be empty!");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(defaultRoles);
		User newUser = userData.save(user);
		LOG.info("Saving new Player in Repository");

		Player newPlayer = playerData.save(new Player(newUser));
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newPlayer.getId()).toUri();
		LOG.info("URI to Player created and returned to Client");
		
		return ResponseEntity.created(location).build();
	}

	/**
	 * Method to update/change a player
	 * Fetches the player by id and throws a playernotfoundexception if the player doesn't exist. 
	 * @param player
	 * @param id
	 * @return ResponseEntity and the updated player
	 */
	@IsPlayer
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

	/**
	 * Method to delete a player.
	 * Fetches the player by id and throws a playernotfoundexception if the player doesn't exist.
	 * If the player exists, the method deletebyid is called through the repository
	 * @param id
	 * @return No Content ResponseEntity
	 */
	@IsPlayer
	@DeleteMapping("/player/{id}")
	public ResponseEntity<HttpStatus> deletePlayer(@PathVariable Long id) {
		LOG.info("Method deletePlayer called for Player with id: " + id);
		Optional<Player> p = playerData.findById(id);
		
		LOG.info("Check if ID is valid and Player exists");
		if (p.isEmpty()) {
			LOG.info("Invalid parameter, casting PlayerNotFoundException");
			throw new PlayerNotFoundException();
		}
		
		LOG.info("Deleting player");
		playerData.deleteById(id);
		
		LOG.info("Player deleted from Repository, no content status returned to client");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
