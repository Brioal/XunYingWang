package com.brioal.xunyingwang.douban.presenter;

import android.os.Handler;

import com.brioal.xunyingwang.bean.DouBanBean;
import com.brioal.xunyingwang.bean.MovieBean;
import com.brioal.xunyingwang.douban.contract.DouBanContract;
import com.brioal.xunyingwang.douban.model.DouBanModel;
import com.brioal.xunyingwang.movie.contract.MovieContract;
import com.brioal.xunyingwang.movie.model.MovieModel;

import java.util.List;

/**
 * Created by Mr.wan on 2017/8/1.
 */

public class DouBanPresenter implements DouBanContract.Presenter {
    private DouBanContract.View mView;
    private DouBanContract.Model mModel;
    private Handler mHandler = new Handler();

    public DouBanPresenter(DouBanContract.View view) {
        mView = view;
        mModel = new DouBanModel();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showRefreshing();
            }
        });
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadDouBan(mView.getPage(),new DouBanContract.OnDouBanLoadListener(){

            @Override
            public void success(final List<DouBanBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRank(list);
                    }
                });
            }

            @Override
            public void failed(final String errMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showFailed(errMsg);
                    }
                });
            }
        });

    }

    @Override
    public void loadMore() {
        mModel.loadDouBan(mView.getPage(),new DouBanContract.OnDouBanLoadListener() {

            @Override
            public void success(final List<DouBanBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.addRank(list);
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showFailed(errorMsg);
                    }
                });
            }
        });
    }
}
