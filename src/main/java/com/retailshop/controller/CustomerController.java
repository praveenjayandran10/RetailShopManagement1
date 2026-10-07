package com.retailshop.controller;

import com.retailshop.dao.CustomerDAO;
import com.retailshop.exception.ValidationException;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class CustomerController {

    private final CustomerDAO dao = new CustomerDAO();

    private void validate(String name, String phone, String email)
            throws ValidationException {

        if (name == null || name.isBlank()
                || name.trim().length() > 100) {

            throw new ValidationException(
                "Enter a customer name of 1 to 100 characters."
            );
        }

        if (phone == null || email == null) {
            throw new ValidationException(
                "Phone and email must be text values."
            );
        }

        if (!phone.isBlank()
                && !phone.trim().matches("\\+?[0-9]{7,15}")) {

            throw new ValidationException(
                "Enter 7 to 15 phone digits, optionally starting with +."
            );
        }

        if (!email.isBlank()
                && (email.trim().length() > 100
                || !email.trim().matches(
                    "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))) {

            throw new ValidationException(
                "Enter a valid email address."
            );
        }
    }

    public DefaultTableModel getAll() throws SQLException {
        return dao.getAll();
    }

    public void add(String name, String phone, String email)
            throws SQLException, ValidationException {

        validate(name, phone, email);

        dao.add(
            name.trim(),
            phone.trim(),
            email.trim()
        );
    }

    public void update(int id, String name,
                       String phone, String email)
            throws SQLException, ValidationException {

        if (id <= 0) {
            throw new ValidationException(
                "Select a valid customer."
            );
        }

        validate(name, phone, email);

        dao.update(
            id,
            name.trim(),
            phone.trim(),
            email.trim()
        );
    }

    public void delete(int id)
            throws SQLException, ValidationException {

        if (id <= 0) {
            throw new ValidationException(
                "Select a valid customer."
            );
        }

        // Matches your original database setup.
        if (id == 1) {
            throw new ValidationException(
                "Keep the default Walk-in Customer for billing."
            );
        }

        dao.delete(id);
    }
}