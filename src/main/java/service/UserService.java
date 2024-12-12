package service;

import DAO.UserDAO;
import com.ecommerce.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

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

    //get all users
    public List<User> getAllUsers() throws SQLException {
        return UserDAO.getAllUsers();
    }

    //get user by id
    public User getUserById(int user_id) throws SQLException {
        try {
            User user = userDAO.getUserById(String.valueOf(user_id));
            if (user == null) {
                System.out.println("No user with ID " + user_id + " was found.");
            }
            return user;
        } catch (SQLException e) {
            throw new SQLException("Error occured" + e.getMessage(), e);
        }
    }

    //delete user
    public boolean deleteUserByUsername(String username) throws SQLException {

        try {
            return userDAO.deleteUser(username);
        } catch (SQLException e) {
            throw new SQLException("Error occurred while deleting user: " + e.getMessage(), e);
        }
    }

}
