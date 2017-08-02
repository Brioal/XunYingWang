package com.brioal.xunyingwang.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseActivity;
import com.brioal.xunyingwang.bean.DetailBean;
import com.brioal.xunyingwang.detail.adapter.DownUrlAdapter;
import com.brioal.xunyingwang.detail.contract.DetailContract;
import com.brioal.xunyingwang.detail.presenter.DetailPresenter;
import com.brioal.xunyingwang.view.ScrollRecyclerView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DetailActivity extends BaseActivity implements DetailContract.View {
    @BindView(R.id.detail_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.decor_iv_cover)
    ImageView mIvCover;
    @BindView(R.id.detail_tv_data)
    TextView mTvData;
    @BindView(R.id.detail_tv_info)
    TextView mTvInfo;
    @BindView(R.id.detail_recyclerView)
    ScrollRecyclerView mRecyclerView;
    @BindView(R.id.detail_refreshLayout)
    PtrFrameLayout mRefreshLayout;
    @BindView(R.id.detail_btn_online)
    Button mBtnOnline;
    private DetailContract.Presenter mPresenter;

    private String mID = "";
    private String mType = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail);
        ButterKnife.bind(this);
        initData();
        initPresenter();
        initView();
    }

    private void initData() {
        mID = getIntent().getStringExtra("ID");
        mType = getIntent().getStringExtra("Type");
    }

    private void initView() {
        //返回按钮
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //下拉刷新
        mRefreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refresh();
            }
        });

    }

    private void initPresenter() {
        mPresenter = new DetailPresenter(this);
        mPresenter.start();
    }

    @Override
    public void showRefreshing() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void showData(final DetailBean bean) {
        mRefreshLayout.refreshComplete();
        //封面
        Glide.with(mContext).load(bean.getCoverUrl()).into(mIvCover);
        //基本信息
        mTvData.setText(bean.getData());
        //介绍
        mTvInfo.setText(bean.getInfo());
        //下载地址
        DownUrlAdapter adapter = new DownUrlAdapter(mContext);
        adapter.showList(bean.getDownLoadBean());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
        //在线播放
        if (bean.getOnLineUrl() != null && !bean.getOnLineUrl().isEmpty()) {
            mBtnOnline.setEnabled(true);
            mBtnOnline.setText("点击在线播放");
            mBtnOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String bpath = "http://www.xunyingwang.com/uploads/m3u8/" + mID + ".m3u8";
                    intent.setDataAndType(Uri.parse(bpath), "video/*");
                    startActivity(intent);
                }
            });
        } else {
            mBtnOnline.setEnabled(false);
            mBtnOnline.setText("无在线播放源");
        }
    }


    @Override
    public void showFailed(String errorMsg) {
        mRefreshLayout.refreshComplete();
        showToast(errorMsg);
    }

    @Override
    public String getId() {
        return mID;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void enterDetail(Context context, String id, String type) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("Type", type);
        context.startActivity(intent);
    }
}
