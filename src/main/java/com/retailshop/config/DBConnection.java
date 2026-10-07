package com.retailshop.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        String password = System.getenv("RETAIL_DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new SQLException(
                "Set RETAIL_DB_PASSWORD and restart NetBeans."
            );
        }

        return DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/retail_shop",
            "retail_app",
            password
        );
    }
}