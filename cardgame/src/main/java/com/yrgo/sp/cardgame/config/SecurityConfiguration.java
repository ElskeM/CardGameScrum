package com.yrgo.sp.cardgame.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yrgo.sp.cardgame.security.JwtAuthenticationEntryPoint;
import com.yrgo.sp.cardgame.security.JwtRequestFilter;

/**
 * @author ptemrz
 * SecurityConfiguration class
 */
@Configuration
@EnableWebSecurity
@Profile("development")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
	}

	/**
	 * returns authenticationmanagerbean from websecurityconfigureradapter
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * @param httpsecurity
	 */
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				// authenticate this particular request
				.authorizeRequests().antMatchers("/game/**").authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * method for setting the configuration for httpmethods
	 * @param websecurity
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers(HttpMethod.POST, "/newPlayer");
		web.ignoring().antMatchers(HttpMethod.DELETE, "player/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/authenticate");
		web.ignoring().antMatchers(HttpMethod.POST, "/h2-console/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/h2-console/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/import_json");

	}
}
