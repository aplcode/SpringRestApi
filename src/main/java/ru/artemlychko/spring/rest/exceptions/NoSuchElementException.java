package ru.artemlychko.spring.rest.exceptions;

public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException(String message) {
        super(message);
    }
}
