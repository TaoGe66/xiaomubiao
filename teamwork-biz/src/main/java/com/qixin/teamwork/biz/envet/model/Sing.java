package com.qixin.teamwork.biz.envet.model;

import java.sql.Timestamp;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/6/14 17:31
 * version:V0.0.1
 */
public class Sing {
    private  String adder;
    private Timestamp stime;

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public Timestamp getStime() {
        return stime;
    }

    public void setStime(Timestamp stime) {
        this.stime = stime;
    }
}
