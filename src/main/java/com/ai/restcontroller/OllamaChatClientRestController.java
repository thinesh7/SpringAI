package com.ai.restcontroller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ai.tools.GoldRateTools;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ollama")
@Slf4j
@Tag(name = "2. Ollama Chat API with Tool")
public class OllamaChatClientRestController {

	private final GoldRateTools goldRateTools;

	private final ChatClient chatClient;

	public OllamaChatClientRestController(OllamaChatModel ollamaChatModel, GoldRateTools goldRateTools) {
		this.chatClient = ChatClient.create(ollamaChatModel);
		this.goldRateTools = goldRateTools;
	}

	@GetMapping("/chat")
	public ResponseEntity<String> chat(@RequestParam String prompt) {
		log.info("Started call to Ollama for prompt: '{}'", prompt);
		long startTime = System.currentTimeMillis();
		String response = chatClient.prompt(prompt).tools(goldRateTools).call().content();
		long endTime = System.currentTimeMillis();
		log.info("Total time taken for response: {} seconds", (endTime - startTime) / 1000);
		log.info("Ollama response: ", response);
		return ResponseEntity.ok(response);
	}

}
