package com.brioal.xunyingwang.bean;

import com.brioal.bannerview.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/27.
 */

public class HomeBean {
    private List<BannerBean> mBanners = new ArrayList<>();//首页轮播图
    private List<RecommendBean> mRecommendBeans = new ArrayList<>();//推荐资源
    private List<MovieBean> mNewMovies = new ArrayList<>();//最新电影
    private List<MovieBean> mNewTvs = new ArrayList<>();//最新电视剧
    private List<MovieBean> mNewActions = new ArrayList<>();//最新动作电影
    private List<MovieBean> mNewScience = new ArrayList<>();//最新科幻电影

    public List<RecommendBean> getRecommendBeans() {
        return mRecommendBeans;
    }

    public List<MovieBean> getNewMovies() {
        return mNewMovies;
    }

    public HomeBean setNewMovies(List<MovieBean> newMovies) {
        mNewMovies = newMovies;
        return this;
    }

    public List<MovieBean> getNewTvs() {
        return mNewTvs;
    }

    public HomeBean setNewTvs(List<MovieBean> newTvs) {
        mNewTvs = newTvs;
        return this;
    }

    public List<MovieBean> getNewActions() {
        return mNewActions;
    }

    public HomeBean setNewActions(List<MovieBean> newActions) {
        mNewActions = newActions;
        return this;
    }

    public List<MovieBean> getNewScience() {
        return mNewScience;
    }

    public HomeBean setNewScience(List<MovieBean> newScience) {
        mNewScience = newScience;
        return this;
    }

    public List<BannerBean> getBanners() {
        return mBanners;
    }

    public HomeBean setBanners(List<BannerBean> banners) {
        mBanners = banners;
        return this;
    }

    public HomeBean setRecommendBeans(List<RecommendBean> recommendBeans) {
        mRecommendBeans = recommendBeans;
        return this;
    }
}
