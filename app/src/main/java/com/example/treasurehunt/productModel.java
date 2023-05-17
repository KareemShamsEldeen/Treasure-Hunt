package com.example.treasurehunt;

public class productModel {

    private String title,descriptions,price,location,descriptionp,category,name,phone,color,productImage,sername,quantity;


    public productModel() {

    }

    public productModel(String title, String descriptions, String price, String location, String descriptionp, String category, String name, String phone, String productImage,String color,String sername, String quantity) {
        this.title = title;
        this.descriptions = descriptions;
        this.price = price;
        this.location = location;
        this.descriptionp = descriptionp;
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.color = color;
        this.productImage = productImage;
        this.sername = sername;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescriptionp() {
        return descriptionp;
    }

    public void setDescriptionp(String descriptionp) {
        this.descriptionp = descriptionp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSername() {
        return sername;
    }

    public void setSername(String sername) {
        this.sername = sername;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
