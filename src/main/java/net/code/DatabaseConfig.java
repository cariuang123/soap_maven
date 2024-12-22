package net.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    
    // Method untuk mendapatkan koneksi ke database
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:4306/ecommerce"; // Sesuaikan dengan URL MySQL Anda
        String user = "project"; // Username database
        String password = "CariNasi123"; // Password database
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Memastikan driver dimuat
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }
}
