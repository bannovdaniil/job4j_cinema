package ru.job4j.exception;

/**
 * Выбрасывается при ошибках в работе с ДБ.
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
