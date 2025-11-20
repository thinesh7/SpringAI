package com.ai.restcontroller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ai.tools.PaymentServiceTools;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payments")
@Slf4j
@Tag(name = "3. Ollama Payment Rest API's")
public class OllamaPaymentServiceChatBotRestController {

	private final PaymentServiceTools paymentServiceTools;

	private final ChatClient chatClient;

	public OllamaPaymentServiceChatBotRestController(OllamaChatModel ollamaChatModel,
			PaymentServiceTools paymentServiceTools) {
		this.chatClient = ChatClient.create(ollamaChatModel);
		this.paymentServiceTools = paymentServiceTools;
	}

	@GetMapping("/chat")
	public ResponseEntity<Map<String, String>> chat(@RequestParam String prompt) {
		log.info("Started call to Ollama for prompt: '{}'", prompt);
		String paymentPrompt = """
				You are a payment assistant. You are only allowed to call these three functions:
				1. makePayment(PaymentRequest) PaymentRequest is username/user and amount - Make Payment for given user and amount
				2. fetchUser(username) Fetch User Details based on username/user
				3. fetchPaymentDetailsByUsername(username) Fetch Payment Details based on username/user

				If a user asks anything unrelated to payments, users, or invoices, do NOT respond and reply:
				"I'm happy to help, but I can only answer payment-related queries."

				User query: """ + prompt;
		long startTime = System.currentTimeMillis();
		String chatResponse = chatClient.prompt(prompt).tools(paymentServiceTools).call().content();
		long endTime = System.currentTimeMillis();
		log.info("Total time taken for response: {} seconds", (endTime - startTime) / 1000);
		Map<String, String> response = Map.of("response", chatResponse);
		log.info("Ollama response: ", response);
		return ResponseEntity.ok(response);
	}

}
