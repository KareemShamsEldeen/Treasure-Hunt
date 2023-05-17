package com.example.treasurehunt.UModel;

public class Orders
{
    private String TotalAMount,name,phone,email,address,date,time;

    public Orders() {
    }

    public Orders(String totalAMount, String name, String phone, String email, String address, String date, String time) {
        TotalAMount = totalAMount;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    public String getTotalAMount()
    {
        return TotalAMount;
    }

    public void setTotalAMount(String totalAMount) {
        TotalAMount = totalAMount;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
