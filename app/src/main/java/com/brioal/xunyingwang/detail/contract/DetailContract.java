package com.brioal.xunyingwang.detail.contract;

import com.brioal.xunyingwang.bean.DetailBean;
import com.brioal.xunyingwang.interfaces.OnNetDataLoadListener;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/31.
 */

public interface DetailContract {
    interface Model {
        void loadDetail(String type, String id, OnNetDataLoadListener<DetailBean> loadListener);
    }

    interface View {
        void showRefreshing();

        void showData(DetailBean bean);

        void showFailed(String errorMsg);

        String getId();

        String getType();

    }

    interface Presenter {
        void start();

        void refresh();
    }
}
