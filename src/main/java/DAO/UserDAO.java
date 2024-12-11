package DAO;

import com.ecommerce.Admin;
import com.ecommerce.Buyer;
import com.ecommerce.Seller;
import com.ecommerce.User;
import com.ecommerce.Product;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

   //Method for registering a user
   public void registerUser(User user) throws SQLException {
       String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";

       //get the database connection
       try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

           //set the parameters
           preparedStatement.setString(1, user.getUsername());  // Assuming User has a getUsername() method
           preparedStatement.setString(2, user.getEmail());     // Assuming User has a getEmail() method
           preparedStatement.setString(3, user.getPassword());  // Assuming User has a getPassword() method
           preparedStatement.setString(4, user.getRole());      // Assuming User has a getRole() method

           //insert the user
           preparedStatement.executeUpdate();
       } catch (SQLException e) {
           //handle any SQLExceptions
           throw new SQLException("Error inserting user into database.", e);
       }
   }


    //Method for finding a user by username for login
    public User getUserByUsername(String username) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String role = resultSet.getString("role");
            if ("buyer".equals(role)) return new Buyer(id, username, email);
            if ("seller".equals(role)) return new Seller(id, username, email);
            if ("admin".equals(role)) return new Admin(id, username, email);

        }
    }
    return null;
    }

    //Get password hash for validation
    public String getPasswordByUsername(String username) throws SQLException {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        }
        return null;
    }


    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery() {

            while (resultSet.next()) {
                User user = new User (
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("role")
                );
                users.add(user);
            }
        }
        return users;
    }

    // Delete a user from system
    public boolean deleteUserById(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            return statement.executeUpdate() > 0;
        }
    }

    // list of all products, including seller name and contact information
    public List<Product> getAllProductsWithSellerInfo() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.price, p.quantity, u.username AS seller_name, u.email AS seller_email " +
                "FROM products p " +
                "JOIN users u ON p.seller_id = u.id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("seller_name"),
                        resultSet.getString("seller_email")
                );
                products.add(product);
            }
        }
        return products;
    }
}









