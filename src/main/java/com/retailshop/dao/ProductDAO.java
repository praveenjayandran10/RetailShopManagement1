package com.retailshop.dao;

import com.retailshop.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;


public class ProductDAO {
    
    public void add(String name, String category,
                BigDecimal price, int stock) throws SQLException {

    String sql = """
        INSERT INTO products (name, category, price, stock_qty)
        VALUES (?, ?, ?, ?)
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setString(1, name);
        statement.setString(2, category);
        statement.setBigDecimal(3, price);
        statement.setInt(4, stock);

        statement.executeUpdate();
    }
}

    public DefaultTableModel getAll() throws SQLException {

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Category", "Price", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = """
            SELECT product_id, name, category, price, stock_qty
            FROM products
            ORDER BY product_id
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                model.addRow(new Object[]{
                    results.getInt("product_id"),
                    results.getString("name"),
                    results.getString("category"),
                    results.getBigDecimal("price"),
                    results.getInt("stock_qty")
                });
            }
        }
        

        return model;
    }
    public void update(int id, String name, String category,
                   BigDecimal price, int stock) throws SQLException {

    String sql = """
        UPDATE products
        SET name = ?, category = ?, price = ?, stock_qty = ?
        WHERE product_id = ?
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setString(1, name);
        statement.setString(2, category);
        statement.setBigDecimal(3, price);
        statement.setInt(4, stock);
        statement.setInt(5, id);

        statement.executeUpdate();
    }
}
    public void delete(int id) throws SQLException {

    String sql = "DELETE FROM products WHERE product_id = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        if (statement.executeUpdate() != 1) {
            throw new SQLException("Product no longer exists.");
        }
    }
}
}