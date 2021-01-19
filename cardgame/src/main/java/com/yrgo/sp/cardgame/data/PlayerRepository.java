package com.yrgo.sp.cardgame.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.user.Player;
import com.yrgo.sp.cardgame.domain.user.User;


/**
 * @author elske
 *
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

	public Optional<Player> findByUser(User user);	
}
