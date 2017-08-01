package com.brioal.xunyingwang.douban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseFragment;
import com.brioal.xunyingwang.bean.DouBanBean;
import com.brioal.xunyingwang.douban.adapter.DouBanAdapter;
import com.brioal.xunyingwang.douban.contract.DouBanContract;
import com.brioal.xunyingwang.douban.presenter.DouBanPresenter;
import com.brioal.xunyingwang.movie.presenter.MoviePresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/8/1.
 */

public class DouBanFragment extends BaseFragment implements DouBanContract.View {
    private static DouBanFragment sFragment;
    private PtrFrameLayout mRefreshLayout;
    private RecyclerView mRankRecyclerView;

    public static synchronized DouBanFragment getInstance() {
        if (sFragment == null) {
            sFragment = new DouBanFragment();
        }
        return sFragment;
    }

    private View mRootView;
    private int mCurrentPage = 1;
    private boolean canLoadMore = true;
    private DouBanContract.Presenter mPresenter;
    private DouBanAdapter douBanAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fra_douban, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initID();
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new DouBanPresenter(this);
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRankRecyclerView.getLayoutManager();
                return linearLayoutManager.findFirstVisibleItemPosition() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mCurrentPage = 1;
                mPresenter.refresh();
            }
        });

        //加载更多
        mRankRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                    //不可以滑动
                    if (canLoadMore) {
                        mCurrentPage++;
                        mPresenter.loadMore();
                        canLoadMore = false;
                    }
                }
            }
        });

    }

    private void initID() {
        mRefreshLayout = mRootView.findViewById(R.id.rank_refreshLayout);
        mRankRecyclerView = mRootView.findViewById(R.id.rank_douban);
    }

    @Override
    public void showRefreshing() {
        mRefreshLayout.autoRefresh(true);
    }

    @Override
    public void showRank(List<DouBanBean> list) {
        mRefreshLayout.refreshComplete();
        douBanAdapter = new DouBanAdapter(mContext);
        douBanAdapter.setList(list);
        mRankRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRankRecyclerView.setAdapter(douBanAdapter);
    }

    @Override
    public void addRank(List<DouBanBean> list) {
        douBanAdapter.addList(list);
        douBanAdapter.notifyDataSetChanged();
        canLoadMore = true;
    }

    @Override
    public void showFailed(String errorMsg) {
        mRefreshLayout.refreshComplete();
        showToast(errorMsg);
    }

    @Override
    public int getPage() {
        return mCurrentPage;
    }
}
