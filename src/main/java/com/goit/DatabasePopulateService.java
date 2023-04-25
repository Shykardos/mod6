package com.goit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePopulateService {
    public static void main(String[] args) {
        String populateSqlFile = "/Users/volodymyrshynkarenko/Documents/Java goit/mod3/mod4/src/main/resources/sql/populate_db.sql";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(DatabasePopulateService.class.getResourceAsStream(populateSqlFile), StandardCharsets.UTF_8))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line);
            }
            String[] sqlStatements = sqlBuilder.toString().split(";");

            try (Connection connection = Database.getInstance().getConnection()) {
                for (String sqlStatement : sqlStatements) {
                    if (!sqlStatement.trim().isEmpty()) {
                        try (Statement stmt = connection.createStatement()) {
                            stmt.execute(sqlStatement);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error executing SQL statements", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading SQL populate file", e);
        }
    }
}
