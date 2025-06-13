// service/CustomUserDetailsService.java
package com.base.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.base.entity.User;
import com.base.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new CustomUserDetails(user);
	}

	// Optional: You can add this if you want to load by userId or email in future
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
		return new CustomUserDetails(user);
	}
}
