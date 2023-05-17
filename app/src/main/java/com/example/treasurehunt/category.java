package com.example.treasurehunt;

public class category {
     String catname,quantity,img;

     public String getCatname() {
          return catname;
     }

     public void setCatname(String catname) {
          this.catname = catname;
     }

     public String getQuantity() {
          String s = "+ " + quantity + " products";
          return s;
     }

     public void setQuantity(String quantity) {
          this.quantity = quantity;
     }

     public String getImg() {
          return img;
     }

     public void setImg(String img) {
          this.img = img;
     }
}

