package com.company.polaris_bustrackingsystem;

public class BusModel {
    String busno,busroute,driverid,stops;
    BusModel()
    {

    }
    public BusModel(String busno, String busroute, String driverid, String stops) {
        this.busno = busno;
        this.busroute = busroute;
        this.driverid = driverid;
        this.stops = stops;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getBusroute() {
        return busroute;
    }

    public void setBusroute(String busroute) {
        this.busroute = busroute;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }
}
