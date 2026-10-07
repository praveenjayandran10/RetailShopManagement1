package com.retailshop.controller;

import com.retailshop.dao.EmployeeDAO;
import com.retailshop.exception.ValidationException;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class EmployeeController {

    private final EmployeeDAO dao = new EmployeeDAO();

    private void validate(String name, String role)
            throws ValidationException {

        if (name == null || name.isBlank()
                || name.trim().length() > 100) {

            throw new ValidationException(
                "Enter an employee name of 1 to 100 characters."
            );
        }

        if (!("Cashier".equals(role)
                || "Manager".equals(role)
                || "Stock Clerk".equals(role))) {

            throw new ValidationException(
                "Select a valid employee role."
            );
        }
    }

    public DefaultTableModel getAll() throws SQLException {
        return dao.getAll();
    }

    public void add(String name, String role)
            throws SQLException, ValidationException {

        validate(name, role);
        dao.add(name.trim(), role);
    }

    public void update(int id, String name, String role)
            throws SQLException, ValidationException {

        if (id <= 0) {
            throw new ValidationException(
                "Select a valid employee."
            );
        }

        validate(name, role);
        dao.update(id, name.trim(), role);
    }

    public void delete(int id)
            throws SQLException, ValidationException {

        if (id <= 0) {
            throw new ValidationException(
                "Select a valid employee."
            );
        }

        dao.delete(id);
    }
}