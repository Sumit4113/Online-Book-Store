package com.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.repository.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userRepo;

	public int countAllUsers() {
		return (int) userRepo.count();
	}
}