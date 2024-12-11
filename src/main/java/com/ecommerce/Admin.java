package com.ecommerce;

public class Admin extends User {
    public Admin(int id, String username, String password, String email) {
        super(id, username, email, password,"admin");
    }
}