package com.ecommerce;

public class Buyer extends User {
    public Buyer(int id, String username, String password, String email) {
        super(username, password, email, "Buyer");
    }

    public Buyer(int username, String password, String email) {
        super(username, password, email, "buyer");
    }
}
