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
import ru.job4j.exception.UniqueConstraintException;
import ru.job4j.model.User;
import ru.job4j.repository.impl.Sql2oUserRepositoryImpl;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {
    private static UserRepository sql2oUserRepository;
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

        sql2oUserRepository = new Sql2oUserRepositoryImpl(sql2o);
        clearUsersTable();
    }

    private static void clearUsersTable() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("TRUNCATE TABLE users;");
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
    public void clearUsers() {
        clearUsersTable();
    }

    @DisplayName("Save and Find User by email and password Then Same")
    @Test
    void whenSaveThenGetSame() throws UniqueConstraintException {
        User user = sql2oUserRepository.save(new User("Egor", "name@test.ru", "password")).orElseThrow();
        Optional<User> savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @DisplayName("Save double Email then get Empty")
    @Test
    void whenSaveDoubleEmailThenGetException() throws UniqueConstraintException {
        Optional<User> user1 = sql2oUserRepository.save(new User("Egor", "name@test.ru", "password"));
        Optional<User> user2;
        UniqueConstraintException exception = Assertions.assertThrows(
                UniqueConstraintException.class, () ->
                        sql2oUserRepository.save(new User("Semen", "name@test.ru", "test"))
        );

        assertThat(user1).isPresent();
    }

    @DisplayName("Find User by email and Wrong password Then Emptu")
    @Test
    void findByEmailAndWrongPasswordThenError() throws UniqueConstraintException {
        User user = sql2oUserRepository.save(new User("Egor", "name@test.ru", "password")).orElseThrow();
        Optional<User> findUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), "bad password");
        assertThat(findUser).isEmpty();
    }

    @DisplayName("Find User by Wrong email and password Then Emptu")
    @Test
    void findByWrongEmailAndPasswordThenError() throws UniqueConstraintException {
        User user = sql2oUserRepository.save(new User("Egor", "name@test.ru", "password")).orElseThrow();
        Optional<User> findUser = sql2oUserRepository.findByEmailAndPassword("wrong@user.ru", user.getPassword());
        assertThat(findUser).isEmpty();
    }

}