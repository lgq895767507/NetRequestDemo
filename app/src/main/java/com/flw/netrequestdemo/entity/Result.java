package com.flw.netrequestdemo.entity;

import java.util.List;

/**
 * Created by lgq on 2016/12/7.
 */

public  class Result{

    private String channel; //频道
    private int num; //	数量
    private List<Contents> list; //内容

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<Contents> getList() {
        return list;
    }

    public void setList(List<Contents> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Result{" +
                "channel='" + channel + '\'' +
                ", num=" + num +
                ", list=" + list +
                '}';
    }
}
