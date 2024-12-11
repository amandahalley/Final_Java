package service;

import DAO.UserDAO;
import com.ecommerce.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Scanner;
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;

    }

    //Register user with encrypted password
    public void registerUser(User user) throws SQLException {
        try {
            // Encrypt the password
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);

            // Delegate to DAO
            userDAO.registerUser(user);
            System.out.println("User successfully created");
        } catch (IllegalArgumentException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error registering user in database: " + e.getMessage());
        }
    }


    public User loginUser(Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Call the `login` method to authenticate the user
            User user = login(username, password);
            if (user != null) {
                System.out.println("Login successful!");
                return user;
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return null;
    }


    //Login by verifying password
    public User login(String username, String password) throws SQLException {
        // Fetch user from database
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            // Verify password using BCrypt
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
