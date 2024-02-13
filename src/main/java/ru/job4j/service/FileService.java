package ru.job4j.service;

import ru.job4j.dto.FileDto;
import ru.job4j.model.File;

public interface FileService {

    File save(FileDto fileDto);

    FileDto getFileById(int id);

    void deleteById(int id);
}