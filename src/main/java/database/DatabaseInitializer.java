package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    // Method to create tables
    public static void initializeDatabase(Connection connection) {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(255) NOT NULL UNIQUE,
                email VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(50) NOT NULL
            );
        """;

        String createProductsTable = """
            CREATE TABLE IF NOT EXISTS products (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                price DECIMAL(10, 2) NOT NULL,
                quantity INT NOT NULL,
                seller_id INT NOT NULL REFERENCES users(id)
            );
        """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createUsersTable);
            statement.execute(createProductsTable);
            System.out.println("Tables initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database tables: " + e.getMessage());
        }
    }

    // Method to insert default data
    public static void insertDefaultData(Connection connection) {
        String insertDefaultUsers = """
            INSERT INTO users (username, email, password, role)
            VALUES 
            ('admin', 'admin@example.com', 'adminpassword', 'admin'),
            ('seller1', 'seller1@example.com', 'sellerpassword', 'seller'),
            ('buyer1', 'buyer1@example.com', 'buyerpassword', 'buyer')
            ON CONFLICT DO NOTHING;
        """;

        String insertDefaultProducts = """
            INSERT INTO products (name, price, quantity, seller_id)
            VALUES 
            ('Product A', 10.99, 100, 2),
            ('Product B', 19.99, 50, 2),
            ('Product C', 5.99, 200, 2)
            ON CONFLICT DO NOTHING;
        """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(insertDefaultUsers);
            statement.execute(insertDefaultProducts);
            System.out.println("Default data inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting default data: " + e.getMessage());
        }
    }
}
