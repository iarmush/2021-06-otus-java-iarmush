package ru.otus.crm.dbmigrations;

import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationsExecutorFlyway {

    private static final Logger logger = LoggerFactory.getLogger(MigrationsExecutorFlyway.class);

    private final Flyway flyway;

    public MigrationsExecutorFlyway(Configuration configuration) {
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        flyway = Flyway.configure()
            .dataSource(dbUrl, dbUserName, dbPassword)
            .locations("classpath:/db/migration")
            .load();
    }

    public void executeMigrations() {
        logger.info("db migration started...");
        flyway.migrate();
        logger.info("db migration finished.");
    }
}
