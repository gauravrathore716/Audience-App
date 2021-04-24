package com.b2c.audience.model;

public class ModelEventDetail {


    private  String title;
    private String detail;
    private String bill;
    private String wallet;
    private String mail;
    private String status;
    private String extra;

    private  int profile;
    private  int navi;

    public ModelEventDetail(String title, String detail, String bill, String wallet, String mail, String status, String extra, int profile, int navi) {
        this.title = title;
        this.detail = detail;
        this.bill = bill;
        this.wallet = wallet;
        this.mail = mail;
        this.status = status;
        this.extra = extra;
        this.profile = profile;
        this.navi = navi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getWallet() {

        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getNavi() {
        return navi;
    }

    public void setNavi(int navi) {
        this.navi = navi;
    }
}
