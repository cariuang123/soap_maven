package net.code;

import com.model.User;

import javax.jws.WebService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebService
public class UserService {

    // Method untuk menambahkan User baru dengan ID yang dapat diatur
    public String registerUser(int id, String name, String email) {
        String responseMessage;
        String query = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);  // Menyisipkan id yang diberikan pengguna
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.executeUpdate();
            responseMessage = "User berhasil didaftarkan dengan ID: " + id;
        } catch (SQLException e) {
            e.printStackTrace();
            responseMessage = "Gagal mendaftarkan user: " + e.getMessage();
        }
        return responseMessage;
    }

    // Method untuk mendapatkan semua User
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, email FROM users";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Method untuk menghapus User berdasarkan ID
    public String deleteUser(int userId) {
        String responseMessage;
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                responseMessage = "User berhasil dihapus!";
            } else {
                responseMessage = "User dengan ID tersebut tidak ditemukan.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            responseMessage = "Gagal menghapus user: " + e.getMessage();
        }
        return responseMessage;
    }

    // Method untuk mencari User berdasarkan ID
    public User getUserById(int userId) {
        User user = null;
        String query = "SELECT id, name, email FROM users WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
