package com.brioal.xunyingwang.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseFragment;
import com.brioal.xunyingwang.bean.MovieBean;
import com.brioal.xunyingwang.home.adapter.VideoAdapter;
import com.brioal.xunyingwang.tv.contract.TvContract;
import com.brioal.xunyingwang.tv.presenter.TvPresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/27.
 */

public class TvFragment extends BaseFragment implements TvContract.View {
    private static TvFragment sFragment;
    private PtrFrameLayout mRefreshLayout;
    private RecyclerView mTvRecyclerView;

    public static synchronized TvFragment getInstance() {
        if (sFragment == null) {
            sFragment = new TvFragment();
        }
        return sFragment;
    }

    private View mRootView;

    private TvContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fra_tv, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initID();
        initView();

        initPresenter();
    }

    private void initView() {
        mRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) mTvRecyclerView.getLayoutManager();
                return gridLayoutManager.findFirstVisibleItemPosition() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refresh();
            }
        });
    }

    private void initID() {
        mRefreshLayout = mRootView.findViewById(R.id.tv_refreshLayout);
        mTvRecyclerView = mRootView.findViewById(R.id.tv_allTvGrid);
    }

    private void initPresenter() {
        mPresenter = new TvPresenter(this);
        mPresenter.start();
    }

    @Override
    public void showRefreshing() {
        mRefreshLayout.autoRefresh(true);

    }

    @Override
    public void showList(List<MovieBean> list) {
        mRefreshLayout.refreshComplete();
        VideoAdapter allTvAdapter = new VideoAdapter(mContext);
        allTvAdapter.setList(list);
        mTvRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mTvRecyclerView.setAdapter(allTvAdapter);
    }

    @Override
    public void showFailed(String errorMsg) {
        mRefreshLayout.refreshComplete();
        showToast(errorMsg);
    }
}
