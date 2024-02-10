package com.lihkin16.util;

public class FoodAPI  {

    private String itemName;
    private String itemDespription;
    private String itemPrice;
    private String imageUri;
    private String foodId ;
    public FoodAPI() {

    }



    public FoodAPI(String itemName, String itemDespription, String itemPrice, String imageUri , String foodId) {
        this.itemName = itemName;
        this.itemDespription = itemDespription;
        this.itemPrice = itemPrice;
        this.imageUri = imageUri;
        this.foodId = foodId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDespription() {
        return itemDespription;
    }

    public String getFoodId()
    {
        return  foodId;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDespription(String itemDespription) {
        this.itemDespription = itemDespription;
    }

    public void setFoodId(String foodId)
    {
        this.foodId = foodId;
    }
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


}
