package com.cody.portfolio.service.sorting;

import com.cody.portfolio.domain.Question;

/**
 * A funtional interface that defines the contract for sorting an array of Question objects.
 */
public interface SortingStrategy {
	void sort(Question[] questions);
}