package com.example.treasurehunt.UModel;

public class Cart
{
    String name,img , price ,qun, rqun,date,time,owner;

    public Cart() {
    }

    public Cart(String name, String img, String price, String qun, String rqun,String date,String time,String owner) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.qun = qun;
        this.rqun = rqun;
        this.date = date;
        this.time = time;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQun() {
        return qun;
    }

    public void setQun(String qun) {
        this.qun = qun;
    }

    public String getRqun() {
        return rqun;
    }

    public void setRqun(String rqun) {
        this.rqun = rqun;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
