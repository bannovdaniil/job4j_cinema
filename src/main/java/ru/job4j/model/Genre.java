package ru.job4j.model;

/**
 * Сущность Жанры фильмов (справочник).
 */
public class Genre {
    private int id;
    private String name;

    public Genre() {
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
}
