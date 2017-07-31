package com.brioal.xunyingwang.tv.presenter;

import android.os.Handler;

import com.brioal.xunyingwang.bean.MovieBean;
import com.brioal.xunyingwang.tv.contract.TvContract;
import com.brioal.xunyingwang.tv.model.TvModel;

import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/30.
 */

public class TvPresenter implements TvContract.Presenter {
    private TvContract.Model mModel;
    private TvContract.View mView;
    private Handler mHandler = new Handler();

    public TvPresenter(TvContract.View view) {
        mView = view;
        mModel = new TvModel();
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
        mModel.loadTvs(new TvContract.OnTvLoadListener() {
            @Override
            public void success(final List<MovieBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showList(list);
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
