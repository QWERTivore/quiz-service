package com.cody.portfolio.service.sorting;

import java.util.Comparator;
import com.cody.portfolio.domain.Question;
import com.cody.portfolio.service.sorting.SortingStrategy;

/**
 * The base class for sorting strategies used to sort an array of Questions objects.
 * Requires a Comparator that defines the ordering semantics used by concrete implemntations.
 */
public abstract class AbstractSortingStrategy implements SortingStrategy {
	protected final Comparator<Question> comparator;
	
	protected AbstractSortingStrategy(Comparator<Question> comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * Compares two Question objects using the injected Comparator.
	 * The comparator must be able to define a consistent ordering for the provided objects.
	 * The delegated method compare(a, b) returns a negative integer, zero, or a positive integer 
	 * as the first argument is less than, equal to, or greater than the second. 
	 * This method returns true if a is considered greater than b.
	 * @param a The Question property to evaluate.
	 * @param b The Question property to be compared against.
	 */
	protected boolean comesBefore(Question a, Question b) {
		return comparator.compare(a, b) > 0;
	}
	
}