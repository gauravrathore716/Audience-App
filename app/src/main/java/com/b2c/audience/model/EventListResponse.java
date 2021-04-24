package com.b2c.audience.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EventListResponse {


    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("active")
    private boolean active;

    @SerializedName("data")
    private List<EventListInfo> eventListInfos;

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

    public List<EventListInfo> getEventListInfos() {
        return eventListInfos;
    }

    public void setEventListInfos(List<EventListInfo> eventListInfos) {
        this.eventListInfos = eventListInfos;
    }
}
