package com.cody.portfolio.service.sorting;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cody.portfolio.domain.Question;

/**
 * Unit tests for the InsertionSortStrategy object that is injected into QuestionService.
 */
public class InsertionSortStrategyTest {
	
	@Test
	void sortCorrectlySortsByOrdinalOccurance() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		Question[] questionArray = new Question[Question.Type.values().length];
		Question.Type[] typeArray = Question.Type.values();
		
		int index = 0;
		for (int i = typeArray.length; i > 0; i--) {
			Question question = new Question("a valid question", "a valid answer");
			question.setType(typeArray[i - 1]); // Assign types in reverse enum order to create an unsorted array.
			questionArray[index++] = question; // Populate the array sequentially.
		}
		
		// After sorting, extract the types and verify they match natural enum order.
		insertionSort.sort(questionArray);
		Question.Type[] sortedTypes = Arrays.stream(questionArray)
				                            .map((Question question) -> question.getType())
				                            .toArray((int arraySize) -> new Question.Type[arraySize]);
		
		assertArrayEquals(typeArray, sortedTypes);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {20})
	void sortThrowsExceptionOnArrayContainingNullElements(int arraySize) {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		Question questionArray[] = new Question[arraySize];
		
		assertThrows(NullPointerException.class, () -> 
			insertionSort.sort(questionArray)
		);
	}
	
	@Test
	void sortMustNotThrowAndMustNotModifyArrayOfLengthZero() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		Question questionArray[] = new Question[0];
		insertionSort.sort(questionArray);
		
		assertEquals(0, questionArray.length);
	}
	
	@Test
	void sortRunsOnSingleElementInQuestionArray() {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		
		Question question = new Question("a valid question", "a valid answer");
		question.setType(Question.Type.Programming);
		
		Question questionArray[] = new Question[] {question};
		insertionSort.sort(questionArray);
		
		assertSame(question, questionArray[0]);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {20})
	void sortPreservesInsertionOrderForEqualTypes(int arraySize) {
		QuestionTypeComparator comparator = new QuestionTypeComparator();
		InsertionSortStrategy insertionSort = new InsertionSortStrategy(comparator);
		Question[] questionArray = new Question[arraySize];
		
		// Set questions and assert type.
		for (int i = 0; i < arraySize; i++) {
			Question question = new Question("a valid question", "a valid answer");
			question.setType(Question.Type.Programming);
			questionArray[i] = question;
		}
		
		// Create a truth array and sort.
		Question[] compareArray = questionArray.clone();
		insertionSort.sort(questionArray);
		
		// Compare order.
		assertArrayEquals(questionArray, compareArray);
	}
}
