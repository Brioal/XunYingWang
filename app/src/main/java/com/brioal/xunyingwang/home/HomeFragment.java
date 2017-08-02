package com.brioal.xunyingwang.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.brioal.bannerview.BannerBean;
import com.brioal.bannerview.BannerView;
import com.brioal.bannerview.LineIndexView;
import com.brioal.bannerview.OnBannerClickListener;
import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseFragment;
import com.brioal.xunyingwang.bean.HomeBean;
import com.brioal.xunyingwang.detail.DetailActivity;
import com.brioal.xunyingwang.home.adapter.VideoAdapter;
import com.brioal.xunyingwang.home.adapter.RecommendAdapter;
import com.brioal.xunyingwang.home.contract.HomeContract;
import com.brioal.xunyingwang.home.presenter.HomePresenter;
import com.brioal.xunyingwang.view.ScrollRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/27.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {
    private static HomeFragment sFragment;
    @BindView(R.id.home_et_search)
    EditText mEtSearch;
    @BindView(R.id.home_banner)
    BannerView mBanner;
    @BindView(R.id.home_recommendRecyclerView)
    ScrollRecyclerView mRecommendRecyclerView;
    @BindView(R.id.home_refreshLayout)
    PtrFrameLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.home_newMovieGrid)
    ScrollRecyclerView mMovieRecyclerView;
    @BindView(R.id.home_newTvGrid)
    ScrollRecyclerView mTvRecyclerView;
    @BindView(R.id.home_newActionGrid)
    ScrollRecyclerView mActionRecyclerView;
    @BindView(R.id.home_newScienceGrid)
    ScrollRecyclerView mScienceRecyclerView;
    @BindView(R.id.home_scrollView)
    ScrollView mScrollView;
    private HomeContract.Presenter mPresenter;

    public static synchronized HomeFragment getInstance() {
        if (sFragment == null) {
            sFragment = new HomeFragment();
        }
        return sFragment;
    }

    private View mRootView;
    private String mKey = "";//搜索内容

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fra_home, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initPresenter();
    }

    private void initView() {
        //下拉刷新

        //处理横向滑动冲突
        mRefreshLayout.disableWhenHorizontalMove(true);
        //设置触发事件
        mRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return mScrollView.getScrollY() == 0 && mRefreshLayout.getOffsetToRefresh() > 50;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refresh();
            }
        });
        //搜索
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != EditorInfo.IME_ACTION_SEARCH) {
                    return false;
                }
                mKey = mEtSearch.getText().toString().trim();
                if (mKey.isEmpty()) {
                    showToast("搜索内容不能为空");
                    return false;
                }
                // TODO: 2017/7/30 跳转搜索
                return false;
            }
        });

    }

    private void initPresenter() {
        mPresenter = new HomePresenter(this);
        mPresenter.start();
    }

    @Override
    public void showRefreshing() {
        mRefreshLayout.autoRefresh(true);
        mRefreshLayout.setOffsetToRefresh(100);
    }

    @Override
    public void showHomeBean(HomeBean homeBean) {
        mRefreshLayout.refreshComplete();
        //显示轮播图
        List<BannerBean> bannerBeans = homeBean.getBanners();
        if (bannerBeans != null && bannerBeans.size() >= 3) {
            mBanner.removeAllViews();
            mBanner.initIndex(new LineIndexView(mContext, 5))
                    .initViewPager(bannerBeans, 2000, new OnBannerClickListener() {
                        @Override
                        public void click(BannerBean bannerBean, int i) {
                            DetailActivity.enterDetail(mContext, bannerBean.getIndex(),"movie");
                        }
                    }).setGallery(true).build(getActivity().getSupportFragmentManager());
        }
        //显示推荐资源
        RecommendAdapter recommendAdapter = new RecommendAdapter(mContext);
        recommendAdapter.setList(homeBean.getRecommendBeans());
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        manager.setAutoMeasureEnabled(true);
        mRecommendRecyclerView.setLayoutManager(manager);
        mRecommendRecyclerView.setHasFixedSize(true);
        mRecommendRecyclerView.setNestedScrollingEnabled(false);
        mRecommendRecyclerView.setAdapter(recommendAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setAutoMeasureEnabled(true);

        //显示最新电影
        VideoAdapter videoAdapter = new VideoAdapter(mContext);
        videoAdapter.setList(homeBean.getNewMovies());
        GridLayoutManager movieGridLayout = new GridLayoutManager(mContext, 3);
        movieGridLayout.setAutoMeasureEnabled(true);
        mMovieRecyclerView.setLayoutManager(movieGridLayout);
        mMovieRecyclerView.setAdapter(videoAdapter);
        mMovieRecyclerView.setHasFixedSize(true);
        mMovieRecyclerView.setNestedScrollingEnabled(false);
        //显示最新电视剧
        VideoAdapter tvAdapter = new VideoAdapter(mContext);
        tvAdapter.setList(homeBean.getNewTvs());
        tvAdapter.setType("tv");
        GridLayoutManager tvGridLayout = new GridLayoutManager(mContext, 3);
        tvGridLayout.setAutoMeasureEnabled(true);
        mTvRecyclerView.setLayoutManager(tvGridLayout);
        mTvRecyclerView.setAdapter(tvAdapter);
        mTvRecyclerView.setHasFixedSize(true);
        mTvRecyclerView.setNestedScrollingEnabled(false);
        //显示最新动作
        VideoAdapter actionAdapter = new VideoAdapter(mContext);
        actionAdapter.setList(homeBean.getNewActions());
        GridLayoutManager actionGridLayout = new GridLayoutManager(mContext, 3);
        actionGridLayout.setAutoMeasureEnabled(true);
        mActionRecyclerView.setLayoutManager(actionGridLayout);
        mActionRecyclerView.setAdapter(actionAdapter);
        mActionRecyclerView.setHasFixedSize(true);
        mActionRecyclerView.setNestedScrollingEnabled(false);
        //显示最新科幻
        VideoAdapter scienceAdapter = new VideoAdapter(mContext);
        scienceAdapter.setList(homeBean.getNewScience());
        GridLayoutManager scienceGridLayout = new GridLayoutManager(mContext, 3);
        scienceGridLayout.setAutoMeasureEnabled(true);
        mScienceRecyclerView.setLayoutManager(scienceGridLayout);
        mScienceRecyclerView.setAdapter(scienceAdapter);
        mScienceRecyclerView.setHasFixedSize(true);
        mScienceRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void showRefreshFailed(String errorMsg) {
        mRefreshLayout.refreshComplete();
        showToast(errorMsg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
