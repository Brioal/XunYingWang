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
import com.brioal.xunyingwang.bean.MovieBean;
import com.brioal.xunyingwang.detail.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/30.
 */

public class VideoAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<MovieBean> mList = new ArrayList<>();

    public VideoAdapter(Context context) {
        mContext = context;
    }

    private String mType = "movie";

    public void setType(String type) {
        mType = type;
    }

    /**
     * 传递数据源
     *
     * @param list
     */
    public void setList(List<MovieBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    /**
     * 添加内容
     *
     * @param list
     */
    public void addList(List<MovieBean> list) {
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MovieViewHolder extends BaseViewHolder<MovieBean> {
        @BindView(R.id.item_movie_iv_img)
        ImageView mIvImg;
        @BindView(R.id.item_movie_tv_quality)
        TextView mTvQuality;
        @BindView(R.id.item_movie_tv_title)
        TextView mTvTitle;
        @BindView(R.id.item_movie_tv_rank)
        TextView mTvRank;
        @BindView(R.id.item_movie_tv_type1)
        TextView mTvType1;
        @BindView(R.id.item_movie_tv_type2)
        TextView mTvType2;
        @BindView(R.id.item_movie_tv_type3)
        TextView mTvType3;
        View mRootView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRootView = itemView;
        }

        @Override
        public void bindView(final MovieBean object, int position) {
            //图片
            Glide.with(mContext).load(object.getCoverUrl()).error(R.mipmap.ic_temp_pic).into(mIvImg);
            //标题
            mTvTitle.setText(object.getName());
            //清晰度
            if (object.getQuality() == null) {
                mTvQuality.setVisibility(View.GONE);
            } else {
                mTvQuality.setVisibility(View.VISIBLE);
                mTvQuality.setText(object.getQuality());
            }
            //评分
            mTvRank.setText(object.getRank());
            if (object.getmActors() == null || object.getmActors().isEmpty()) {
                //显示类型
                mTvType2.setVisibility(View.VISIBLE);
                mTvType3.setVisibility(View.VISIBLE);
                //类型
                if (object.getTypes().length > 0) {
                    mTvType1.setText(object.getTypes()[0]);
                    mTvType1.setVisibility(View.VISIBLE);
                    mTvType2.setVisibility(View.GONE);
                    mTvType3.setVisibility(View.GONE);
                }
                if (object.getTypes().length > 1) {
                    mTvType1.setText(object.getTypes()[0]);
                    mTvType2.setText(object.getTypes()[1]);
                    mTvType1.setVisibility(View.VISIBLE);
                    mTvType2.setVisibility(View.VISIBLE);
                    mTvType3.setVisibility(View.GONE);
                }
                if (object.getTypes().length > 2) {
                    mTvType1.setText(object.getTypes()[0]);
                    mTvType2.setText(object.getTypes()[1]);
                    mTvType3.setText(object.getTypes()[2]);
                    mTvType1.setVisibility(View.VISIBLE);
                    mTvType2.setVisibility(View.VISIBLE);
                    mTvType3.setVisibility(View.VISIBLE);
                }
            } else {
                //显示主演
                mTvType1.setText(object.getmActors());
                mTvType2.setVisibility(View.GONE);
                mTvType3.setVisibility(View.GONE);
            }

            //点击事件
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailActivity.enterDetail(mContext, object.getID(), mType);
                }
            });
        }
    }
}
