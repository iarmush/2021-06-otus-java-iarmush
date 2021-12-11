package ru.otus.demo;

import java.util.List;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        Client clientFirst = new Client("client1");
        clientFirst.setAddress(new Address("address1"));
        clientFirst.setPhones(List.of(new Phone("89999999991", clientFirst), new Phone("89999999992", clientFirst)));
        dbServiceClient.saveClient(clientFirst);

        var clientSecond = dbServiceClient.saveClient(new Client("client2"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
            .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);
///
        Client client3 = new Client(clientSecondSelected.getId(), "client3");
        client3.setPhones(List.of(new Phone("89999999993", client3)));
        client3.setAddress(new Address("address 3"));
        dbServiceClient.saveClient(client3);
        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
            .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }
}
