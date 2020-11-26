package com.yrgo.sp.cardgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Player;


/**
 * @author elske
 *
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

	public Player findByUserName(String userName);
	public Player findByEmail(String email);
	
}
