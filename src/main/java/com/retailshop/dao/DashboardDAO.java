package com.retailshop.dao;

import com.retailshop.config.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class DashboardDAO {

    public int getProductCount() throws SQLException {

        String sql = "SELECT COUNT(*) FROM products";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            results.next();
            return results.getInt(1);
        }
    }

    public BigDecimal getTodaySales() throws SQLException {

        String sql = """
            SELECT COALESCE(SUM(total_amount), 0)
            FROM sales
            WHERE sale_date >= CURRENT_DATE()
              AND sale_date < CURRENT_DATE() + INTERVAL 1 DAY
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            results.next();
            return results.getBigDecimal(1);
        }
    }

    public DefaultTableModel getLowStock() throws SQLException {

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Product", "Stock"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = """
            SELECT product_id, name, stock_qty
            FROM products
            WHERE stock_qty <= 5
            ORDER BY stock_qty, name
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                model.addRow(new Object[]{
                    results.getInt("product_id"),
                    results.getString("name"),
                    results.getInt("stock_qty")
                });
            }
        }

        return model;
    }
}