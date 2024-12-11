package com.ecommerce;

import com.ecommerce.User;

public class Seller extends User {
    public Seller(int id, String username, String password, String email) {
        super(id, username, email, password,"seller");
    }
}
