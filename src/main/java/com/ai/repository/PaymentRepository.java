package com.ai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.entity.Payment;
import com.ai.entity.User;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> getByUser(User user);
	
}
