package com.yrgo.sp.cardgame.security;

import com.yrgo.sp.cardgame.domain.Player;
import com.yrgo.sp.cardgame.data.PlayerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final PlayerRepository userRepository;

    public JwtUserDetailsService(PlayerRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Player person = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)));
        return new User(person.getUserName(), person.getPassword(), new ArrayList<>());
    }

}
