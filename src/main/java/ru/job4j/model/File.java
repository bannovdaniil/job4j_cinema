package ru.job4j.model;

/**
 * Файлы (аватарки и постеры)
 */
public class File {
    private int id;
    private final String name;
    private final String path;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }
}