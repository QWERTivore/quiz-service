package com.cody.portfolio.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the Question domain object.
 */
public class QuestionTest {

	// isNull
	
	@Test
	void constructorThrowsExceptionOnNullQuestion() {
		assertThrows(IllegalArgumentException.class, () ->
			new Question(null, "a valid answer")
		);
	}
	
	@Test
	void constructorThrowsExceptionOnNullAnswer() {
		assertThrows(IllegalArgumentException.class, () ->
			new Question("a valid question", null)
		);
	}
	
	@Test
	void setQuestionThrowsExceptionOnNullQuestion() {
		Question question = new Question("a valid question", "a valid answer");
		
		assertThrows(IllegalArgumentException.class, () ->
			question.setQuestion(null)
		);
	}
	
	@Test
	void setAnswerThrowsExceptionOnNullAnswer() {
		Question question = new Question("a valid question", "a valid answer");
		
		assertThrows(IllegalArgumentException.class, () ->
			question.setAnswer(null)
		);
	}
	
	
	// isBlank
	
	@ParameterizedTest
	@ValueSource(strings = { "", " ", "   ", "\t", "\n" })
	void constructorThrowsExceptionOnBlankQuestion(String invalid) {
		assertThrows(IllegalArgumentException.class, () ->
			new Question(invalid, "a valid answer")
		);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "", " ", "   ", "\t", "\n" })
	void constructorThrowsExceptionOnBlankAnswer(String invalid) {
		assertThrows(IllegalArgumentException.class, () ->
			new Question("a valid question", invalid)
		);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "", " ", "   ", "\t", "\n" })
	void setQuestionThrowsExceptionOnBlankQuestion(String invalid) {
		Question question = new Question("a valid question", "a valid answer");
		
		assertThrows(IllegalArgumentException.class, () ->
			question.setQuestion(invalid)
		);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "", " ", "   ", "\t", "\n" })
	void setAnswerThrowsExceptionOnBlankAnswer(String invalid) {
		Question question = new Question("a valid question", "a valid answer");
		
		assertThrows(IllegalArgumentException.class, () ->
			question.setAnswer(invalid)
		);
	}
}