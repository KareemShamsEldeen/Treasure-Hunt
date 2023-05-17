package com.example.treasurehunt.UModel;

public class Users
{
   private String address,email,password,phone,username,year,month,day,gender;

   public Users ()
   {
       gender="";
   }

    public Users(String address, String email, String password, String phone, String username, String year, String month, String day,String gender)
    {
        this.address = address;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.year = year;
        this.month = month;
        this.day = day;
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

