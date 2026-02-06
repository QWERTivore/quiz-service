package com.cody.portfolio.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cody.portfolio.domain.Question;
import com.cody.portfolio.service.QuestionService;
import com.cody.portfolio.controller.dto.ApiResponse;
import com.cody.portfolio.controller.dto.QuestionBody;

/**
 * This controller is responsible for managing Questions. 
 * 
 * Provides explicit endpoints for creating, retrieving, listing, and deleting Questions.
 * Domain logic and state are delegated to QuestionService; this conroller is the REST endpoint.
 */
@RestController
@RequestMapping("/questions")
public class QuestionController {
	private final QuestionService questionService;
	
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	@PostMapping("/set-single")
	public ResponseEntity<ApiResponse> setQuestion(@RequestBody QuestionBody questionBody) {
		try {
			
			Question question = new Question(
					questionBody.question(),
					questionBody.answer()
			);
					
			boolean success = questionService.setQuestion(question);
			
			if (success) {
				return ResponseEntity.ok(new ApiResponse(true, "Success: Question stored!"));
			} else {
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed: The question array is full!"));
			}
			
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}
	
	@GetMapping("/get-single/{id}")
	public ResponseEntity<Question> getSingleQuestion(@PathVariable UUID id) {
		return questionService.getQuestion(id)
				.map(Question -> ResponseEntity.ok(Question))
				.orElseGet(() -> ResponseEntity.notFound().build());	
	}
	
	@GetMapping("/get-many/{type}")
	public ResponseEntity<Question[]> getManyQuestions(@PathVariable Question.Type type) {
		return questionService.getQuestions(type)
				.map(array -> ResponseEntity.ok(array))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<Question[]> getAllQuestions() {
		return questionService.getAll()
				.map(array -> ResponseEntity.ok(array))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/delete-single/{id}")
	public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable UUID id) {
		boolean success = questionService.delete(id);
		
		if (success) {
			return ResponseEntity.ok(new ApiResponse(true, "Success: Question deleted!"));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed: Question not found in array!"));
		}
	}
}
