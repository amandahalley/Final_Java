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
    public void registerUser(User user) throws SQLException {
       try{
           userDAO.registerUser(user);
           System.out.println("User successfully created");
       } catch (IllegalArgumentException e) {
           System.out.println("Error registering user: " + e.getMessage());
       }
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
