package com.qixin.teamwork.biz.envet.model;

import java.util.List;

/** 封装签到记录json
 * Created by
 * Author:xiehuilin
 * Date:2017/6/14 19:24
 * version:V0.0.1
 */
public class JsonSing {
    private List<SingData> data;//签到记录
    private  Integer count;//签到次数

    public List<SingData> getData() {
        return data;
    }

    public void setData(List<SingData> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
