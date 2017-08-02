package com.brioal.xunyingwang.commune;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseFragment;
import com.brioal.xunyingwang.bean.CommuneBean;
import com.brioal.xunyingwang.commune.adapter.CommuneAdapter;
import com.brioal.xunyingwang.commune.contract.CommuneContract;
import com.brioal.xunyingwang.commune.presenter.CommunePresenter;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class CommuneFragment extends BaseFragment implements CommuneContract.View {
    private static CommuneFragment sFragment;
    private PtrFrameLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean canLoadMore = true;
    private int mCurrentPage = 1;
    private CommuneAdapter communeAdapter;

    public static synchronized CommuneFragment getInstance() {
        if (sFragment == null) {
            sFragment = new CommuneFragment();
        }
        return sFragment;
    }

    private View mRootView;
    private CommuneContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fra_commune, container, false);
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
        //下拉刷新
        mRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                return linearLayoutManager.findFirstVisibleItemPosition() == 0;
            }
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refresh();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
        mRefreshLayout = mRootView.findViewById(R.id.commune_refreshLayout);
        mRecyclerView = mRootView.findViewById(R.id.commune_recyclerView);
    }

    private void initPresenter() {
        mPresenter = new CommunePresenter(this);
        mPresenter.start();
    }

    public void showRefreshing() {
        mRefreshLayout.autoRefresh(true);
    }

    @Override
    public void showCommune(List<CommuneBean> list) {
        mRefreshLayout.refreshComplete();
        communeAdapter = new CommuneAdapter(mContext);
        communeAdapter.setList(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(communeAdapter);
    }

    @Override
    public void addCommune(List<CommuneBean> list) {
        communeAdapter.addList(list);
        communeAdapter.notifyDataSetChanged();
        canLoadMore=true;
    }

    public void showFailed(String errorMsg) {
        mRefreshLayout.refreshComplete();
        showToast(errorMsg);
    }

    public int getPage(){
        return mCurrentPage;
    }
}
