package com.ecommerce;

public class Admin extends User{
    public Admin(int id, String username, String password, String email) {
        super(username, password, email, "Admin");
    }

    public Admin(String username, String password, String email) {
        super(username, password, email, "admin");
    }
}
