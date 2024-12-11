package service;

import DAO.ProductDAO;
import com.ecommerce.Product;

import java.util.List;
import java.util.Scanner;


public class ProductService {
    private final ProductDAO productDAO;

    // Constructor to initialize ProductDAO
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    // Method to add a product
    public void addProduct(Scanner scanner, int sellerId) {
        try {
            System.out.print("Enter product name: ");
            String name = scanner.nextLine();
            System.out.print("Enter product price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter product quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            Product product = new Product(0, name, price, quantity, sellerId);
            if (productDAO.addProduct(product)) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Failed to add product!");
            }
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());

        }
    }

    // Method to view all products
    public void viewAllProducts() {
        try {
            List<Product> products = productDAO.getProducts();
            if (products.isEmpty()) {
                System.out.println("No products available.");
            } else {
                System.out.println("Available Products:");
                for (Product product : products) {
                    System.out.println(product);
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving products: " + e.getMessage());

        }
    }

    // Method to delete a product by ID
    public void deleteProduct(Scanner scanner) {
        try {
            System.out.print("Enter the product ID to delete: ");
            int productId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (productDAO.deleteProduct(productId)) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Failed to delete product! Product not found.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());

        }
    }
}
