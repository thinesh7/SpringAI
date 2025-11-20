package com.ai.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

	private Long id;

	private Double amount;

	private String referenceNumber;

	private Date paymentDate;

	private String username;

}
