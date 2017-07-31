package com.brioal.xunyingwang.filter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.brioal.labelview.LabelView;
import com.brioal.labelview.entity.LabelEntity;
import com.brioal.labelview.interfaces.OnLabelSelectedListener;
import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFilterActivity extends BaseActivity {

    @BindView(R.id.filter_year_label)
    LabelView mYearLabel;
    @BindView(R.id.filter_rank_label)
    LabelView mRankLabel;
    @BindView(R.id.filter_area_label)
    LabelView mAreaLabel;
    @BindView(R.id.filter_type_label)
    LabelView mTypeLabel;
    @BindView(R.id.filter_btn_reset)
    TextView mBtnReset;
    @BindView(R.id.filter_btn_done)
    TextView mBtnDone;

    //年份
    private List<String> mYears = new ArrayList<>();
    private List<LabelEntity> mYearsEntity = new ArrayList<>();
    //评分
    private List<LabelEntity> mRanksEntity = new ArrayList<>();
    //地区
    private List<LabelEntity> mAreasEntity = new ArrayList<>();
    //类型
    private List<LabelEntity> mTypesEntity = new ArrayList<>();

    private String mYearSelected = "全部";
    private String mRankSelected = "全部";
    private String mAreaSelected = "全部";
    private String mTypeSelected = "全部";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video_filter);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        //年份
        mYearLabel.setListener(new OnLabelSelectedListener() {
            @Override
            public void selected(int i, String s) {
                mYearSelected = s;
            }
        });
        mYearLabel.setColorBGNormal(Color.parseColor("#E6E6E6"));
        mYearLabel.setColorBGSelect(getResources().getColor(R.color.colorPrimary));
        mYearLabel.setColorTextNormal(Color.parseColor("#888888"));
        mYearLabel.setColorTextSelect(Color.WHITE);
        mYearLabel.setLabels(mYearsEntity);

        //评分
        mRankLabel.setListener(new OnLabelSelectedListener() {
            @Override
            public void selected(int i, String s) {
                mRankSelected = s;
            }
        });
        mRankLabel.setColorBGNormal(Color.parseColor("#E6E6E6"));
        mRankLabel.setColorBGSelect(getResources().getColor(R.color.colorPrimary));
        mRankLabel.setColorTextNormal(Color.parseColor("#888888"));
        mRankLabel.setColorTextSelect(Color.WHITE);
        mRankLabel.setLabels(mRanksEntity);
        //地区
        mAreaLabel.setListener(new OnLabelSelectedListener() {
            @Override
            public void selected(int i, String s) {
                mAreaSelected = s;
            }
        });
        mAreaLabel.setColorBGNormal(Color.parseColor("#E6E6E6"));
        mAreaLabel.setColorBGSelect(getResources().getColor(R.color.colorPrimary));
        mAreaLabel.setColorTextNormal(Color.parseColor("#888888"));
        mAreaLabel.setColorTextSelect(Color.WHITE);
        mAreaLabel.setLabels(mAreasEntity);

        //类型
        mTypeLabel.setListener(new OnLabelSelectedListener() {
            @Override
            public void selected(int i, String s) {
                mTypeSelected = s;
            }
        });
        mTypeLabel.setColorBGNormal(Color.parseColor("#E6E6E6"));
        mTypeLabel.setColorBGSelect(getResources().getColor(R.color.colorPrimary));
        mTypeLabel.setColorTextNormal(Color.parseColor("#888888"));
        mTypeLabel.setColorTextSelect(Color.WHITE);
        mTypeLabel.setLabels(mTypesEntity);

        //重置
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Year", "全部");
                intent.putExtra("Rank", "全部");
                intent.putExtra("Area", "全部");
                intent.putExtra("Type", "全部");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //完成
        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Year", mYearSelected);
                intent.putExtra("Rank", mRankSelected);
                intent.putExtra("Area", mAreaSelected);
                intent.putExtra("Type", mTypeSelected);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void initData() {
        //年份
        mYears.clear();
        mYears.add("全部");
        int currentYear = 2017;
        for (int i = currentYear; i >= 2005; i--) {
            mYears.add(i + "");
        }
        mYears.add("十年前");
        mYearsEntity.clear();
        for (int i = 0; i < mYears.size(); i++) {
            mYearsEntity.add(new LabelEntity(mYears.get(i), mYears.get(i)));
        }
        //评分
        mRanksEntity.clear();
        mRanksEntity.add(new LabelEntity("全部", "全部"));
        mRanksEntity.add(new LabelEntity("9~10分", "10"));
        mRanksEntity.add(new LabelEntity("8~9分", "9"));
        mRanksEntity.add(new LabelEntity("7~8分", "78"));
        mRanksEntity.add(new LabelEntity("6~7分", "7"));
        mRanksEntity.add(new LabelEntity("5~6分", "6"));
        //地区
        mAreasEntity.clear();
        mAreasEntity.add(new LabelEntity("全部", "全部"));
        mAreasEntity.add(new LabelEntity("中国", "中国"));
        mAreasEntity.add(new LabelEntity("美国", "美国"));
        mAreasEntity.add(new LabelEntity("日本", "日本"));
        mAreasEntity.add(new LabelEntity("韩国", "韩国"));
        mAreasEntity.add(new LabelEntity("印度", "印度"));
        mAreasEntity.add(new LabelEntity("法国", "法国"));
        mAreasEntity.add(new LabelEntity("泰国", "泰国"));
        mAreasEntity.add(new LabelEntity("英国", "英国"));
        mAreasEntity.add(new LabelEntity("俄罗斯", "俄罗斯"));
        mAreasEntity.add(new LabelEntity("加拿大", "加拿大"));
        mAreasEntity.add(new LabelEntity("意大利", "意大利"));
        //类型
        mTypesEntity.clear();

        mTypesEntity.add(new LabelEntity("全部", "全部"));
        mTypesEntity.add(new LabelEntity("传记", "传记"));
        mTypesEntity.add(new LabelEntity("喜剧", "喜剧"));
        mTypesEntity.add(new LabelEntity("剧情", "剧情"));
        mTypesEntity.add(new LabelEntity("枪战", "枪战"));
        mTypesEntity.add(new LabelEntity("恐怖", "恐怖"));
        mTypesEntity.add(new LabelEntity("励志", "励志"));
        mTypesEntity.add(new LabelEntity("动画", "动画"));
        mTypesEntity.add(new LabelEntity("歌舞", "歌舞"));
        mTypesEntity.add(new LabelEntity("罪案", "罪案"));
        mTypesEntity.add(new LabelEntity("偶像", "偶像"));
        mTypesEntity.add(new LabelEntity("悬疑", "悬疑"));
        mTypesEntity.add(new LabelEntity("爱情", "爱情"));
        mTypesEntity.add(new LabelEntity("惊悚", "惊悚"));
        mTypesEntity.add(new LabelEntity("综艺", "综艺"));
        mTypesEntity.add(new LabelEntity("纪录", "纪录"));
        mTypesEntity.add(new LabelEntity("青春", "青春"));
        mTypesEntity.add(new LabelEntity("科幻", "科幻"));
        mTypesEntity.add(new LabelEntity("冒险", "冒险"));
        mTypesEntity.add(new LabelEntity("魔幻", "魔幻"));
        mTypesEntity.add(new LabelEntity("丧尸", "丧尸"));
        mTypesEntity.add(new LabelEntity("动作", "动作"));
        mTypesEntity.add(new LabelEntity("幻想", "幻想"));
        mTypesEntity.add(new LabelEntity("战争", "战争"));
        mTypesEntity.add(new LabelEntity("情色", "情色"));
        mTypesEntity.add(new LabelEntity("血腥", "血腥"));
        mTypesEntity.add(new LabelEntity("西部", "西部"));
        mTypesEntity.add(new LabelEntity("童话", "童话"));
        mTypesEntity.add(new LabelEntity("暴力", "暴力"));
        mTypesEntity.add(new LabelEntity("古装", "古装"));
        mTypesEntity.add(new LabelEntity("灾难", "灾难"));
        mTypesEntity.add(new LabelEntity("谍战", "谍战"));
        mTypesEntity.add(new LabelEntity("生活", "生活"));
        mTypesEntity.add(new LabelEntity("讽刺", "讽刺"));
        mTypesEntity.add(new LabelEntity("其他", "其他"));
        mTypesEntity.add(new LabelEntity("同性", "同性"));

        //获取传入数据
        mYearSelected = getIntent().getStringExtra("Year");
        mRankSelected = getIntent().getStringExtra("Rank");
        mAreaSelected = getIntent().getStringExtra("Area");
        mTypeSelected = getIntent().getStringExtra("Type");
    }
}
