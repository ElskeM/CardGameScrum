package com.yrgo.sp.cardgame.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.user.Player;
import com.yrgo.sp.cardgame.domain.user.User;


/**
 * @author ptemrz
 * Repository interface for players
 * Extends JpaRepository
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

	/**
	 * Method to find "player" by "user-object"
	 * @param user
	 * @return player
	 */
	public Optional<Player> findByUser(User user);	
}
