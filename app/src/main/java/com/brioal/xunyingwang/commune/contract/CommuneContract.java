package com.brioal.xunyingwang.commune.contract;

import com.brioal.xunyingwang.bean.CommuneBean;
import java.util.List;

public interface CommuneContract {
    interface Model {
        void loadCommune(int pager,CommuneContract.OnCommuneLoadListener loadListener);
    }

    interface View {
        void showRefreshing();//显示正在刷新

        void showCommune(List<CommuneBean> list);//显示动态列表

        void addCommune(List<CommuneBean> list); //添加动态列表

        void showFailed(String errorMsg);//显示刷新失败

        int getPage();//返回当前的页数

    }

    interface Presenter {
        void start();

        void loadMore();

        void refresh();
    }

    interface OnCommuneLoadListener {
        void success(List<CommuneBean> list);

        void failed(String errorMsg);
    }
}

