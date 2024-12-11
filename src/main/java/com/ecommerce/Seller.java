package com.ecommerce;

import com.ecommerce.User;

public class Seller extends User{
    public Seller(int id, String username, String password, String email) {
        super(String.valueOf(id), username, email, "Seller");
    }

    public Seller(String username, String password, String email) {
        super(username, password, email, "seller");
    }
}
