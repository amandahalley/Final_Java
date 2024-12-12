
package com.ecommerce;

// Import DAO and service classes
import DAO.ProductDAO;
import DAO.UserDAO;

// Import database connection
import database.DatabaseConnection;
import database.DatabaseInitializer;

// Import services
import service.ProductService;
import service.UserService;

// Import utility classes
import java.sql.Connection;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.logging.Logger;


public class EcommerceApp {
    private static final Logger logger = Logger.getLogger(EcommerceApp.class.getName());


    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Initialize DAOs
            UserDAO userDAO = new UserDAO();
            ProductDAO productDAO = new ProductDAO(connection);
            // Initialize database and insert default data
            DatabaseInitializer.initializeDatabase(connection);
            DatabaseInitializer.insertDefaultData(connection);

            // Initialize Services
            UserService userService = new UserService(userDAO);
            ProductService productService = new ProductService(productDAO);

            // Start the application
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            System.out.println("Welcome to the E-Commerce App!");

            while (running) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1: // Register
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        System.out.print("Enter role (buyer/seller/admin): ");
                        String role = scanner.nextLine();

                        // Create a User object based on the role
                        User newUser;
                        switch (role.toLowerCase()) {
                            case "buyer":
                                newUser = new Buyer(username, password, email, role);
                                break;
                            case "seller":
                                newUser = new Seller(username, password, email, role);
                                break;
                            case "admin":
                                newUser = new Admin(username, password, email, role);
                                break;
                            default:
                                System.out.println("Invalid role! Registration failed.");
                                continue; // Skip further processing in this iteration
                        }

                        // Register the user using UserService
                        try {
                            userService.registerUser(newUser);
                            System.out.println("User registered successfully!");
                        } catch (SQLException e) {
                            System.err.println("Error registering user: " + e.getMessage());
                        }
                        break;
                    case 2:
                        // Handle login and provide role-based access
                        User loggedInUser = userService.loginUser();
                        if (loggedInUser != null) {
                            handleRoleBasedAccess(scanner, loggedInUser, productService);
                        }
                        break;
                    case 3:
                        running = false;
                        System.out.println("Thank you for using the E-Commerce App!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());

        }
    }

    private static void handleRoleBasedAccess(Scanner scanner, User user, ProductService productService) {
        System.out.println("\nWelcome, " + user.getUsername() + "!");
        boolean roleMenu = true;

        switch (user.getRole().toLowerCase()) {
            case "buyer":
                while (roleMenu) {
                    System.out.println("\nBuyer Menu:");
                    System.out.println("1. View Products");
                    System.out.println("2. Logout");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            productService.viewAllProducts();
                            break;
                        case 2:
                            roleMenu = false;
                            System.out.println("Logged out.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
                break;

            case "seller":
                while (roleMenu) {
                    System.out.println("\nSeller Menu:");
                    System.out.println("1. Add Product");
                    System.out.println("2. View Products");
                    System.out.println("3. Delete Product");
                    System.out.println("4. Logout");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            productService.addProduct(scanner, user.getId());
                            break;
                        case 2:
                            productService.viewAllProducts();
                            break;
                        case 3:
                            productService.deleteProduct(scanner);
                            break;
                        case 4:
                            roleMenu = false;
                            System.out.println("Logged out.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
                break;

            case "admin":
                while (roleMenu) {
                    System.out.println("\nAdmin Menu:");
                    System.out.println("1. View All Products");
                    System.out.println("2. Delete Product");
                    System.out.println("3. Logout");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            productService.viewAllProducts();
                            break;
                        case 2:
                            productService.deleteProduct(scanner);
                            break;
                        case 3:
                            roleMenu = false;
                            System.out.println("Logged out.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
                break;

            default:
                System.out.println("Invalid role. Logging out.");
        }
    }
}