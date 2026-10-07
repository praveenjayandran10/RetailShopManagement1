package com.retailshop.dao;

import com.retailshop.config.DBConnection;
import com.retailshop.model.ComboItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.retailshop.model.CartItem;

public class SalesLookupDAO {

    private List<ComboItem> readItems(String sql)
            throws SQLException {

        List<ComboItem> items = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                items.add(new ComboItem(
                    results.getInt("id"),
                    results.getString("name")
                ));
            }
        }

        return items;
    }

    public List<ComboItem> getCustomers() throws SQLException {
        return readItems("""
            SELECT customer_id AS id, name
            FROM customers
            ORDER BY customer_id
            """);
    }

    public List<ComboItem> getEmployees() throws SQLException {
        return readItems("""
            SELECT employee_id AS id, name
            FROM employees
            ORDER BY employee_id
            """);
    }

    public List<ComboItem> getProducts() throws SQLException {
        return readItems("""
            SELECT product_id AS id, name
            FROM products
            WHERE stock_qty > 0
            ORDER BY name
            """);
    }
    public CartItem getCartItem(int productId, int quantity)
        throws SQLException {

    String sql = """
        SELECT name, price, stock_qty
        FROM products
        WHERE product_id = ?
        """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
             connection.prepareStatement(sql)) {

        statement.setInt(1, productId);

        try (ResultSet results = statement.executeQuery()) {

            if (!results.next()) {
                throw new SQLException("This product no longer exists.");
            }

            int available = results.getInt("stock_qty");

            if (quantity <= 0 || quantity > available) {
                throw new SQLException(
                    "Available stock: " + available
                    + ". Enter a quantity within this amount."
                );
            }

            return new CartItem(
                productId,
                results.getString("name"),
                results.getBigDecimal("price"),
                quantity
            );
        }
    }
}
    public List<ComboItem> getAllProducts() throws SQLException {

    return readItems("""
        SELECT product_id AS id, name
        FROM products
        ORDER BY name
        """);
}
}