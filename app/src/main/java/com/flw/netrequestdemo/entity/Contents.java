package com.flw.netrequestdemo.entity;

/**
 * Created by lgq on 2016/12/7.
 */

public class Contents {

    private String title; //标题
    private String time; //时间
    private String src; //来源
    private String category; //分类
    private String pic; //图片
    private String content; //内容
    private String url; //原文手机网址
    private String weburl; //原文PC网址

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Contents{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", src='" + src + '\'' +
                ", category='" + category + '\'' +
                ", pic='" + pic + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", weburl='" + weburl + '\'' +
                '}';
    }

    public Contents() {
    }

    public Contents(String title, String time, String src, String category, String pic, String content, String url, String weburl) {
        this.title = title;
        this.time = time;
        this.src = src;
        this.category = category;
        this.pic = pic;
        this.content = content;
        this.url = url;
        this.weburl = weburl;
    }
}
