package ru.job4j.exception;

/**
 * Возникает когда не найдена сущность.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
