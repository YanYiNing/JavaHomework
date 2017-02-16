package com.example.yanyining.zhihudaily;

/**
 * Created by YanYiNing on 2017/2/15.
 */

public class News {
    private String title;
    private String imgUrl;

    public News(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
