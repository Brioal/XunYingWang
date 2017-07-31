package com.brioal.xunyingwang.detail.contract;

import com.brioal.xunyingwang.interfaces.OnNetDataLoadListener;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/31.
 */

public interface DetailContract {
    interface Model {
        void loadDetail(OnNetDataLoadListener loadListener);
    }

    interface View {
        void showRefreshing();

        void showData();

        void showFailed(String errorMsg);

    }

    interface Presenter {
        void start();

        void refresh();
    }
}
