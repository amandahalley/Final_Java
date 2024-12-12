package service;

import DAO.UserDAO;
import com.ecommerce.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

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
    public User loginUser(String username, String password) throws SQLException{
        if(username == null || password == null){
            System.out.println("The User Does Not Exist");
        }

        User user = userDAO.getUserByUsername(username);

        if(user == null){
            System.out.println("The User Does Not Exist! ");
            return null;
        }

        if(!BCrypt.checkpw(password, user.getPassword())){
            System.out.println("Wrong Password, Please Try Again!");
            return null;
        }

        System.out.println("User Has Passed Auth");

        return user;
    }


    //get all users
    public List<User> getAllUsers() throws SQLException {
        return UserDAO.getAllUsers();
    }

    //get user by id
    public User getUserByEmail(String email) throws SQLException {
        try {
            User user = userDAO.getUserById(String.valueOf(email));
            if (user == null) {
                System.out.println("No user with email " + email + " was found.");
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
