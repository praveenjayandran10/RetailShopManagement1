package com.retailshop.dao;

import com.retailshop.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class CustomerDAO {

    public DefaultTableModel getAll() throws SQLException {

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Phone", "Email"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = """
            SELECT customer_id, name, phone, email
            FROM customers
            ORDER BY customer_id
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                model.addRow(new Object[]{
                    results.getInt("customer_id"),
                    results.getString("name"),
                    results.getString("phone"),
                    results.getString("email")
                });
            }
        }

        return model;
    }

    public void add(String name, String phone, String email)
            throws SQLException {

        String sql = """
            INSERT INTO customers (name, phone, email)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);

            statement.executeUpdate();
        }
    }
    public void update(int id, String name, String phone, String email)
        throws SQLException {

    String sql = """
        UPDATE customers
        SET name = ?, phone = ?, email = ?
        WHERE customer_id = ?
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setString(1, name);
        statement.setString(2, phone);
        statement.setString(3, email);
        statement.setInt(4, id);

        if (statement.executeUpdate() != 1) {
            throw new SQLException("Customer no longer exists.");
        }
    }
}
    public void delete(int id) throws SQLException {

    String sql = "DELETE FROM customers WHERE customer_id = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        if (statement.executeUpdate() != 1) {
            throw new SQLException("Customer no longer exists.");
        }
    }
}
}
