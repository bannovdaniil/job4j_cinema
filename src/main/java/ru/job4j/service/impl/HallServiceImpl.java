package ru.job4j.service.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Hall;
import ru.job4j.repository.HallRepository;
import ru.job4j.service.HallService;

import java.util.Collection;
import java.util.Optional;

/**
 * Бизнес логика для справочника Залы.
 */
@Service
@ThreadSafe
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;

    public HallServiceImpl(HallRepository sql2oHallRepositoryImpl) {
        this.hallRepository = sql2oHallRepositoryImpl;
    }

    @Override
    public Collection<Hall> findAll() {
        return hallRepository.findAll();
    }

    @Override
    public Optional<Hall> findById(int hallId) {
        return hallRepository.findById(hallId);
    }
}