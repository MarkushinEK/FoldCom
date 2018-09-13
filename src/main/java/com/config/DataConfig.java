package com.config;

import com.dataSet.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DataConfig {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/foldercom";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "test";
    private static final String DIALECT ="org.hibernate.dialect.MySQLDialect";
    private static final String SHOW_SQL = "true";
    private static final String HBM2DDL = "create";
    private SessionFactory sessionFactory;

    public DataConfig() {
        Configuration config = new Configuration();
        config.addAnnotatedClass(Folder.class).
                addAnnotatedClass(RelationshipEntity.class);

        config.setProperty("hibernate.dialect", DIALECT);
        config.setProperty("hibernate.connection.driver_class", DRIVER);
        config.setProperty("hibernate.connection.url", URL);
        config.setProperty("hibernate.connection.username", USERNAME);
        config.setProperty("hibernate.connection.password", PASSWORD);
        config.setProperty("hibernate.show_sql", SHOW_SQL);
        config.setProperty("hibernate.hbm2ddl.auto", HBM2DDL);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(config.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
