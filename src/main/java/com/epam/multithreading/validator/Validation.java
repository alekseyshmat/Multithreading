package com.epam.multithreading.validator;

public class Validation {
    private static final String REGEX = "^(\\d+\\s\\d+\\s\\d+\\s*?\\n*)$";

    public boolean isValid(String inputLine) {
        return inputLine.matches(REGEX);
    }
}
