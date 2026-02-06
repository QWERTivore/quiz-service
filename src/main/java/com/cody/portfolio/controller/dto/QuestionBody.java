package com.cody.portfolio.controller.dto;

/**
 * A simple DTO to manage the shape of a request.
 */
public record QuestionBody( 
    String type,
    String difficulty,
    String question,
    String answer
) {}
