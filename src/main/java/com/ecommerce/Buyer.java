package com.ecommerce;

public class Buyer extends User {
    public Buyer(int id, String username, String password, String email) {
        super(id, username, email, password,"Buyer");
    }
}
