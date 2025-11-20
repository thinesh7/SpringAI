package com.ai.restcontroller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/openai")
@Slf4j
@Tag(name = "4. Open AI Chat API")
public class OpenAIRestController {

	private OpenAiChatModel openAiChatModel;

	public OpenAIRestController(OpenAiChatModel openAiChatModel) {
		this.openAiChatModel = openAiChatModel;
	}

	@GetMapping("/chat")
	public ResponseEntity<String> chatOllama(@RequestParam String prompt) {
		log.info("Started call to Ollama for prompt: '{}'", prompt);

		long startTime = System.currentTimeMillis();
		ChatResponse chatResposne = openAiChatModel.call(new Prompt(prompt));
		long endTime = System.currentTimeMillis();

		String model = chatResposne.getMetadata().getModel();
		String response = chatResposne.getResult().getOutput().getText();
		log.info("Total time taken for response: {}", ((endTime - startTime) / 1000));
		log.info("Used Ollama Model: {}", model);
		log.info("Ollama response: /n {}", response);
		return ResponseEntity.ok(response);
	}

}