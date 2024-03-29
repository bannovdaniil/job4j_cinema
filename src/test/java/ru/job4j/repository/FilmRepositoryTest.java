package ru.job4j.repository;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.configuration.DatasourceConfiguration;
import ru.job4j.model.Film;
import ru.job4j.repository.impl.Sql2oFilmRepositoryImpl;
import ru.job4j.repository.impl.Sql2oUserRepositoryImpl;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class FilmRepositoryTest {
    private static FilmRepository filmRepository;
    private static Sql2o sql2o;
    private static DataSource datasource;
    private static String liquibaseSchema;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryImpl.class.getClassLoader().getResourceAsStream("db/liquibase_test.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("url");
        var username = properties.getProperty("username");
        var password = properties.getProperty("password");
        liquibaseSchema = properties.getProperty("changeLogFile");

        var configuration = new DatasourceConfiguration();
        datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        filmRepository = new Sql2oFilmRepositoryImpl(sql2o);
        liquibase(datasource, liquibaseSchema);
        clearFilmsTable();
    }

    @AfterAll
    static void afterAll() throws Exception {
        liquibase(datasource, liquibaseSchema);
    }

    private static void clearFilmsTable() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("DELETE FROM film_sessions;");
            query.executeUpdate();
            query = connection.createQuery("DELETE FROM films;");
            query.executeUpdate();
        }
    }

    public static void liquibase(DataSource dataSource, String defaultLiquibaseChangelog) throws Exception {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
        Liquibase liquibase = new Liquibase(defaultLiquibaseChangelog, new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.clearCheckSums();
        liquibase.update();
    }

    @AfterEach
    public void clearFilms() {
        clearFilmsTable();
    }

    @DisplayName("Save then ok")
    @Test
    void save() {
        Film film = new Film("film new", "description film new", 2020, 1, 19, 120, 1);
        Film saveFilm = filmRepository.save(film);
        Optional<Film> resultFilmOptional = filmRepository.findById(saveFilm.getId());

        Assertions.assertTrue(resultFilmOptional.isPresent());

        assertThat(saveFilm).usingRecursiveComparison().isEqualTo(resultFilmOptional.orElseThrow());
    }

    @DisplayName("Update then ok")
    @Test
    void update() {
        Film film = new Film("film new", "description film new", 2020, 1, 19, 120, 1);
        Film saveFilm = filmRepository.save(film);
        saveFilm.setName("edit film");
        saveFilm.setDescription("edit desc");
        saveFilm.setYear(2023);
        saveFilm.setGenreId(2);
        saveFilm.setMinimalAge(12);
        saveFilm.setDurationInMinutes(100);
        saveFilm.setFileId(2);

        filmRepository.update(saveFilm);

        Optional<Film> resultFilmOptional = filmRepository.findById(saveFilm.getId());

        Assertions.assertTrue(resultFilmOptional.isPresent());

        assertThat(saveFilm).usingRecursiveComparison().isEqualTo(resultFilmOptional.orElseThrow());
    }

    @DisplayName("findAll then size Of List more then 0")
    @Test
    void findAll() {
        int beforeSize = filmRepository.findAll().size();
        Film film = new Film("film findAll", "description film findAll", 2020, 1, 19, 120, 1);
        filmRepository.save(film);
        int afterSize = filmRepository.findAll().size();

        Assertions.assertNotEquals(beforeSize, afterSize);
        Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("findById save then getId == saveId")
    @Test
    void findById() {
        Film film = new Film("film name find by id", "description film  find by id", 2020, 1, 19, 120, 1);
        Film exceptedFilm = filmRepository.save(film);

        Film resultFilm = filmRepository.findById(exceptedFilm.getId()).orElseThrow();

        Assertions.assertEquals(exceptedFilm.getId(), resultFilm.getId());
    }

}