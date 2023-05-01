package com.goit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;

    private static final String DB_URL ="jdbc:h2:./test1";
            //"http://192.168.31.143:8082?key=151bf6fa966d871bb5d53205682820d7e503d1343abe420a17c828c737bea05f";
    //"jdbc:h2:~/data/parserpalce";

    private static final String DB_USER = "admin1";
    private static final String DB_PASSWORD = "12345";

    private Database() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Database connection failed!", e);
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}