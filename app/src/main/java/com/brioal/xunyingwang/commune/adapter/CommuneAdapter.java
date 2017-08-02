package com.brioal.xunyingwang.commune.adapter;

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
import com.brioal.xunyingwang.bean.CommuneBean;
import com.brioal.xunyingwang.commune.DialogueActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;

/**
 * Created by Mr.wan on 2017/7/30.
 */

public class CommuneAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<CommuneBean> mList = new ArrayList<>();

    public CommuneAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<CommuneBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public  void addList(List<CommuneBean> list){
        mList.addAll(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommuneViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_commune, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CommuneViewHolder extends BaseViewHolder<CommuneBean> {
        private ImageView mCommuneImg;
        private TextView mCommuneTittle;
        private TextView mCommuneType;
        private TextView mCommuneTime;
        private TextView mCommuneAuthor;
        View mRootView;

        public CommuneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRootView = itemView;
        }

        @Override
        public void bindView(final CommuneBean object, int position) {
            mCommuneAuthor = mRootView.findViewById(R.id.item_commune_tv_author);
            mCommuneTime = mRootView.findViewById(R.id.item_commune_tv_time);
            mCommuneType = mRootView.findViewById(R.id.item_commune_tv_type);
            mCommuneTittle = mRootView.findViewById(R.id.item_commune_tv_tittle);
            mCommuneImg = mRootView.findViewById(R.id.item_commune_iv_img);

            //头像
            Glide.with(mContext).load(object.getTopUrl()).error(R.mipmap.ic_temp_pic).into(mCommuneImg);
            //标题
            mCommuneTittle.setText(object.getTittle());
            //时间
            mCommuneTime.setText(object.getTime());
            //作者
            mCommuneAuthor.setText(object.getAuthor());
            //类型
            mCommuneType.setText(object.getType());
            //点击事件
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enterDialogueDetail(object.getID());
                }
            });
        }
    }
    private void enterDialogueDetail(String id) {
        Intent intent = new Intent(mContext, DialogueActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }
}

