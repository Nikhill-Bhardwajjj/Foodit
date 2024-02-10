package com.lihkin16.util;

public class OrderAPI {

    String username ;
    String foodprice ;
    String foodquantity ;
    String foodname;
    String imageUrl;
    String specification;

    public OrderAPI(){
    }

    public OrderAPI(String username, String foodprice, String foodquantity, String foodname, String imageUrl ,String specification) {
        this.username = username;
        this.foodprice = foodprice;
        this.foodquantity = foodquantity;
        this.foodname = foodname;
        this.imageUrl = imageUrl;
        this.specification =specification;
    }


    public String getUsername() {
        return username;
    }

    public String getSpecification(){return specification;}

    public String getFoodprice() {
        return foodprice;
    }

    public String getFoodquantity() {
        return foodquantity;
    }

    public String getFoodname() {
        return foodname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setSpecification(String specification){this.specification = specification;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFoodprice(String foodprice) {
        this.foodprice = foodprice;
    }

    public void setFoodquantity(String foodquantity) {
        this.foodquantity = foodquantity;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
