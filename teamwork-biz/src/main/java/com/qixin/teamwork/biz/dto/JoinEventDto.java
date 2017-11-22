package com.qixin.teamwork.biz.dto;

import org.framework.utils.BaseModel;

/**
 * Created by
 * Author:xiehuilin
 * Date:2017/6/21 16:18
 * version:V0.0.1
 */
public class JoinEventDto extends BaseModel{
    private Long id;
    private  String userName;//用户姓名
    private  Byte sex;//性别
    private  String birthday;//出生日期
    private  String note;//个人说明
    private  String address;//所在地
    private  String advantage;//个人优势
    private  String tel;
    private Integer frequency;
    private  Byte state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
