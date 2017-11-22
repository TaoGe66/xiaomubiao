package com.qixin.teamwork.biz.envet.model;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/6/14 19:28
 * version:V0.0.1
 */
public class SingData {
    private  String address;//签到地址
    private  String time;//签到时间
    private  String lng;//经度
    private  String lat;//纬度

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
