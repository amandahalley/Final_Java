package service;

import DAO.UserDAO;
import com.ecommerce.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(String username, String email, String password, String role) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userDAO.registerUser(username, email, hashedPassword, role);
    }

    public User login (String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            String storedPassword = userDAO.getPasswordByUsername(username);
            if (BCrypt.checkpw(password, storedPassword)) {
                return user;
            }
        }
        return null;
    }
 }
