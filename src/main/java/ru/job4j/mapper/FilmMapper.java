package ru.job4j.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.dto.FilmOutDto;
import ru.job4j.model.Film;
import ru.job4j.model.Genre;

@Component
public class FilmMapper {
    public FilmOutDto map(Film film, Genre genre, String duration) {
        return new FilmOutDto(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getYear(),
                genre,
                film.getMinimalAge(),
                duration,
                film.getFileId()
        );
    }
}
