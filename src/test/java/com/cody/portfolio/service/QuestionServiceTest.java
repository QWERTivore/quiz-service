package com.cody.portfolio.service;

import java.util.UUID;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cody.portfolio.domain.Question;
import com.cody.portfolio.service.sorting.InsertionSortStrategy;
import com.cody.portfolio.service.sorting.QuestionTypeComparator;

/**
 * Unit tests for the QuestionService service object.
 */
public class QuestionServiceTest {

	@ParameterizedTest
	@ValueSource(ints = {0})
	void constructorThrowsExceptionOnInvalidArraySize(int invalidArraySize) {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		
		assertThrows(IllegalArgumentException.class, () ->
			new QuestionService(insertionSort, invalidArraySize)
		);
	}
	
	@Test
	void setQuestionReturnsTrueWhenTheQuestionIsAdded() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		QuestionService questionService = new QuestionService(insertionSort);
		Question question = new Question("a valid question", "a valid answer");
		
		assertTrue(questionService.setQuestion(question));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {20})
	void setQuestionReturnsFalseWhenTheInternalArrayIsFull(int defaultArraySize) {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		QuestionService questionService = new QuestionService(insertionSort);
		
		// Fill the service.
		for (int i = 0; i < defaultArraySize; i++) {
			Question question = new Question("a valid question", "a valid answer");
			questionService.setQuestion(question);
		}
		
		// Test the assertion.
		Question question = new Question("a valid question", "a valid answer");
		assertFalse(questionService.setQuestion(question));
	}
	
	@Test
	void getQuestionReturnsTheCorrectQuestionByUUID() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		QuestionService questionService = new QuestionService(insertionSort);
		
		// Create
		Question question = new Question("a valid question", "a valid answer");
		question.setType(Question.Type.Programming);
		UUID id = question.getID();
		
		// Store
		questionService.setQuestion(question);
		
		// Compare
		assertEquals(question, questionService.getQuestion(id).orElseThrow());
	}
	
	@Test
	void sortMethodThroughGetQuestionsMethodReturnsQuestionsOfTheMatchingTypesByInsertionOrder() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		QuestionService questionService = new QuestionService(insertionSort);
		
		// Assert the type.
		Question.Type type = Question.Type.Programming;
		
		// Create two questions in the service.
		Question q1 = new Question("q1", "a1");
		q1.setType(type);
		questionService.setQuestion(q1);
		
		Question q2 = new Question("q2", "a2");
		q2.setType(type);
		questionService.setQuestion(q2);
		
		// Create an array that reflects the order you added questions to the service.
		Question insertionOrder[] = {q1, q2};
		
		// Get questions from the service.
		Question[] stored = questionService.getQuestions(type).orElseThrow();
		
		// Check that the order of the elements is as they were inserted.
		assertArrayEquals(insertionOrder, stored);
	}
	
	@Test
	void getAllReturnsQuestionsForAllUniqueTypes() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		QuestionService questionService = new QuestionService(insertionSort);
		
		// Set questions and assert types in the service.
		for (Question.Type type : Question.Type.values()) {
			Question question = new Question("a valid question", "a valid answer");
			question.setType(type);
			questionService.setQuestion(question);
		}
		
		// Get questions from the service.
		Question[] questionServiceArray = questionService.getAll().orElseThrow();
		
		// Extract types stored in the service.
		EnumSet<Question.Type> questionServiceStoredTypes = Arrays.stream(questionServiceArray)
			                                                      .map((Question question) -> question.getType())
			                                                      .collect(Collectors.toCollection(() -> EnumSet.noneOf(Question.Type.class)));

		// Extract all the enum members.
		EnumSet<Question.Type> enumTypes = EnumSet.allOf(Question.Type.class);
		
		// Compare
		assertEquals(questionServiceStoredTypes, enumTypes);
	}
	
	@Test
	void deleteReturnsTrueWhenAQuestionIsDeletedFromTheService() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		QuestionService questionService = new QuestionService(insertionSort);
		Question question = new Question("a valid question", "a valid answer");
		UUID id = question.getID();
		
		questionService.setQuestion(question);
		assertTrue(questionService.delete(id));
	}
}
