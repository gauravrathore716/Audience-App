package com.b2c.audience.model;

public class ModelCardPayment {


    private  String cardtype;
    private String cardno;
    private String principle;

    private  int ivcard;
    private  int extra;

    public ModelCardPayment(String cardtype, String cardno, String principle, int ivcard, int extra) {
        this.cardtype = cardtype;
        this.cardno = cardno;
        this.principle = principle;
        this.ivcard = ivcard;
        this.extra = extra;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public int getIvcard() {
        return ivcard;
    }

    public void setIvcard(int ivcard) {
        this.ivcard = ivcard;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }
}
