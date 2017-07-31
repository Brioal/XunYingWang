package com.brioal.xunyingwang.movie.contract;

import com.brioal.xunyingwang.bean.MovieBean;

import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/30.
 */

public interface MovieContract {
    interface Model {
        void loadMovies(String year, String rank, String area, String type, int pager, OnMoviesLoadListener loadListener);
    }

    interface View {
        void showRefreshing();//显示正在刷新

        void showMovies(List<MovieBean> list);//显示电影列表

        void addMovies(List<MovieBean> list);//添加电影列表

        void showFailed(String errorMsg);//显示刷新失败

        String getYear();//返回选中的年份

        String getRank();//返回选中的评分

        String getArea();//返回选中的地区

        String getType();//返回选中的类型

        int getPage();//返回当前的页数

    }

    interface Presenter {
        void start();

        void refresh();

        void loadMore();
    }

    interface OnMoviesLoadListener {
        void success(List<MovieBean> list);

        void failed(String errorMsg);
    }
}
