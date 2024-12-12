package com.ecommerce;

public class Seller extends User {
    public Seller(int id, String username, String password, String email) {
        super(id, username, email, password,"seller");
    }
}
