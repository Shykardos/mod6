package com.goit.CRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Connection connection;

    public ClientService(Connection connection) {
        this.connection = connection;
    }

    public long create(String name) throws SQLException {
        validateName(name);

        String query = "INSERT INTO client (name) VALUES (?)";
        PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, name);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getLong(1);
        }

        throw new SQLException("Creating client failed, no ID obtained.");
    }

    public String getById(long id) throws SQLException {
        String query = "SELECT name FROM client WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("name");
        }

        return null;
    }

    public void setName(long id, String name) throws SQLException {
        validateName(name);

        String query = "UPDATE client SET name = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        String query = "DELETE FROM client WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT id, name FROM client";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            clients.add(new Client(rs.getLong("id"), rs.getString("name")));
        }

        return clients;
    }

    private void validateName(String name) {
        int length = name.length();
        if (length < 3 || length > 25) {
            throw new IllegalArgumentException("Client name must be between 3 and 25 characters long");
        }
    }
}