/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author k
 */
public class config {
    
    public static Connection connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:user.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void addRecord(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        pstmt.executeUpdate();
        System.out.println("Record added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding record: " + e.getMessage());
    }
}
    
    public String authenticate(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("type");
            }
        }
    } catch (SQLException e) {
        System.out.println("Login Error: " + e.getMessage());
    }
    return null;
}
    
    
public void displayData(String sql, javax.swing.JTable table) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        table.setModel(DbUtils.resultSetToTableModel(rs));

    } catch (SQLException e) {
        System.out.println("Error displaying data: " + e.getMessage());
    }
}

public void displayData(String sql, javax.swing.JTable table, Object... params) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
    } catch (SQLException e) {
        System.out.println("Error displaying data: " + e.getMessage());
    }
}

    /**
     * Ensures tbl_products exists and seeds default products (Strawberries, Pineapple, Orange, Apple) if the table is empty.
     */
    public static void seedDefaultProducts() {
        try (Connection conn = connectDB();
             Statement st = conn != null ? conn.createStatement() : null) {
            if (st == null) return;
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS tbl_products (" +
                "p_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_name TEXT NOT NULL," +
                "price REAL," +
                "quantity INTEGER," +
                "expiration_date TEXT," +
                "status TEXT)"
            );
            try (ResultSet rs = st.executeQuery("SELECT COUNT(*) AS c FROM tbl_products")) {
                if (rs.next() && rs.getInt("c") == 0) {
                    try (PreparedStatement ins = conn.prepareStatement(
                            "INSERT INTO tbl_products (product_name, price, quantity, expiration_date, status) VALUES (?,?,?,?,?)")) {
                        ins.setString(1, "Strawberries");
                        ins.setDouble(2, 50.00);
                        ins.setInt(3, 100);
                        ins.setString(4, null);
                        ins.setString(5, "available");
                        ins.executeUpdate();
                        ins.setString(1, "Pineapple");
                        ins.setDouble(2, 80.00);
                        ins.setInt(3, 100);
                        ins.setString(4, null);
                        ins.setString(5, "available");
                        ins.executeUpdate();
                        ins.setString(1, "Orange");
                        ins.setDouble(2, 30.00);
                        ins.setInt(3, 100);
                        ins.setString(4, null);
                        ins.setString(5, "available");
                        ins.executeUpdate();
                        ins.setString(1, "Apple");
                        ins.setDouble(2, 40.00);
                        ins.setInt(3, 100);
                        ins.setString(4, null);
                        ins.setString(5, "available");
                        ins.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error seeding products: " + e.getMessage());
        }
    }
}
