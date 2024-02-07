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
import ru.job4j.model.File;
import ru.job4j.repository.impl.Sql2oFileRepositoryImpl;
import ru.job4j.repository.impl.Sql2oUserRepositoryImpl;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

class FileRepositoryTest {
    private static FileRepository sql2oFileRepository;
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

        sql2oFileRepository = new Sql2oFileRepositoryImpl(sql2o);
        clearFilesTable();
    }

    private static void clearFilesTable() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("DELETE FROM film_sessions;");
            query.executeUpdate();
            query = connection.createQuery("DELETE FROM films;");
            query.executeUpdate();
            query = connection.createQuery("DELETE FROM files;");
            query.executeUpdate();
        }
    }

    @AfterAll
    static void afterAll() throws Exception {
        liquibase(datasource, liquibaseSchema);
    }

    public static void liquibase(DataSource dataSource, String defaultLiquibaseChangelog) throws Exception {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
        Liquibase liquibase = new Liquibase(defaultLiquibaseChangelog, new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.clearCheckSums();
        liquibase.update();
    }

    @AfterEach
    public void clearFiles() {
        clearFilesTable();
    }

    @DisplayName("Save and Find User by email and password Then Same")
    @Test
    void save() {
        String expectedName = "test_file.txt";
        String expectedPath = "/test_files";
        File file = new File(expectedName, expectedPath);

        File expectedFile = sql2oFileRepository.save(file);
        File resultFile = sql2oFileRepository.findById(expectedFile.getId()).orElseThrow();

        Assertions.assertEquals(expectedName, resultFile.getName());
        Assertions.assertEquals(expectedPath, resultFile.getPath());
    }

    @DisplayName("findById , Object then save and then find is equals")
    @Test
    void findById() {
        String expectedName = "test_file.txt";
        String expectedPath = "/test_files";
        File file = new File(expectedName, expectedPath);

        File expectedFile = sql2oFileRepository.save(file);
        File resultFile = sql2oFileRepository.findById(expectedFile.getId()).orElseThrow();

        Assertions.assertEquals(expectedName, resultFile.getName());
        Assertions.assertEquals(expectedPath, resultFile.getPath());
        Assertions.assertEquals(expectedFile.getId(), resultFile.getId());
    }

    @DisplayName("findById , save find and delete not found")
    @Test
    void deleteById() {
        String expectedName = "test_file.txt";
        String expectedPath = "/test_files";
        File file = new File(expectedName, expectedPath);

        File expectedFile = sql2oFileRepository.save(file);
        Optional<File> checkExistFile = sql2oFileRepository.findById(expectedFile.getId());
        Assertions.assertTrue(checkExistFile.isPresent());

        sql2oFileRepository.deleteById(expectedFile.getId());

        Optional<File> checkDeleteFile = sql2oFileRepository.findById(expectedFile.getId());
        Assertions.assertTrue(checkDeleteFile.isEmpty());
    }
}