package com.cody.portfolio.service.sorting;

import java.util.Comparator;
import com.cody.portfolio.domain.Question;
import org.springframework.stereotype.Component;

/**
 * A concrete sorting strategy that performs in-place insertion sort on an array of Question objects. 
 * Intended to be used by QuestionService to order questions before searching.
 * The ordering semantics are defined by the injected Comparator.
 * The insertion sort has N^2 worst case, N^2/2 average case.
 */
@Component
public class InsertionSortStrategy extends AbstractSortingStrategy  {
	
	public InsertionSortStrategy(Comparator<Question> comparator) {
		super(comparator);
	}
	
	/**
	 * Performs an insertion sort on an array of Question objects.
	 * The ordering semantics are defined by the injected Comparator.
	 * Elements are sorted based on the declaration order of the enum type.
	 * @param questions A Question array that will be sorted.
	 */
	@Override
	public void sort(Question[] questions) {
		

		for (int i = 1; i < questions.length; i++) {
			
			Question compare = questions[i]; // The element to insert.
			int index = (i - 1); // The index of the element to the left.
			
			// Shift while the left element is greater than compare.
			while ((index >= 0) && comesBefore(questions[index], compare)) {
				questions[index + 1] = questions[index]; // Shift the element to the right.
				index--;
			}
			questions[index + 1] = compare; // Restore the compare element.
		}
	}
}