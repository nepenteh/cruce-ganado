package com.jmrh.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
 	public UserDetailsService userDetailsService() {
		
		PasswordEncoder encoder = passwordEncoder();
		
 		UserDetails user = User.builder().passwordEncoder(encoder::encode)
 			.username("mame")
 			.password("toros")
 			.roles("USER")
 			.build();
 		UserDetails admin = User.builder().passwordEncoder(encoder::encode)
 			.username("admin")
 			.password("1234")
 			.roles("ADMIN", "USER")
 			.build();
 		return new InMemoryUserDetailsManager(user, admin);
 	}

	
}
