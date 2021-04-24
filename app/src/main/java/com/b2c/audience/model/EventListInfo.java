package com.b2c.audience.model;

import com.google.gson.annotations.SerializedName;

public class EventListInfo {


    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("event_date")
    private String event_date;

    @SerializedName("event_time")
    private String event_time;

    @SerializedName("WTP")
    private String WTP;

    @SerializedName("postcode")
    private int postcode;

    @SerializedName("city")
    private String city;

    @SerializedName("address")
    private String address;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("total_nice")
    private String total_nice;

    @SerializedName("event_pic")
    private String event_pic;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getWTP() {
        return WTP;
    }

    public void setWTP(String WTP) {
        this.WTP = WTP;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTotal_nice() {
        return total_nice;
    }

    public void setTotal_nice(String total_nice) {
        this.total_nice = total_nice;
    }

    public String getEvent_pic() {
        return event_pic;
    }

    public void setEvent_pic(String event_pic) {
        this.event_pic = event_pic;
    }
}
