package com.yrgo.sp.cardgame.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Player;


/**
 * @author elske
 *
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

	public Optional<Player> findByUserName(String userName);
	public Player findByEmail(String email);

	
}
