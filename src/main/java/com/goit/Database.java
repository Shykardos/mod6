package com.goit;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class Database {
    private static Database instance;
    private final JdbcDataSource dataSource;

    private Database() {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("admin1");
        dataSource.setPassword("12345");

        configureFlyway();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DataSource getDataSource() {
        return dataSource;
    }

    private void configureFlyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(getDataSource())
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();
    }
}
//
//import org.flywaydb.core.Flyway;
//import org.h2.jdbcx.JdbcDataSource;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import javax.sql.DataSource;
//
//public class Database {
//    private static Database instance;
//    private final JdbcDataSource dataSource;
//
//    private Database() {
//        dataSource = new JdbcDataSource();
//        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
//        dataSource.setUser("admin1");
//        dataSource.setPassword("12345");
//
//        configureFlyway();
//    }
//
//    public static synchronized Database getInstance() {
//        if (instance == null) {
//            instance = new Database();
//        }
//        return instance;
//    }
//
//    public Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//
//    private DataSource getDataSource() {
//        return dataSource;
//    }
//
//    private void configureFlyway() {
//        Flyway flyway = Flyway.configure()
//                .dataSource(getDataSource())
//                .locations("classpath:db/migration")
//                .load();
//        flyway.migrate();
//    }
//}