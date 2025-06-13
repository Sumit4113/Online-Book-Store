// config/SecurityConfig.java
package com.base.configure;

import com.base.configure.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtFilter;

	@Bean
	public CustomUserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(customUserDetailsService());
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());

		http.authenticationProvider(authProvider());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll() // public login/register
						.requestMatchers("/books/pdf/**").permitAll().requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN").anyRequest().authenticated());

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
