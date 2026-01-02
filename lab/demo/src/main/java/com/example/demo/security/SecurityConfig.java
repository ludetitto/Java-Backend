package com.example.demo.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.*;

@Configuration
public class SecurityConfig {
	// Deprecado
	/*
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests(authorizeReuqests ->
					authorizeReuqests
						.antMatchers("public/**").permitAll()
						.anyRequest().authenticated()
			)
			.httpBasic();
		return http.build();
	}
	*/
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorizeReuqests ->
					authorizeReuqests
						.requestMatchers("/public/**").permitAll()
						.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	// Deprecado
	/*
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	*/
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
				.username("user")
				.password("{noop}password")
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password("{noop}admin")
				.roles("USER", "ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}
}
