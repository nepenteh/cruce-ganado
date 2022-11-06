package com.jmrh.app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpBasicSecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
 	@Bean
 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 		http
 			.authorizeRequests(request -> {
 				request
 				.antMatchers("/login").permitAll();
 			})
 			.authorizeRequests()
 			.anyRequest().authenticated()
 			.and()
 			.formLogin().and()
 			.httpBasic();
 		
 		return http.build();
 	}

 	@Bean
 	public UserDetailsService userDetailsService() {
 		
 		PasswordEncoder encoder = passwordEncoder();
 		
 		UserDetails user = User.builder()
 			.passwordEncoder(encoder::encode)
 			.username("mame")
 			.password("toro")
 			.roles("USER")
 			.build();
 		UserDetails admin = User.builder()
			.passwordEncoder(encoder::encode)
 			.username("admin")
 			.password("1234")
 			.roles("USER", "ADMIN")
 			.build();
 		return new InMemoryUserDetailsManager(user, admin);
 	}
	
	
}