package com.brioal.xunyingwang.detail;

import android.os.Bundle;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseActivity;
import com.brioal.xunyingwang.detail.contract.DetailContract;
import com.brioal.xunyingwang.detail.presenter.DetailPresenter;

public class DetailActivity extends BaseActivity implements DetailContract.View {
    private DetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail);
        initPresenter();

    }

    private void initPresenter() {
        mPresenter = new DetailPresenter(this);
        mPresenter.start();
    }

    @Override
    public void showRefreshing() {

    }

    @Override
    public void showData() {

    }

    @Override
    public void showFailed(String errorMsg) {

    }
}
