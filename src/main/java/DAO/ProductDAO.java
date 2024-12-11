package DAO;

import com.ecommerce.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private  Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addProduct(Product product) throws SQLException {
        String query = "INSERT INTO Products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getSeller_id());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("seller_id")
                );
                products.add(product);
            }
        }
        return products;
    }

    public boolean deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM Products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    // updates name, price, quantity of a product only if it belongs to seller.
    public boolean updateProduct(Product product) throws SQLException {
        String query = "UPDATE Products SET name = ?, price = ?, quantity = ? WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getId());
            stmt.setInt(5, product.getSeller_id());
            return stmt.executeUpdate() > 0;
        }
    }

    // fetches products associated with a specific seller.
    public List<Product> getProductsBySeller(int sellerId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sellerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getInt("seller_id")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }
}

