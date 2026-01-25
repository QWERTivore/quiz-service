package com.cody.portfolio.utility;

import java.util.function.IntFunction;

/**
 * A generic utility class for performing array transformations.
 * Domain-agnostic operations such as removing nulls, resizing, etc...
 */
public final class ArrayUtility {

	private ArrayUtility() {}
	
	/**
	 * Removes null entries from an array to hide service state from API consumers.
	 * 
	 * @param <T> The element type stored in the array.
	 * @param Array The array that may contain nulls.
	 * @param arrayFunction The function that defines the array type and size.
	 * @return A new array with all null elements removed.
	 */
	public static <T> T[] removeNulls(T[] array, IntFunction<T[]> arrayFunction) {
		int count = 0;
		int index = 0;
		
		// Count the number of non-null elements in the array.
		for (T element : array) {
			if (element != null) count++;
		}
		
		// Use the provided function to create an array.
		T[] result = arrayFunction.apply(count);
		for (T element : array) {
			if (element != null) result[index++] = element;
		}
		
		return result;
	}
}
