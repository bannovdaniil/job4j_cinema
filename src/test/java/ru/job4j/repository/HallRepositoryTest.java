package ru.job4j.repository;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.configuration.DatasourceConfiguration;
import ru.job4j.model.Hall;
import ru.job4j.repository.impl.Sql2oHallRepositoryImpl;
import ru.job4j.repository.impl.Sql2oUserRepositoryImpl;

import javax.sql.DataSource;
import java.util.Properties;

class HallRepositoryTest {
    private static HallRepository sql2oHallRepository;
    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryImpl.class.getClassLoader().getResourceAsStream("db/liquibase_test.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("url");
        var username = properties.getProperty("username");
        var password = properties.getProperty("password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepositoryImpl(sql2o);
        liquibase(datasource, properties.getProperty("changeLogFile"));
    }

    public static void liquibase(DataSource dataSource, String defaultLiquibaseChangelog) throws Exception {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
        Liquibase liquibase = new Liquibase(defaultLiquibaseChangelog, new ClassLoaderResourceAccessor(), database);
        liquibase.dropAll();
        liquibase.clearCheckSums();
        liquibase.update();
    }

    @DisplayName("findAll then size Of List more then 0")
    @Test
    void findAll() {
        int size = sql2oHallRepository.findAll().size();

        Assertions.assertNotEquals(0, size);
    }

    @DisplayName("findById 1 then getId == 1")
    @Test
    void findById() {
        int exceptedId = 1;

        Hall hall = sql2oHallRepository.findById(exceptedId).orElseThrow();

        Assertions.assertEquals(exceptedId, hall.getId());
    }
}