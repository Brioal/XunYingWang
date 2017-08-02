package com.brioal.xunyingwang.douban.model;

import com.brioal.xunyingwang.bean.DouBanBean;
import com.brioal.xunyingwang.douban.contract.DouBanContract;
import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr.wan on 2017/8/1.
 */

public class DouBanModel implements DouBanContract.Model {

    private String URL_DOUBAN = "http://www.xunyingwang.com/movie/top250_douban?page=%s";

    @Override
    public void loadDouBan(int pager, final DouBanContract.OnDouBanLoadListener onDouBanLoadListener) {
        //解析豆瓣排行榜
        OkHttpClient client = new OkHttpClient();
        String REAL_URL = String.format(URL_DOUBAN, pager);

        Request request = new Request.Builder()
                .url(REAL_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                KLog.e(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();

                KLog.d(content);
                try {
                    Document document = Jsoup.parse(content);
                    //解析排行榜

                    List<DouBanBean> list = new ArrayList<>();

                    Elements mdocument = document.getElementsByClass("col-xs-8");

                    for (int i = 0; i < mdocument.size(); i++) {
                        Element rankDocument = mdocument.get(i);

                        //排名
                        String rank = String.valueOf(i + 1);

                        //评分
                        String score = document.getElementsByClass("pull-right").get(i + 4).getElementsByTag("strong").text();

                        //ID
                        String idWith = rankDocument.getElementsByClass("col-xs-9").first().getElementsByTag("a").attr("href");
                        String idTop = "http://www.xunyingwang.com%s";
                        String id = "";
                        id = String.format(idTop, idWith);


                        //封面地址
                        String coverUrl = rankDocument.getElementsByTag("img").first().attr("src");

                        //简介
                        String info = rankDocument.getElementsByTag("p").text();


                        DouBanBean douBanBean = new DouBanBean();
                        KLog.e(rank);
                        KLog.e(coverUrl);
                        KLog.e(id);
                        KLog.e(score);
                        KLog.e(info);

                        douBanBean.setID(id)
                                .setInfo(info)
                                .setPic(coverUrl)
                                .setRank(rank)
                                .setScore(score);

                        list.add(douBanBean);
                    }
                    onDouBanLoadListener.success(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    onDouBanLoadListener.failed(e.getMessage());
                }
            }
        });


    }
}
