package com.brioal.xunyingwang.bean;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/31.
 */

public class DownLoadBean {
    private String mType = "磁力";//地址类型
    private String mUrl = "";//网盘地址
    private String mPass = "";//网盘密码

    public String getType() {
        return mType;
    }

    public DownLoadBean setType(String type) {
        mType = type;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public DownLoadBean setUrl(String url) {
        mUrl = url;
        return this;
    }

    public String getPass() {
        return mPass;
    }

    public DownLoadBean setPass(String pass) {
        mPass = pass;
        return this;
    }
}
