package com.lihkin16.foodit.user;

public class userMenuItem {

    private String name;
    private String description;
    private String price;

    private int  imageResourceId ;

    public userMenuItem(String burger, String delicious_burger, String s , int imageResourceId) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
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
}
