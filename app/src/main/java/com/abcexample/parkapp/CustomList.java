package com.abcexample.parkapp;

import java.util.ArrayList;

/**
 * Created by divya on 17-06-2017.
 */

public class CustomList {
    private String Mallid;
    private String Address;
    private String Name;
    private String url;
    private String TwoWheelersTotal;
    private String TwoWheelersleft;
    private String FourWheelersTotal;
    private String FourWheelersleft;

    public CustomList(){

    }

    public CustomList(String mallid, String address, String name, String url,
                      String twoWheelersTotal, String twoWheelersleft, String fourWheelersTotal, String fourWheelersleft) {
        Mallid = mallid;
        Address = address;
        Name = name;
        this.url = url;
        TwoWheelersTotal = twoWheelersTotal;
        TwoWheelersleft = twoWheelersleft;
        FourWheelersTotal = fourWheelersTotal;
        FourWheelersleft = fourWheelersleft;
    }

    public String getMallid() {
        return Mallid;
    }

    public void setMallid(String mallid) {
        Mallid = mallid;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTwoWheelersTotal() {
        return TwoWheelersTotal;
    }

    public void setTwoWheelersTotal(String twoWheelersTotal) {
        TwoWheelersTotal = twoWheelersTotal;
    }

    public String getTwoWheelersleft() {
        return TwoWheelersleft;
    }

    public void setTwoWheelersleft(String twoWheelersleft) {
        TwoWheelersleft = twoWheelersleft;
    }

    public String getFourWheelersTotal() {
        return FourWheelersTotal;
    }

    public void setFourWheelersTotal(String fourWheelersTotal) {
        FourWheelersTotal = fourWheelersTotal;
    }

    public String getFourWheelersleft() {
        return FourWheelersleft;
    }

    public void setFourWheelersleft(String fourWheelersleft) {
        FourWheelersleft = fourWheelersleft;
    }
}
