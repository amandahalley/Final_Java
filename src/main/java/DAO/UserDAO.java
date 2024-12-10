package DAO;

import com.ecommerce.Admin;
import com.ecommerce.Buyer;
import com.ecommerce.Seller;
import com.ecommerce.User;
import database.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

   //Method for registering a user
    public void registerUser(String username, String email, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, role);
            statement.executeUpdate();
        }
    }


    //Method for finding a user by username for login
    public User getUserByUsername(String username) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = ?";
    try (Connection connection = DatabaseConnection.getConnection();
    PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String role = resultSet.getString("role");
            if ("buyer".equals(role)) return new Buyer(id, username, email);
            if ("seller".equals(role)) return new Buyer(id, username, email);
            if ("admin".equals(role)) return new Buyer(id, username, email);

        }
    }
    return null;
    }

    //Get password hash for validation
    public String getPasswordByUsername(String username) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        }
        return null;
    }
}









