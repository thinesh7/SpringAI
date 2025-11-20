package com.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

	private String username;

	private String amount;

	private String city;

	private String ssn;

}
