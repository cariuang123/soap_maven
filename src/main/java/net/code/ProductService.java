package net.code;

import com.model.Product;

import javax.jws.WebService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebService
public class ProductService {

    // Method untuk menambahkan Product baru
    public String addProduct(int userId, String name, double price) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Periksa apakah userId valid
            String userCheckQuery = "SELECT COUNT(*) FROM users WHERE id = ?";
            PreparedStatement userCheckStmt = conn.prepareStatement(userCheckQuery);
            userCheckStmt.setInt(1, userId);
            ResultSet rs = userCheckStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                return "User ID tidak valid.";
            }

            // Tambahkan produk
            String query = "INSERT INTO products (user_id, name, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            return "Produk berhasil ditambahkan!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Gagal menambahkan produk: " + e.getMessage();
        }
    }

    // Method untuk mendapatkan semua Product berdasarkan User ID
    public List<Product> getProductsByUser(int userId) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM products WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // Method untuk memperbarui Product
    public String updateProduct(int productId, String name, double price) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE products SET name = ?, price = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
            return "Produk berhasil diperbarui!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Gagal memperbarui produk: " + e.getMessage();
        }
    }

    // Method untuk menghapus Product
    public String deleteProduct(int productId) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM products WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            stmt.executeUpdate();
            return "Produk berhasil dihapus!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Gagal menghapus produk: " + e.getMessage();
        }
    }

    // Method untuk mencari Product berdasarkan keyword pada nama
    public List<Product> searchProductsByName(String keyword) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
