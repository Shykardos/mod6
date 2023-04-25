package com.goit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {

    public List<MaxProjectCountClient> findMaxProjectsClient() {
        String queryFile = "/sql/find_max_projects_client.sql";
        String sqlQuery;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(DatabaseQueryService.class.getResourceAsStream(queryFile), StandardCharsets.UTF_8))) {
            sqlQuery = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error reading SQL query file", e);
        }

        List<MaxProjectCountClient> result = new ArrayList<>();

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int projectCount = resultSet.getInt("project_count");
                result.add(new MaxProjectCountClient(name, projectCount));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        }

        return result;
    }
}
