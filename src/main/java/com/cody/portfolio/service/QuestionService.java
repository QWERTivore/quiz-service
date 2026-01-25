package com.cody.portfolio.service;

import java.util.UUID;
import java.util.Optional;
import com.cody.portfolio.domain.Question;
import org.springframework.stereotype.Service;
import com.cody.portfolio.utility.ArrayUtility;
import org.springframework.beans.factory.annotation.Autowired;
import com.cody.portfolio.service.sorting.AbstractSortingStrategy;

/**
 * An array backed service responsible for managing a collection of Question objects.
 * Provides operations for storing, retrieving, filtering, sorting, and deleting.
 * Uses AbstractSortingStrategy to implement a sorting algorithm appropriate for the collection size.
 */
@Service
public class QuestionService {
	private Question questions[];
	private AbstractSortingStrategy sortingStrategy;
	
	/**
	 * Creates a QuestionService with a default capacity of 20 Questions.
	 * 
	 * @param strategy The sorting strategy used to order the internal Question array.
	 */
	@Autowired
	public QuestionService(AbstractSortingStrategy strategy) {
		this.sortingStrategy = strategy;
		this.questions = new Question[20];
	}
	
	/**
	 * Creates a QuestionService with a caller specified capacity.
	 * 
	 * @param strategy The sorting strategy used to order the internal Question array.
	 * @param numQuestions The desired array size; must be at least 1.
	 * @throws IllegalArgumentException If numQuestions is less than 1.
	 */
	public QuestionService(AbstractSortingStrategy strategy, int numQuestions)  {
		this.sortingStrategy = strategy;
		if (numQuestions < 1) throw new IllegalArgumentException("numQuestions must not be less than 1; default is 20!");
		this.questions = new Question[numQuestions];
	}
	
	/**
	 * Attempts to store a new Question in the service's internal array.
	 * Rejects null Questions.
	 * 
	 * @param question The Question to store.
	 * @return True if the Question was stored; false if the array is full.
	 */
	public boolean setQuestion(Question question) {
	
		for (int i = 0; i < this.questions.length; i++) {
			
			if (this.questions[i] == (null)) {
				this.questions[i] = question;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Attempts to retrieve a Question by UUID.
	 * 
	 * @param id The UUID of the Question you are searching for
	 * @return An Optional containing the matching Question, or Optional.isEmpty() if no matching id's exist.
	 */
	public Optional<Question> getQuestion(UUID id) {
		sort();
		
		for (Question element : this.questions) {
			if (element != null && element.getID().equals(id)) return Optional.of(element);
		}
		return Optional.empty();
	}
	
	/**
	 * Attempts to return an array of match type Questions.
	 * 
	 * @param type The Type enum in Question
	 * @return An Optional containing the matching Question Type in a Question[], or Optional.isEmpty() if the type does not exist.
	 */
	public Optional<Question[]> getQuestions(Question.Type type) {
		sort();
		int count = 0;
		Question[] result = new Question[this.questions.length];

		for (Question element : this.questions) {
			if (element != null && element.getType() == type) {
				result[count++] = element;
			}
		}
		if (count == 0) return Optional.empty();
		return Optional.of(result);
	}
	
	/**
	 * Returns all.
	 * @return An Optional containing all Questions in a Question[], or Optional.isEmpty() if no Questions have been stored.
	 */
	public Optional<Question[]> getAll() {
		Question[] result = ArrayUtility.removeNulls(questions, (int count) -> new Question[count]);
		if (result.length == 0) return Optional.empty();
		return Optional.of(result);
	}
	
	/**
	 * Attempts to delete a question from the internal Question array.
	 * 
	 * @param id The UUID of the Question you want to delete.
	 * @return True if the Question was deleted from the array.
	 */
	public boolean delete(UUID id) {
		
		for (int i = 0; i < questions.length; i++) {
			if (questions[i] != null && questions[i].getID().equals(id)) {
				questions[i] = null;
				sort();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A helper method that sorts the Questions in the array according to the sorting strategy used.
	 */
	private void sort() {
		this.sortingStrategy.sort(questions);
	}
}
