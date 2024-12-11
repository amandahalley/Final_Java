package com.ecommerce;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int seller_id;

    //Constructor
    public Product(int id, String name, double price, int quantity, int seller_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.seller_id = seller_id;
    }

    public Product(int id, String name, double price, int quantity, String sellerName, String sellerEmail) {
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSeller_id() {
        return seller_id;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", seller_id=" + seller_id +
                '}';
    }
}
