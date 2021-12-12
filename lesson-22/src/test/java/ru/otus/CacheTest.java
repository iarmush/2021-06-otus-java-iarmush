package ru.otus;

import java.util.Date;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;

public class CacheTest {

    private static final Logger log = LoggerFactory.getLogger(CacheTest.class);
    private static final int COUNT = 1000;
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
    private static TransactionManager transactionManager;
    private static DataTemplate<Client> clientTemplate;

    @BeforeAll
    static void setUp() {
        new MigrationsExecutorFlyway(configuration).executeMigrations();
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);
        transactionManager = new TransactionManagerHibernate(sessionFactory);
        clientTemplate = new DataTemplateHibernate<>(Client.class);
    }

    @Test
    void testCache() {
        var cache = new MyCache<Long, Client>();
        var listener = new HwListener<Long, Client>() {
            @Override
            public void notify(Long key, Client value, String action) {
                log.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
        cache.addListener(listener);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, cache);

        long start = new Date().getTime();
        for (int i = 0; i <= COUNT; i++) {
            long id = dbServiceClient.saveClient(new Client("name" + i)).getId();
            dbServiceClient.getClient(id);
        }
        long end = new Date().getTime();
        log.info("time with cache:{}", end - start);
        log.info("Cache size: {} ", cache.size());
        cache.removeListener(listener);
    }

    @Test
    void testNoCache() {
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, null);

        long start = new Date().getTime();
        for (int i = 0; i <= COUNT; i++) {
            long id = dbServiceClient.saveClient(new Client("name" + i)).getId();
            dbServiceClient.getClient(id);
        }
        long end = new Date().getTime();
        log.info("time without cache:{}", end - start);
    }
}
