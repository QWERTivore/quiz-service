package com.cody.portfolio.domain;

import java.util.UUID;

/**
 * Represents a single quiz question within the domain.
 * A Question must always contain non-null, non-blank text for both
 * the question and the answer. This invariant is enforced at construction and at modification.
 * Each Question is assigned a unique, opaque identifier (UUID) on creation.
 */
public class Question {
	private Type type;
	private Difficulty difficulty;	
	private String question;
	private String answer;
	private final UUID uuid;
	
	public Question(String question, String answer) {
		if (question == null || question.isBlank()) throw new IllegalArgumentException("The parameter question must have non-null, non-blank text!");
		if (answer == null || answer.isBlank())  throw new IllegalArgumentException("The parameter answer must have non-null, non-blank text!");
		this.question = question;
		this.answer = answer;
		this.uuid = UUID.randomUUID();
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public void setQuestion(String question) {
		if (question == null || question.isBlank()) throw new IllegalArgumentException("The parameter question must have non-null, non-blank text!");
		this.question = question;
	}
	
	public void setAnswer(String answer) {
		if (answer == null || answer.isBlank())  throw new IllegalArgumentException("The parameter answer must have non-null, non-blank text!");
		this.answer = answer;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public Difficulty getDifficulty() {
		return this.difficulty;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public UUID getID() {
		return this.uuid;
	}
	
	/**
	 * Categories that describe the subject area of a question.
	 */
	public enum Type{
		Programming,
		DataStructures,
		DiscreteMath, 
		SoftwareArchitecture,
		SoftwareEngineering,
		Networks
	}

	/**
	 * Levels of difficulty assigned to a question.
	 */
	public enum Difficulty {
		Easy,
		Medium,
		Hard
	}
}
