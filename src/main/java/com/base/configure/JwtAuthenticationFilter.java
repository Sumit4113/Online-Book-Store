package com.base.configure;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authHeader = httpRequest.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			String username = jwtUtil.extractUsername(jwt);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				var userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtUtil.validateToken(jwt)) {
					var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		chain.doFilter(request, response);
	}
}
