package com.goit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {

    public List<MaxProjectCountClient> findMaxProjectsClient() {
        String queryFile = "src/main/resources/sql/find_max_projects_client.sql";
        String sqlQuery;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(DatabaseQueryService.class.getResourceAsStream(queryFile), StandardCharsets.UTF_8))) {
            sqlQuery = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error reading SQL query file", e);
        }

        List<MaxProjectCountClient> result = new ArrayList<>();

        try (Connection connection = Database.getInstance().getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                 ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int projectCount = resultSet.getInt("project_count");
                    result.add(new MaxProjectCountClient(name, projectCount));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error executing SQL query", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting connection", e);
        }

        return result;
    }
}