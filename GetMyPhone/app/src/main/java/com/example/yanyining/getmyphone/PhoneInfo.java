package com.example.yanyining.getmyphone;

/**
 * Created by YanYiNing on 2016/11/29.
 */

public class PhoneInfo {
    private String name;
    private String number;

    public PhoneInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
