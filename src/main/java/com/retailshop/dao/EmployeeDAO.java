package com.retailshop.dao;

import com.retailshop.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class EmployeeDAO {

    public DefaultTableModel getAll() throws SQLException {

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Role"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = """
            SELECT employee_id, name, role
            FROM employees
            ORDER BY employee_id
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                model.addRow(new Object[]{
                    results.getInt("employee_id"),
                    results.getString("name"),
                    results.getString("role")
                });
            }
        }

        return model;
    }
    public void add(String name, String role) throws SQLException {

    String sql = """
        INSERT INTO employees (name, role)
        VALUES (?, ?)
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setString(1, name);
        statement.setString(2, role);
        statement.executeUpdate();
    }
}
    public void update(int id, String name, String role)
        throws SQLException {

    String sql = """
        UPDATE employees
        SET name = ?, role = ?
        WHERE employee_id = ?
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setString(1, name);
        statement.setString(2, role);
        statement.setInt(3, id);

        if (statement.executeUpdate() != 1) {
            throw new SQLException("Employee no longer exists.");
        }
    }
}
    public void delete(int id) throws SQLException {

    String sql = "DELETE FROM employees WHERE employee_id = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        if (statement.executeUpdate() != 1) {
            throw new SQLException("Employee no longer exists.");
        }
    }
}
}