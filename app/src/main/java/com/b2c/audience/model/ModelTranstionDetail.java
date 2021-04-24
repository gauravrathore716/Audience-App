package com.b2c.audience.model;

public class ModelTranstionDetail {

    private  String price;
    private String date;
    private String status;

    public ModelTranstionDetail(String price, String date, String status) {

        this.price = price;
        this.date = date;
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
