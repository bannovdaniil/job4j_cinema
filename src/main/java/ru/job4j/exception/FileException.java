package ru.job4j.exception;

/**
 * Когда возникают проблемы с файлами
 */
public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }
}
