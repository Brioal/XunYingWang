package com.brioal.xunyingwang.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 电影实体类
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/27.
 */

public class MovieBean {
    private String mID = "";//电影ID
    private String mName = "";//电影的Name
    private String mQuality = "超清";//电影画质,默认超清
    private String[] mTypes;//电影类型
    private String mCoverUrl = "";//封面的图片地址
    private String mRank = "";//评分
    private String mActors = "";//演员

    public String getmActors() {
        return mActors;
    }

    public MovieBean setmActors(String mActors) {
        this.mActors = mActors;
        return this;
    }

    public String getRank() {
        return mRank;
    }

    public MovieBean setRank(String rank) {
        mRank = rank;
        return this;
    }

    public String getID() {
        return mID;
    }

    public MovieBean setID(String ID) {
        mID = ID;
        return this;
    }

    public String getName() {
        return mName;
    }

    public MovieBean setName(String name) {
        mName = name;
        return this;
    }

    public String getQuality() {
        return mQuality;
    }

    public MovieBean setQuality(String quality) {
        mQuality = quality;
        return this;
    }

    public String[] getTypes() {
        return mTypes;
    }

    public MovieBean setTypes(String[] types) {
        mTypes = types;
        return this;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public MovieBean setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
        return this;
    }

}
