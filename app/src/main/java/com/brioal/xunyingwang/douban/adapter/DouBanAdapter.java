package com.brioal.xunyingwang.douban.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseViewHolder;
import com.brioal.xunyingwang.bean.DouBanBean;
import com.brioal.xunyingwang.detail.DetailActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;

/**
 * Created by Mr.wan on 2017/8/1.
 */

public class DouBanAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<DouBanBean> mList = new ArrayList<>();

    public DouBanAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<DouBanBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void addList(List<DouBanBean> list) {
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DouBanViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_doubanrank, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DouBanViewHolder extends BaseViewHolder<DouBanBean> {

        private ImageView itemIvImg;
        private TextView itemRank;
        private TextView itemTvInfo;
        private TextView itemTvScore;
        View mRootView;

        public DouBanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRootView = itemView;
        }

        @Override
        public void bindView(final DouBanBean object, int position) {
            itemIvImg = mRootView.findViewById(R.id.item_rank_iv_img);
            itemRank = mRootView.findViewById(R.id.item_rank);
            itemTvInfo = mRootView.findViewById(R.id.item_rank_tv_info);
            itemTvScore = mRootView.findViewById(R.id.item_rank_tv_score);

            //图片
            Glide.with(mContext).load(object.getPic()).error(R.mipmap.ic_temp_pic).into(itemIvImg);
            //简介
            itemRank.setText(object.getRank());
            //排名
            itemTvInfo.setText(object.getInfo());
            //评分
            itemTvScore.setText(object.getScore());
            //点击事件
//            mRootView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    enterDetail(object.getID());
//                }
//            });
        }
    }
//    private void enterDetail(String id) {
//        Intent intent = new Intent(mContext, DetailActivity.class);
//        intent.putExtra("id", id);
//        mContext.startActivity(intent);
//    }
}
