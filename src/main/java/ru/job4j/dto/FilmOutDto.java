package ru.job4j.dto;

import ru.job4j.model.Genre;

/**
 * Сущность для показа фильмов на фронте.
 */
public class FilmOutDto {
    private int id;
    private String name;
    private String description;
    private int year;
    private Genre genre;
    private int minimalAge;
    private String durationInMinutes;
    private int fileId;

    public FilmOutDto() {
    }

    public FilmOutDto(int id, String name, String description, int year, Genre genre, int minimalAge, String durationInMinutes, int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.genre = genre;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public String getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(String durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
