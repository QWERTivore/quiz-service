package com.cody.portfolio.controller;


import java.util.UUID;
import java.util.Optional;

import com.cody.portfolio.domain.Question;
import com.cody.portfolio.service.QuestionService;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc; // Send mock http requests to the DispatchServlet.
import org.springframework.beans.factory.annotation.Autowired; // Inject Spring beans.
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest; // Creates a sliced/limited WebApplicationContext.
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Overrides the service bean with a Mokito mock.

// Factory method to create HTTP request.
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// Method to write assertions for the returned HTTP request.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Method to print the http response.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(QuestionController.class) // Creates a sliced WebApplicationContext with a QuestionController and Spring MVC infrastructure.
public class QuestionControllerTest {
	
	@MockitoBean // Create the service to be called in the sliced WebApplicationContext.
	private QuestionService questionService;
	
	@Autowired // Simulates requests to the endpoints through the DispatcherServlet.
	private MockMvc mockMVC;

	@Test
	void setSingleReturns200RequestSucceededWhenTheQuestionObjectIsValid() throws Exception {
		String requestJson = """
		{
		  "question": "a valid question",
		  "answer": "a valid answer"
		}
		""";

		when(questionService.setQuestion(any(Question.class))).thenReturn(true);
		
		mockMVC.perform(post("/questions/set-single")
				             .contentType(MediaType.APPLICATION_JSON)
				             .content(requestJson))
		
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Success: Question stored!"));
	}
	
	@Test
	void setSingleReturns400BadRequestWhenTheQuestionArrayIsFull() throws Exception {
		String requestJson = """
		{
		  "question": "a valid question",
		  "answer": "a valid answer"
		}
		""";
		
		when(questionService.setQuestion(any(Question.class))).thenReturn(false);
		
		mockMVC.perform(post("/questions/set-single")
				             .contentType(MediaType.APPLICATION_JSON)
				             .content(requestJson))

				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.message").value("Failed: The question array is full!"));
	}
	
	@Test
	void setSingleReturns400BadRequestWhenTheQuestionFieldIsInvalid() throws Exception {
		String requestJson = """
		{
		  "question": "",
		  "answer": "a valid answer"
		}
		""";
		
		when(questionService.setQuestion(any(Question.class))).thenThrow(new IllegalArgumentException("Invalid question!"));
		
		
		mockMVC.perform(post("/questions/set-single")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
		
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.message").value("The parameter question must have non-null, non-blank text!"));
	}
	
	@Test
	void setSingleReturns400BadRequesWhenTheAnswerFieldIsInvalid() throws Exception {
		String requestJson = """
		{
		  "question": "a valid question",
		  "answer": ""
		}
		""";
		
		when(questionService.setQuestion(any(Question.class))).thenThrow(new IllegalArgumentException("Invalid answer!"));
		
		
		mockMVC.perform(post("/questions/set-single")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
		
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.success").value(false))
			.andExpect(jsonPath("$.message").value("The parameter answer must have non-null, non-blank text!"));
	}
	
	@Test
	void getSingleReturns200RequestSucceededWhenTheQuestionIdIsValid() throws Exception {
		Question question = new Question("a valid question", "a valid answer");
		UUID id = question.getID();

		when(questionService.getQuestion(id)).thenReturn(Optional.of(question));
		
		mockMVC.perform(get("/questions/get-single/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.question").value("a valid question"))
				.andExpect(jsonPath("$.answer").value("a valid answer"))
				.andExpect(jsonPath("$.ID").value(id.toString()));
	}
	
	@Test
	void getSingleReturns404NotFoundWhenTheQuestionIdIsNotFound() throws Exception {
		UUID invalidId = UUID.randomUUID();
		
		when(questionService.getQuestion(invalidId)).thenReturn(Optional.empty());
		
		mockMVC.perform(get("/questions/get-single/" + invalidId))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void getManyReturns200RequestSuccededWhenTheQuestionTypeIsValid() throws Exception {
		Question.Type type = Question.Type.Programming; 
		Question question = new Question("a valid question", "a valid answer");
		question.setType(type);
		UUID id = question.getID();
		Question questionArray[] = {question};
		
		when(questionService.getQuestions(type)).thenReturn(Optional.of(questionArray));
		
		mockMVC.perform(get("/questions/get-many/" + type))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].question").value("a valid question"))
			.andExpect(jsonPath("$[0].answer").value("a valid answer"))
			.andExpect(jsonPath("$[0].ID").value(id.toString()))
			.andExpect(jsonPath("$[0].type").value(type.toString()));
	}
	
	@Test
	void getManyReturns404NotFoundWhenTheQustionTypeIsNotFound() throws Exception {
		
	}
	

}
