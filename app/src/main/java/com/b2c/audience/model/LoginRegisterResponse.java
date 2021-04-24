package com.b2c.audience.model;

import com.google.gson.annotations.SerializedName;

public class LoginRegisterResponse {


    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("active")
    private boolean active;

    @SerializedName("data")
    private ModelUserInfo userInfo;

    {
        active = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ModelUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ModelUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
