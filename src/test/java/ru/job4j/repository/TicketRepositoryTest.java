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
import ru.job4j.model.Ticket;
import ru.job4j.repository.impl.Sql2oTicketRepositoryImpl;
import ru.job4j.repository.impl.Sql2oUserRepositoryImpl;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class TicketRepositoryTest {
    private static TicketRepository ticketRepository;
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

        ticketRepository = new Sql2oTicketRepositoryImpl(sql2o);
        liquibase(datasource, liquibaseSchema);
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
    public void clearTickets() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("TRUNCATE TABLE tickets;");
            query.executeUpdate();
        }
    }

    @DisplayName("Save then ok")
    @Test
    void save() {
        Ticket ticket = new Ticket(1, 1, 2, 2, 1);

        Ticket saveTicket = ticketRepository.save(ticket);
        Optional<Ticket> resultTicket = ticketRepository.findById(saveTicket.getId());

        Assertions.assertTrue(resultTicket.isPresent());

        assertThat(saveTicket).usingRecursiveComparison().isEqualTo(resultTicket.orElseThrow());
    }

    @DisplayName("findAll then size Of List more then 0")
    @Test
    void findAll() {
        int beforeSize = ticketRepository.findAll().size();
        Ticket ticket = new Ticket(1, 1, 2, 2, 1);

        ticketRepository.save(ticket);
        int afterSize = ticketRepository.findAll().size();

        Assertions.assertNotEquals(beforeSize, afterSize);
        Assertions.assertEquals(beforeSize + 1, afterSize);
    }

    @DisplayName("findById save then getId == saveId")
    @Test
    void findById() {
        Ticket ticket = new Ticket(1, 1, 2, 2, 1);

        Ticket saveTicket = ticketRepository.save(ticket);
        Ticket resultTicket = ticketRepository.findById(saveTicket.getId()).orElseThrow();

        assertThat(saveTicket).usingRecursiveComparison().isEqualTo(resultTicket);
    }

    @Test
    void findBySession() {
        int expectedSessionId = 2;

        Ticket ticket = new Ticket(1, expectedSessionId, 14, 15, 1);

        Ticket saveTicket = ticketRepository.save(ticket);
        List<Ticket> ticketList = ticketRepository.findBySession(expectedSessionId);

        assertThat(saveTicket).usingRecursiveComparison().isEqualTo(ticketList.get(0));
    }

    @Test
    void findByPlace() {
        int expectedSessionId = 12;
        int expectedRowNumber = 13;
        int expectedPlaceNumber = 15;
        Ticket ticket = new Ticket(1, expectedSessionId, expectedRowNumber, expectedPlaceNumber, 1);

        Ticket saveTicket = ticketRepository.save(ticket);
        Ticket resultTicket = ticketRepository.findByPlace(expectedSessionId, expectedRowNumber, expectedPlaceNumber).orElseThrow();

        assertThat(saveTicket).usingRecursiveComparison().isEqualTo(resultTicket);
    }
}