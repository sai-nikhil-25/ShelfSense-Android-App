package com.kunduris1.shelfsenseapp;

import java.io.Serializable;

/**
 * Model class representing a food item with name, quantity, and expiry date.
 */
public class FoodItem implements Serializable {
    String name;
    String quantity;
    long expiryDate; // stored as milliseconds

    public FoodItem(String name, String quantity, long expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }
}
