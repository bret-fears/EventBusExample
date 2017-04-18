package com.willowtreeapps.eventbusexample.models;

public class ProductDetailEvent {

    final long identifier;
    final String brand;
    final String name;
    final float price;

    public ProductDetailEvent(long identifier, String brand, String name, float price) {
        this.identifier = identifier;
        this.brand = brand;
        this.name = name;
        this.price = price;
    }

    public long getIdentifier() {
        return identifier;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
