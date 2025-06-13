// repository/UserRepository.java
package com.base.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);

	Optional<User> findById(Long id);
}
