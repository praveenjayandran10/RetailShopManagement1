package com.retailshop.service;

import com.retailshop.config.DBConnection;
import com.retailshop.exception.ValidationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.retailshop.service.StockService;
import com.retailshop.exception.ValidationException;

public class StockService {

    public void addStock(int productId, int employeeId,
                         int quantity, String notes)
            throws SQLException, ValidationException {

        if (productId <= 0 || employeeId <= 0) {
            throw new ValidationException(
                "Select a product and employee."
            );
        }

        if (quantity <= 0) {
            throw new ValidationException(
                "Quantity must be greater than zero."
            );
        }

        if (notes == null) {
            notes = "";
        }

        notes = notes.trim();

        if (notes.length() > 255) {
            throw new ValidationException(
                "Notes must not exceed 255 characters."
            );
        }

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                String checkSql = """
                    SELECT stock_qty
                    FROM products
                    WHERE product_id = ?
                    FOR UPDATE
                    """;

                try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                    statement.setInt(1, productId);

                    try (ResultSet results =
                             statement.executeQuery()) {

                        if (!results.next()) {
                            throw new ValidationException(
                                "This product no longer exists."
                            );
                        }

                        long newStock =
                            (long) results.getInt("stock_qty")
                            + quantity;

                        if (newStock > Integer.MAX_VALUE) {
                            throw new ValidationException(
                                "The resulting stock quantity is too large."
                            );
                        }
                    }
                }

                String entrySql = """
                    INSERT INTO stock_entries
                    (product_id, employee_id, quantity, notes)
                    VALUES (?, ?, ?, ?)
                    """;

                try (PreparedStatement statement =
                         connection.prepareStatement(entrySql)) {

                    statement.setInt(1, productId);
                    statement.setInt(2, employeeId);
                    statement.setInt(3, quantity);
                    statement.setString(4, notes);
                    statement.executeUpdate();
                }

                String updateSql = """
                    UPDATE products
                    SET stock_qty = stock_qty + ?
                    WHERE product_id = ?
                    """;

                try (PreparedStatement statement =
                         connection.prepareStatement(updateSql)) {

                    statement.setInt(1, quantity);
                    statement.setInt(2, productId);

                    if (statement.executeUpdate() != 1) {
                        throw new SQLException(
                            "Could not update stock."
                        );
                    }
                }

                connection.commit();

            } catch (SQLException | ValidationException
                     | RuntimeException ex) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    ex.addSuppressed(rollbackError);
                }

                throw ex;
            }
        }
    }
}