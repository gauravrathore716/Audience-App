package com.b2c.audience.model;

public class ModelEventTicketSelectList {


    private  String tickettype;
    private String ticketprice;
    private String nuofticket;

    public ModelEventTicketSelectList(String tickettype, String ticketprice, String nuofticket) {
        this.tickettype = tickettype;
        this.ticketprice = ticketprice;
        this.nuofticket = nuofticket;
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getNuofticket() {
        return nuofticket;
    }

    public void setNuofticket(String nuofticket) {
        this.nuofticket = nuofticket;
    }
}

