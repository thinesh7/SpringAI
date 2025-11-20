package com.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User getByUsername(String username);
	
}
