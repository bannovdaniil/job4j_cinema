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
import ru.job4j.model.FilmSession;
import ru.job4j.repository.impl.Sql2oFilmSessionRepositoryImpl;
import ru.job4j.repository.impl.Sql2oUserRepositoryImpl;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class FilmSessionRepositoryTest {
    private static FilmSessionRepository filmSessionRepository;
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

        filmSessionRepository = new Sql2oFilmSessionRepositoryImpl(sql2o);
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
        FilmSession filmSession = new FilmSession(1, 1,
                LocalDateTime.now().withNano(0),
                LocalDateTime.now().withNano(0),
                200
        );
        FilmSession saveFilmSession = filmSessionRepository.save(filmSession);
        Optional<FilmSession> resultFilmSessionOptional = filmSessionRepository.findById(saveFilmSession.getId());

        Assertions.assertTrue(resultFilmSessionOptional.isPresent());

        assertThat(saveFilmSession).usingRecursiveComparison().isEqualTo(resultFilmSessionOptional.orElseThrow());
    }

    @DisplayName("Update then ok")
    @Test
    void update() {
        FilmSession filmSession = new FilmSession(1, 1,
                LocalDateTime.now().withNano(0),
                LocalDateTime.now().withNano(0),
                200
        );
        FilmSession saveFilmSession = filmSessionRepository.save(filmSession);

        saveFilmSession.setFilmId(2);
        saveFilmSession.setHallId(2);
        saveFilmSession.setStartTime(LocalDateTime.now().withNano(0));
        saveFilmSession.setEndTime(LocalDateTime.now().withNano(0));
        saveFilmSession.setPrice(300);

        filmSessionRepository.update(saveFilmSession);

        Optional<FilmSession> resultFilmOptional = filmSessionRepository.findById(saveFilmSession.getId());

        Assertions.assertTrue(resultFilmOptional.isPresent());

        assertThat(saveFilmSession).usingRecursiveComparison().isEqualTo(resultFilmOptional.orElseThrow());
    }

    @DisplayName("findAll then size Of List more then 0")
    @Test
    void findAll() {
        int beforeSize = filmSessionRepository.findAll().size();
        FilmSession filmSession = new FilmSession(1, 1,
                LocalDateTime.now().withNano(0),
                LocalDateTime.now().withNano(0),
                200
        );

        filmSessionRepository.save(filmSession);
        int afterSize = filmSessionRepository.findAll().size();

        Assertions.assertNotEquals(beforeSize, afterSize);
        Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("findById save then getId == saveId")
    @Test
    void findById() {
        FilmSession filmSession = new FilmSession(1, 1,
                LocalDateTime.now().withNano(0),
                LocalDateTime.now().withNano(0),
                200
        );
        FilmSession saveFilmSession = filmSessionRepository.save(filmSession);

        FilmSession resultFilmSession = filmSessionRepository.findById(saveFilmSession.getId()).orElseThrow();

        assertThat(saveFilmSession).usingRecursiveComparison().isEqualTo(resultFilmSession);
    }
}