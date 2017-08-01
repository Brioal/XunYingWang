package com.brioal.xunyingwang.bean;

/**
 * Created by Mr.wan on 2017/8/1.
 */

public class DouBanBean {
    private String Rank = "";//排名
    private String Info = "";//简介
    private String Pic = "";//封面图片
    private String ID = "";//ID
    private String Score = "";//评分

    public String getRank() {
        return Rank;
    }

    public DouBanBean setRank(String rank) {
        Rank = rank;
        return this;
    }

    public String getInfo() {
        return Info;
    }

    public DouBanBean setInfo(String info) {
        Info = info;
        return this;
    }

    public String getPic() {
        return Pic;
    }

    public DouBanBean setPic(String pic) {
        Pic = pic;
        return this;
    }

    public String getID() {
        return ID;
    }

    public DouBanBean setID(String ID) {
        this.ID = ID;
        return this;
    }

    public String getScore() {
        return Score;
    }

    public DouBanBean setScore(String score) {
        Score = score;
        return this;
    }
}
