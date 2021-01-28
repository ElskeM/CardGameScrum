package com.yrgo.sp.cardgame.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yrgo.sp.cardgame.data.UserRepository;

/**
 * @author ptemrz
 * JwtUserDetailService class, implements UserDetailService interface
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository
     */
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method finds user and loads userdetails accordingly
     * @param username
     * @return user
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)));
        return user;
    }

}
