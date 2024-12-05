package database;


import java.sql.Connection;

public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while connecting to the database");


        }

    }

}



