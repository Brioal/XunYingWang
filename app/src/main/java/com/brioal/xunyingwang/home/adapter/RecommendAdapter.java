package com.brioal.xunyingwang.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseViewHolder;
import com.brioal.xunyingwang.bean.RecommendBean;
import com.brioal.xunyingwang.detail.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/30.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private Context mContext;
    private List<RecommendBean> mList = new ArrayList<>();

    public RecommendAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<RecommendBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class RecommendViewHolder extends BaseViewHolder<RecommendBean> {
        private TextView mTvTitle;
        private ImageView mIvImg;
        private View mRootView;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            mRootView = itemView;
            mTvTitle = mRootView.findViewById(R.id.item_recommend_tv_title);
            mIvImg = mRootView.findViewById(R.id.item_recommend_iv_img);
        }

        @Override
        public void bindView(final RecommendBean object, int position) {
            //显示图片
            Glide.with(mContext).load(object.getPicUrl()).error(R.mipmap.ic_temp_pic).into(mIvImg);
            //显示标题
            mTvTitle.setText(object.getName());
            mTvTitle.setSelected(true);
            //点击事件
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailActivity.enterDetail(mContext, object.getId(),"movie");
                }
            });
        }
    }
}
