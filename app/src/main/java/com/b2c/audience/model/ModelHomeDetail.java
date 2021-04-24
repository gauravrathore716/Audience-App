package com.b2c.audience.model;

public class ModelHomeDetail {

    private  String txtone;
    private String txttwo;
    private String txtthree;
    private  int profile;

    public ModelHomeDetail(String txtone, String txttwo, String txtthree, int profile) {
        this.txtone = txtone;
        this.txttwo = txttwo;
        this.txtthree = txtthree;
        this.profile = profile;

    }

    public String getTxtone() {
        return txtone;
    }

    public void setTxtone(String txtone) {
        this.txtone = txtone;
    }

    public String getTxttwo() {
        return txttwo;
    }

    public void setTxttwo(String txttwo) {
        this.txttwo = txttwo;
    }

    public String getTxtthree() {
        return txtthree;
    }

    public void setTxtthree(String txtthree) {
        this.txtthree = txtthree;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }
}
