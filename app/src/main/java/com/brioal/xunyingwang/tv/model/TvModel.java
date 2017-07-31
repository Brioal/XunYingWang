package com.brioal.xunyingwang.tv.model;

import com.brioal.xunyingwang.bean.MovieBean;
import com.brioal.xunyingwang.tv.contract.TvContract;
import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/30.
 */

public class TvModel implements TvContract.Model {
    private String URL_TVS = "http://www.xunyingwang.com/tv";


    @Override

    public void loadTvs(String year, String rank, String area, String type, int pager, final TvContract.OnTvLoadListener loadListener) {
        //解析电视剧列表
        //参照首页Model
        OkHttpClient client = new OkHttpClient();
        String url = String.format(URL_TVS, year.equals("全部")?"":year, rank.equals("全部")?"":rank, area.equals("全部")?"":area, type.equals("全部")?"":type, pager);
        Request request = new Request.Builder()
                .url(url)

  
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
                    //解析电视剧

                    List<MovieBean> list = new ArrayList<>();

                    Elements mdocument = document.getElementsByClass("col-xs-1-5 col-sm-4 col-xs-6 movie-item");

                    for (int i = 0; i < mdocument.size(); i++) {
                        Element moviesDocument = mdocument.get(i);
                        //电视剧名称
                        String title = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").text();

                        //电视剧评分
                        String rankWith = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("em").text();
                        String rank = "";
                        Pattern pattern = Pattern.compile("\\d.\\d");
                        Matcher matcher = pattern.matcher(rankWith);
                        while (matcher.find()) {
                            rank = matcher.group();
                        }

                        //电视剧类型
                        Elements typesWith = moviesDocument.getElementsByClass("meta").first().getElementsByClass("otherinfo").first().getElementsByTag("a");
                        StringBuffer typeBuffer = new StringBuffer();
                        for (int j = 0; j < typesWith.size(); j++) {
                            Element singleTypeElement = typesWith.get(j);
                            String singleType = singleTypeElement.text();
                            typeBuffer.append(singleType);
                            typeBuffer.append("-");
                        }
                        String[] types = typeBuffer.toString().split("-");

                        //电视剧ID
                        String idWith = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").attr("href");
                        String id = "";
                        pattern = Pattern.compile("\\d+");
                        matcher = pattern.matcher(idWith);
                        while (matcher.find()) {
                            id = matcher.group();
                        }
                        //清晰度
                        Elements spans = moviesDocument.getElementsByTag("span");
                        String quality = "";
                        if (spans.size() > 0) {
                            quality = spans.first().text();
                        }
                        //封面地址
                        String coverUrl = moviesDocument.getElementsByTag("img").first().attr("data-original");

                        MovieBean movieBean = new MovieBean();
                        KLog.e(title);
                        KLog.e(coverUrl);
                        KLog.e(id);
                        KLog.e(quality);
                        KLog.e(rank);
                        KLog.e(typeBuffer);
                        movieBean.setName(title)
                                .setCoverUrl(coverUrl)
                                .setID(id)
                                .setQuality(quality)
                                .setRank(rank)
                                .setTypes(types);
                        list.add(movieBean);
                    }
                    loadListener.success(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    loadListener.failed(e.getMessage());
                }
            }
        });
    }


}
