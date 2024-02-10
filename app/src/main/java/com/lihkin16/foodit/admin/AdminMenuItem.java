package com.lihkin16.foodit.admin;

public class AdminMenuItem {


    private String name;
    private String description;
    private String price;
    private int imageResourceId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public AdminMenuItem(String name, String description, String price, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

}
