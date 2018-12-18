package com.example.pc.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Food {
    private String name;
    private float price;
    private int quantity;

    public Food(String name, float price) {
        this.name = name;
        this.price = price;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if(quantity == 0) return;
        this.quantity--;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Food(JSONObject jsonFood) throws JSONException {
        name = jsonFood.getString("name");
        price = jsonFood.getInt("price");
    }

}
