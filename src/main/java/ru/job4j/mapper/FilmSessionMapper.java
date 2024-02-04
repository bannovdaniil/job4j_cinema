package ru.job4j.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.dto.FilmOutDto;
import ru.job4j.dto.FilmSessionOutDto;
import ru.job4j.dto.PlaceDto;
import ru.job4j.model.FilmSession;
import ru.job4j.model.Hall;

import java.util.List;

@Component
public class FilmSessionMapper {
    public FilmSessionOutDto map(FilmSession filmSession, FilmOutDto filmOutDto, Hall hall, List<List<PlaceDto>> rows) {
        return new FilmSessionOutDto(
                filmSession.getId(),
                filmOutDto,
                hall,
                filmSession.getStartTime(),
                filmSession.getEndTime(),
                filmSession.getPrice(),
                rows
        );
    }

    public FilmSessionOutDto map(FilmSession filmSession, FilmOutDto filmOutDto, Hall hall) {
        return new FilmSessionOutDto(
                filmSession.getId(),
                filmOutDto,
                hall,
                filmSession.getStartTime(),
                filmSession.getEndTime(),
                filmSession.getPrice()
        );
    }
}
