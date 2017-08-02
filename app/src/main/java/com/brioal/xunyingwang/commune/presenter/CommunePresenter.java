package com.brioal.xunyingwang.commune.presenter;

import android.os.Handler;

import com.brioal.xunyingwang.bean.CommuneBean;
import com.brioal.xunyingwang.commune.contract.CommuneContract;
import com.brioal.xunyingwang.commune.model.CommuneModel;

import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/30.
 */

public class CommunePresenter implements CommuneContract.Presenter {

    private CommuneContract.View mView;
    private CommuneContract.Model mModel;
    private Handler mHandler = new Handler();

    public CommunePresenter(CommuneContract.View view) {
        mView = view;
        mModel = new CommuneModel();
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
        mModel.loadCommune(mView.getPage(),new CommuneContract.OnCommuneLoadListener(){
            @Override
            public void success(final List<CommuneBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showCommune(list);
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

    public void loadMore() {
        mModel.loadCommune(mView.getPage(),new CommuneContract.OnCommuneLoadListener(){

            @Override
            public void success(final List<CommuneBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.addCommune(list);
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

