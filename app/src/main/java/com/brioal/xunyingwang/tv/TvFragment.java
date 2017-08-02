package com.brioal.xunyingwang.tv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseFragment;
import com.brioal.xunyingwang.bean.MovieBean;
import com.brioal.xunyingwang.filter.VideoFilterActivity;
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

    private ImageButton mBtnFilter;
    private int REQUEST_FILTER = 0;


    public static synchronized TvFragment getInstance() {
        if (sFragment == null) {
            sFragment = new TvFragment();
        }
        return sFragment;
    }

    private View mRootView;
    private TvContract.Presenter mPresenter;
    private String mYearSelected = "全部";
    private String mRankSelected = "全部";
    private String mAreaSelected = "全部";
    private String mTypeSelected = "全部";
    private int mCurrentPage = 1;
    private VideoAdapter allTvAdapter;
    private boolean canLoadMore = true;


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
                mCurrentPage = 1;
                mPresenter.refresh();
            }
        });

        mBtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VideoFilterActivity.class);
                intent.putExtra("Year", mYearSelected);
                intent.putExtra("Rank", mRankSelected);
                intent.putExtra("Area", mAreaSelected);
                intent.putExtra("Type", mTypeSelected);
                startActivityForResult(intent, REQUEST_FILTER);
            }
        });
        mTvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
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
        mRefreshLayout = mRootView.findViewById(R.id.tv_refreshLayout);
        mTvRecyclerView = mRootView.findViewById(R.id.tv_allTvGrid);
        mBtnFilter = mRootView.findViewById(R.id.tv_btn_filter);
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
        allTvAdapter = new VideoAdapter(mContext);
        allTvAdapter.setType("tv");
        allTvAdapter.setList(list);
        mTvRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mTvRecyclerView.setAdapter(allTvAdapter);
    }

    @Override
    public void showFailed(String errorMsg) {
        mRefreshLayout.refreshComplete();
        showToast(errorMsg);
    }


    @Override
    public void addTvs(List<MovieBean> list) {
        allTvAdapter.addList(list);
        allTvAdapter.notifyDataSetChanged();
        canLoadMore = true;
    }

    @Override
    public String getYear() {
        return mYearSelected;
    }

    @Override
    public String getRank() {
        return mRankSelected;
    }

    @Override
    public String getArea() {
        return mAreaSelected;
    }

    @Override
    public String getType() {
        return mTypeSelected;
    }

    @Override
    public int getPage() {
        return mCurrentPage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FILTER && resultCode == Activity.RESULT_OK) {
            mYearSelected = data.getStringExtra("Year");
            mRankSelected = data.getStringExtra("Rank");
            mAreaSelected = data.getStringExtra("Area");
            mTypeSelected = data.getStringExtra("Type");
            mPresenter.start();
        }

    }
}
