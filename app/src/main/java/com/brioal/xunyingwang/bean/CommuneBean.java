package com.brioal.xunyingwang.bean;

/**
 * Created by Mr.wan on 2017/7/31.
 */

public class CommuneBean {
    private String tittle = "";//标题
    private String author = "";//作者
    private String time = "";//时间
    private String type = "";//类型
    private String topUrl = "";//头像
    private String ID=""; //ID

    public String getID() {
        return ID;
    }

    public CommuneBean setID(String ID) {
        this.ID = ID;
        return this;
    }

    public String getTittle() {
        return tittle;
    }

    public CommuneBean setTittle(String tittle) {
        this.tittle = tittle;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommuneBean setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTime() {
        return time;
    }

    public CommuneBean setTime(String time) {
        this.time = time;
        return this;
    }

    public String getType() {
        return type;
    }

    public CommuneBean setType(String type) {
        this.type = type;
        return this;
    }

    public String getTopUrl() {
        return topUrl;
    }

    public CommuneBean setTopUrl(String topUrl) {
        this.topUrl = topUrl;
        return this;
    }


}
