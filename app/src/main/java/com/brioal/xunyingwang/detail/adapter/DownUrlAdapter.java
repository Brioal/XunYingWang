package com.brioal.xunyingwang.detail.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseViewHolder;
import com.brioal.xunyingwang.bean.DownLoadBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/8/1.
 */

public class DownUrlAdapter extends RecyclerView.Adapter<DownUrlAdapter.DownUrlViewHolder> {
    private Context mContext;
    private List<DownLoadBean> mList = new ArrayList<>();

    public DownUrlAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<DownLoadBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public DownUrlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownUrlViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dwonload, parent, false));
    }

    @Override
    public void onBindViewHolder(DownUrlViewHolder holder, int position) {
        holder.bindView(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DownUrlViewHolder extends BaseViewHolder<DownLoadBean> {
        private TextView mTvTitle;

        public DownUrlViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.item_download_tv);
        }

        @Override
        public void bindView(final DownLoadBean object, int position) {
            //显示文字
            mTvTitle.setText(object.getTitle());
            //点击跳转
            mTvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (object.getTitle().contains("百度")) {
                        Pattern pattern = Pattern.compile("[[0-9][a-z]]{4}");
                        Matcher matcher = pattern.matcher(object.getTitle());
                        String pass = "";
                        while (matcher.find()) {
                            pass = matcher.group();
                        }
                        //密码复制道剪贴板
                        ClipboardManager myClipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                        ClipData myClip;
                        myClip = ClipData.newPlainText("text", pass);
                        myClipboard.setPrimaryClip(myClip);
                        Toast.makeText(mContext, "密码:" + pass + "已复制道剪贴板", Toast.LENGTH_LONG).show();
                    }
                    String link = object.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    intent.addCategory("android.intent.category.DEFAULT");
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
