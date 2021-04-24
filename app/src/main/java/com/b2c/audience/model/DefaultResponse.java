package com.b2c.audience.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("active")
    private boolean active;

    {
        active = true;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isActive() {
        return active;
    }


    public String getMessage() {
        return message;
    }



}
