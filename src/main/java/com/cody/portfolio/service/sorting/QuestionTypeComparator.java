package com.cody.portfolio.service.sorting;

import java.util.Comparator;
import com.cody.portfolio.domain.Question;
import org.springframework.stereotype.Component;

/**
 * Comparator for ordering Question objects by their Type field.
 * Uses the enum's declaration order as the Comparator.compare argument.
 * All compared Question instances must have a non-null Type value.
 */
@Component
public class QuestionTypeComparator implements Comparator<Question> {
	@Override
	public int compare(Question a, Question b) {
		if (a.getType() == null || b.getType() == null) throw new IllegalArgumentException("Question.Type must not be null!");
		return a.getType().compareTo(b.getType());
	}
}