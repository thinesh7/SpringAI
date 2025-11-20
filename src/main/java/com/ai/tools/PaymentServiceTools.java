package com.ai.tools;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.UUID;
import com.ai.dto.PaymentDTO;
import com.ai.dto.PaymentRequest;
import com.ai.entity.Payment;
import com.ai.entity.User;
import com.ai.repository.PaymentRepository;
import com.ai.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaymentServiceTools {

	private UserRepository userRepo;

	private PaymentRepository paymentRepository;

	public PaymentServiceTools(UserRepository userRepo, PaymentRepository paymentRepository) {
		super();
		this.userRepo = userRepo;
		this.paymentRepository = paymentRepository;
	}

	@Tool(description = "Call this method to make a payment. Input: PaymentRequest containing username/user and amount. Returns status and remarks")
	public Map<String, String> makePayment(PaymentRequest paymentRequest) {
		Map<String, String> response = new HashMap<>();
		Double amount = 0d;
		if (paymentRequest == null || !StringUtils.hasText(paymentRequest.getUsername())
				|| !StringUtils.hasText(paymentRequest.getAmount())) {
			log.error("Payment Request", paymentRequest);
			response.put("status", "failed");
			response.put("remarks", "Invalid username or amount");
			return response;
		}

		amount = Double.valueOf(paymentRequest.getAmount());
		if (amount <= 0) {
			log.error("Payment Request", paymentRequest);
			response.put("status", "failed");
			response.put("remarks", "Amount cannot be zero");
			return response;
		}

		User user = userRepo.getByUsername(paymentRequest.getUsername());
		if (user == null) {
			response.put("status", "failed");
			response.put("remarks", "User not found");
			return response;
		}

		Payment payment = new Payment();
		payment.setUser(user);
		payment.setAmount(amount);
		payment.setReferenceNumber(UUID.randomUUID().toString());
		payment.setPaymentDate(new Date());
		payment = paymentRepository.save(payment);
		response.put("status", "success");
		response.put("remarks", "Payment Success for " + user.getUsername() + " and Payment Reference Id: " + payment.getReferenceNumber());
		log.info("Payment Success for {}, Response: {}", paymentRequest, response);
		return response;
	}

	@Tool(description = "Call this method to fetch or retrive user details by username/user. Input: the exact username string. Returns user details.")
	public Map<String, Object> fetchUser(String username) {
		Map<String, Object> response = new HashMap<>();
		User user = userRepo.getByUsername(username);
		if (user == null) {
			response.put("status", "failed");
			response.put("remarks", "User not found");
			return response;
		}
		response.put("status", "success");
		response.put("userDetails", user);
		log.info("UserDeatils of: {} and Response: {}", username, response);
		return response;
	}

	@Tool(description = "Call this method to fetch payment details by username/user. Input: the exact username string. Returns all payments.")
	public Map<String, Object> fetchPaymentDetailsByUsername(String username) {
		Map<String, Object> response = new HashMap<>();
		User user = userRepo.getByUsername(username);
		if (user == null) {
			response.put("status", "failed");
			response.put("remarks", "User not found");
			return response;
		}
		response.put("status", "success");
		List<Payment> payments = paymentRepository.getByUser(user);
		List<PaymentDTO> paymentList = payments.stream()
				.map(x -> new PaymentDTO(x.getId(), x.getAmount(), x.getReferenceNumber(), x.getPaymentDate(), x.getUser().getUsername()))
				.toList();
		response.put("paymentDetails", paymentList);
		log.info("Fetch PaymentDetails by username of: {} and Response: {}", username, response);
		return response;
	}

}
