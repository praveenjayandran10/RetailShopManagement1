package com.retailshop.service;

import com.retailshop.config.DBConnection;
import com.retailshop.exception.ValidationException;
import com.retailshop.model.CartItem;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SaleService {

    public int completeSale(int customerId, int employeeId,
                            List<CartItem> cart,
                            BigDecimal cashReceived)
            throws SQLException, ValidationException {

        if (customerId <= 0 || employeeId <= 0) {
            throw new ValidationException(
                "Select a customer and cashier."
            );
        }

        if (cart == null || cart.isEmpty()) {
            throw new ValidationException("The cart is empty.");
        }

        if (cashReceived == null || cashReceived.signum() < 0
                || cashReceived.scale() > 2) {
            throw new ValidationException("Enter a valid cash amount.");
        }

        // Sort products by ID so stock locks use a consistent order.
        Map<Integer, CartItem> items = new TreeMap<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart) {

            if (item == null || item.getProductId() <= 0
                    || item.getQuantity() <= 0
                    || item.getUnitPrice() == null
                    || item.getUnitPrice().signum() <= 0) {
                throw new ValidationException("Invalid cart item.");
            }

            if (items.put(item.getProductId(), item) != null) {
                throw new ValidationException(
                    "The cart contains duplicate product rows."
                );
            }

            total = total.add(item.getLineTotal());
        }

        if (cashReceived.compareTo(total) < 0) {
            throw new ValidationException(
                "Cash received is less than the total."
            );
        }

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // Lock and check each product before saving.
                String checkSql = """
                    SELECT name, price, stock_qty
                    FROM products
                    WHERE product_id = ?
                    FOR UPDATE
                    """;

                try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                    for (CartItem item : items.values()) {
                        statement.setInt(1, item.getProductId());

                        try (ResultSet results =
                                 statement.executeQuery()) {

                            if (!results.next()) {
                                throw new ValidationException(
                                    "Product no longer exists: "
                                    + item.getProductName()
                                );
                            }

                            if (results.getInt("stock_qty")
                                    < item.getQuantity()) {
                                throw new ValidationException(
                                    "Insufficient stock for "
                                    + item.getProductName()
                                );
                            }

                            if (results.getBigDecimal("price")
                                    .compareTo(item.getUnitPrice()) != 0) {
                                throw new ValidationException(
                                    "Price changed for "
                                    + item.getProductName()
                                    + ". Remove it and add it again."
                                );
                            }
                        }
                    }
                }

                int saleId;

                String saleSql = """
                    INSERT INTO sales
                    (customer_id, employee_id, total_amount)
                    VALUES (?, ?, ?)
                    """;

                try (PreparedStatement statement =
                         connection.prepareStatement(
                             saleSql,
                             Statement.RETURN_GENERATED_KEYS)) {

                    statement.setInt(1, customerId);
                    statement.setInt(2, employeeId);
                    statement.setBigDecimal(3, total);
                    statement.executeUpdate();

                    try (ResultSet keys =
                             statement.getGeneratedKeys()) {

                        if (!keys.next()) {
                            throw new SQLException(
                                "Could not obtain the sale ID."
                            );
                        }

                        saleId = keys.getInt(1);
                    }
                }

                String itemSql = """
                    INSERT INTO sale_items
                    (sale_id, product_id, quantity, unit_price)
                    VALUES (?, ?, ?, ?)
                    """;

                String stockSql = """
                    UPDATE products
                    SET stock_qty = stock_qty - ?
                    WHERE product_id = ?
                    """;

                try (PreparedStatement itemStatement =
                         connection.prepareStatement(itemSql);
                     PreparedStatement stockStatement =
                         connection.prepareStatement(stockSql)) {

                    for (CartItem item : items.values()) {

                        itemStatement.setInt(1, saleId);
                        itemStatement.setInt(2, item.getProductId());
                        itemStatement.setInt(3, item.getQuantity());
                        itemStatement.setBigDecimal(
                            4, item.getUnitPrice()
                        );
                        itemStatement.executeUpdate();

                        stockStatement.setInt(1, item.getQuantity());
                        stockStatement.setInt(2, item.getProductId());

                        if (stockStatement.executeUpdate() != 1) {
                            throw new SQLException(
                                "Could not update product stock."
                            );
                        }
                    }
                }

                String paymentSql = """
                    INSERT INTO payments
                    (sale_id, payment_method, amount_paid)
                    VALUES (?, 'CASH', ?)
                    """;

                try (PreparedStatement statement =
                         connection.prepareStatement(paymentSql)) {

                    statement.setInt(1, saleId);

                    // Store money retained by the shop.
                    // Customer change is not sales revenue.
                    statement.setBigDecimal(2, total);
                    statement.executeUpdate();
                }

                connection.commit();
                return saleId;

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