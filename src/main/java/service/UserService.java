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
    //Register user with encrypted password
    public void registerUser(String username, String email, String password, String role) throws SQLException {
        //hash password before storing it
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userDAO.registerUser(username, email, hashedPassword, role);
    }

    //Login by verifying password
    public User login(String username, String password) throws SQLException {
        //Fetch user from database
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            String storedPassword = userDAO.getPasswordByUsername(username);
            //Verify
            if (BCrypt.checkpw(password, storedPassword)) {
                return user;
            }
        }
        return null;
    }
    }
