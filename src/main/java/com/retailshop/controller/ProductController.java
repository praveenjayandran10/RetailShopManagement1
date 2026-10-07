package com.retailshop.controller;

import com.retailshop.dao.ProductDAO;
import com.retailshop.exception.ValidationException;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ProductController {

    private final ProductDAO dao = new ProductDAO();

    private void validate(String name, String category,
                          BigDecimal price, int stock)
            throws ValidationException {

        if (name == null || name.isBlank()
                || name.trim().length() > 100) {

            throw new ValidationException(
                "Enter a product name of 1 to 100 characters."
            );
        }

        if (category == null || category.isBlank()
                || category.trim().length() > 50) {

            throw new ValidationException(
                "Enter a category of 1 to 50 characters."
            );
        }

        if (price == null || price.signum() <= 0
                || price.scale() > 2
                || price.compareTo(
                    new BigDecimal("99999999.99")) > 0) {

            throw new ValidationException(
                "Enter a positive price with up to 2 decimal places."
            );
        }

        if (stock < 0) {
            throw new ValidationException(
                "Stock quantity cannot be negative."
            );
        }
    }

    public DefaultTableModel getAll() throws SQLException {
        return dao.getAll();
    }

    public void add(String name, String category,
                    BigDecimal price, int stock)
            throws SQLException, ValidationException {

        validate(name, category, price, stock);
        dao.add(name.trim(), category.trim(), price, stock);
    }

    public void update(int id, String name, String category,
                       BigDecimal price, int stock)
            throws SQLException, ValidationException {

        if (id <= 0) {
            throw new ValidationException(
                "Select a valid product."
            );
        }

        validate(name, category, price, stock);
        dao.update(id, name.trim(), category.trim(), price, stock);
    }

    public void delete(int id)
            throws SQLException, ValidationException {

        if (id <= 0) {
            throw new ValidationException(
                "Select a valid product."
            );
        }

        dao.delete(id);
    }
}