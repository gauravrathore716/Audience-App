package com.b2c.audience.model;

public class ModelEventTicketList {


    private  String tickettype;
    private String ticketprice;

    public ModelEventTicketList(String tickettype, String ticketprice) {
        this.tickettype = tickettype;
        this.ticketprice = ticketprice;
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
}

