package com.brioal.xunyingwang.bean;

import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/31.
 */

public class DetailBean {
    private String mYear = "";//年份
    private String mCoverUrl = "";//封面图
    private String mUpdateTime = "";//更新时间
    private VideoFunData mVideoFunData;//基础数据
    private String mInfo = "";//介绍
    private String[] mTypes = null;//类型
    private List<DownLoadBean> mDownLoadBean = null;//网盘地址
    private String mOnLineUrl = "";//在线播放的地址


    public String getOnLineUrl() {
        return mOnLineUrl;
    }

    public DetailBean setOnLineUrl(String onLineUrl) {
        mOnLineUrl = onLineUrl;
        return this;
    }

    public List<DownLoadBean> getDownLoadBean() {
        return mDownLoadBean;
    }

    public DetailBean setDownLoadBean(List<DownLoadBean> downLoadBean) {
        mDownLoadBean = downLoadBean;
        return this;
    }

    public String[] getTypes() {
        return mTypes;
    }

    public DetailBean setTypes(String[] types) {
        mTypes = types;
        return this;
    }

    public String getInfo() {
        return mInfo;
    }

    public DetailBean setInfo(String info) {
        mInfo = info;
        return this;
    }

    public VideoFunData getVideoFunData() {
        return mVideoFunData;
    }

    public DetailBean setVideoFunData(VideoFunData videoFunData) {
        mVideoFunData = videoFunData;
        return this;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public DetailBean setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
        return this;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public DetailBean setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
        return this;
    }

    public String getYear() {
        return mYear;
    }

    public DetailBean setYear(String year) {
        mYear = year;
        return this;
    }
}
