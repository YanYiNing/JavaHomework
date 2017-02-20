package com.example.yanyining.tset;

/**
 * Created by YanYiNing on 2017/2/1.
 * 定义一个Msg类，用int型type来区别收收到Msg还是发送Msg，以此来决定窗口的摆放位置
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
