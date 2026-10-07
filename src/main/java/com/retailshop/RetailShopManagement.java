package com.retailshop;

import com.retailshop.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.retailshop.view.DashboardFrame;
import javax.swing.SwingUtilities;

public class RetailShopManagement {

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            new DashboardFrame().setVisible(true);
        });
        

        String sql = "SELECT product_id, name FROM products";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            System.out.println("Database connected successfully!");

            while (results.next()) {
                System.out.println(
                    results.getInt("product_id") + " - "
                    + results.getString("name")
                );
            }

        } catch (SQLException ex) {
            System.err.println("Database connection or query failed:");
            ex.printStackTrace();
        }
    }
    
    
}