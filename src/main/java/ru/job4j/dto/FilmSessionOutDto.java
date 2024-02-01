package ru.job4j.dto;

import ru.job4j.model.Hall;

import java.time.LocalDateTime;

/**
 * Сущность Для показа Расписания фильмов.
 */
public class FilmSessionOutDto {
    private int id;
    private FilmOutDto film;
    private Hall hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public FilmSessionOutDto() {
    }

    public FilmSessionOutDto(int id, FilmOutDto film, Hall hall, LocalDateTime startTime, LocalDateTime endTime, int price) {
        this.id = id;
        this.film = film;
        this.hall = hall;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FilmOutDto getFilm() {
        return film;
    }

    public void setFilm(FilmOutDto film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
