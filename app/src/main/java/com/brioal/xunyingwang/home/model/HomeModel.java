package com.brioal.xunyingwang.home.model;

import com.brioal.bannerview.BannerBean;
import com.brioal.xunyingwang.bean.HomeBean;
import com.brioal.xunyingwang.bean.MovieBean;
import com.brioal.xunyingwang.bean.RecommendBean;
import com.brioal.xunyingwang.home.contract.HomeContract;
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
 * Created by Brioal on 2017/7/27.
 */

public class HomeModel implements HomeContract.Model {
    private String URL_HOMe = "http://www.xunyingwang.com/";

    @Override
    public void loadHomeData(final HomeContract.OnHomeBeanLoadListener loadListener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_HOMe)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                HomeBean homeBean = new HomeBean();
                KLog.d(content);
                try {
                    Document document = Jsoup.parse(content);
                    //解析轮播图
                    homeBean.setBanners(getBanners(document))//解析轮播图
                            .setRecommendBeans(getRecommonds(document))//解析推荐资源
                            .setNewMovies(getNewMovies(document))//解析最新电影
                            .setNewTvs(getTvs(document))//解析电视剧
                            .setNewActions(getNewActions(document))//解析动作电影
                            .setNewScience(getNewScience(document))//解析科幻电影
                    ;
                    loadListener.success(homeBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    loadListener.failed(e.getMessage());
                }

            }
        });
    }

    /**
     * 返回最新科幻片
     *
     * @param mainDocuments
     * @return
     */
    private List<MovieBean> getNewScience(Document mainDocuments) {
        List<MovieBean> list = new ArrayList<>();
        Element document = mainDocuments.getElementsByClass("col-xs-12 index-box").get(2);
        Elements moviesDocuments = document.getElementsByClass("col-xs-1-5 movie-item");
        for (int i = 0; i < moviesDocuments.size(); i++) {
            Element moviesDocument = moviesDocuments.get(i);
            //标题
            String title = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").text();
            //评分初级
            String rankWith = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("em").text();
            String rank = "";
            Pattern pattern = Pattern.compile("\\d.\\d");
            Matcher matcher = pattern.matcher(rankWith);
            //Rank
            while (matcher.find()) {
                rank = matcher.group();
            }
            //类型初级
            Elements typesWith = moviesDocument.getElementsByClass("meta").first().getElementsByClass("otherinfo").first().getElementsByTag("a");
            StringBuffer typeBuffer = new StringBuffer();
            for (int j = 0; j < typesWith.size(); j++) {
                Element singleTypeElement = typesWith.get(j);
                String singleType = singleTypeElement.text();
                typeBuffer.append(singleType);
                typeBuffer.append("-");
            }
            //类型
            String[] types = typeBuffer.toString().split("-");
            //id初级
            String idWith = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").attr("href");
            String id = "";
            pattern = Pattern.compile("\\d+");
            matcher = pattern.matcher(idWith);
            //Id
            while (matcher.find()) {
                id = matcher.group();
            }
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
        return list;
    }

    /**
     * 返回最新动作电影
     *
     * @param mainDocuments
     * @return
     */
    private List<MovieBean> getNewActions(Document mainDocuments) {
        List<MovieBean> list = new ArrayList<>();
        Element document = mainDocuments.getElementsByClass("col-xs-12 index-box").get(1);
        Elements moviesDocuments = document.getElementsByClass("col-xs-1-5 movie-item");
        for (int i = 0; i < moviesDocuments.size(); i++) {
            Element moviesDocument = moviesDocuments.get(i);
            //标题
            String title = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").text();
            //评分初级
            String rankWith = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("em").text();
            String rank = "";
            Pattern pattern = Pattern.compile("\\d.\\d");
            Matcher matcher = pattern.matcher(rankWith);
            //Rank
            while (matcher.find()) {
                rank = matcher.group();
            }
            //类型初级
            Elements typesWith = moviesDocument.getElementsByClass("meta").first().getElementsByClass("otherinfo").first().getElementsByTag("a");
            StringBuffer typeBuffer = new StringBuffer();
            for (int j = 0; j < typesWith.size(); j++) {
                Element singleTypeElement = typesWith.get(j);
                String singleType = singleTypeElement.text();
                typeBuffer.append(singleType);
                typeBuffer.append("-");
            }
            //类型
            String[] types = typeBuffer.toString().split("-");
            //id初级
            String idWith = moviesDocument.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").attr("href");
            String id = "";
            pattern = Pattern.compile("\\d+");
            matcher = pattern.matcher(idWith);
            //Id
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
        return list;
    }

    /**
     * 返回最新电视剧
     *
     * @param mainDocuments
     * @return
     */
    private List<MovieBean> getTvs(Document mainDocuments) {
        List<MovieBean> list = new ArrayList<>();
        Element document = mainDocuments.getElementsByClass("col-xs-12 index-box").first();
        Elements tvDocuments = document.getElementsByClass("col-xs-1-5 movie-item");
        for (int i = 0; i < tvDocuments.size(); i++) {
            Element tvDocument = tvDocuments.get(i);
            //Name
            String name = tvDocument.getElementsByTag("img").first().attr("title");
            //CoverUrl
            String coverUrl = tvDocument.getElementsByTag("img").first().attr("data-original");
            //IdWith
            String idWith = tvDocument.getElementsByTag("h1").first().getElementsByTag("a").first().attr("href");
            //RankWith
            String rankWith = tvDocument.getElementsByTag("h1").first().getElementsByTag("em").first().text();
            //Rank
            String rank = "";
            Pattern pattern = Pattern.compile("\\d.\\d");
            Matcher matcher = pattern.matcher(rankWith);
            while (matcher.find()) {
                rank = matcher.group();
            }
            String id = "";
            pattern = Pattern.compile("\\d+");
            matcher = pattern.matcher(idWith);
            //Id
            while (matcher.find()) {
                id = matcher.group();
            }
            //Actors
            String actors = tvDocument.getElementsByClass("otherinfo").first().text().trim();
            MovieBean bean = new MovieBean();
            bean.setName(name)
                    .setID(id)
                    .setRank(rank)
                    .setmActors(actors)
                    .setCoverUrl(coverUrl);
            list.add(bean);
            KLog.d(id);
            KLog.d(name);
            KLog.d(rank);
            KLog.d(actors);
            KLog.d(coverUrl);
        }
        return list;
    }

    /**
     * 获取最新电影列表
     *
     * @param mainDocuments
     * @return
     */
    private List<MovieBean> getNewMovies(Document mainDocuments) {
        List<MovieBean> list = new ArrayList<>();
        Elements movies = mainDocuments.getElementsByClass("col-xs-3 movie-item");
        for (int i = 0; i < movies.size(); i++) {
            Element movie = movies.get(i);
            //标题
            String title = movie.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").attr("title");
            //评分初级
            String rankWith = movie.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("em").text();
            String rank = "";
            Pattern pattern = Pattern.compile("\\d.\\d");
            Matcher matcher = pattern.matcher(rankWith);
            //Rank
            while (matcher.find()) {
                rank = matcher.group();
            }
            //类型初级
            Elements typesWith = movie.getElementsByClass("meta").first().getElementsByClass("otherinfo").first().getElementsByTag("a");
            StringBuffer typeBuffer = new StringBuffer();
            for (int j = 0; j < typesWith.size(); j++) {
                Element singleTypeElement = typesWith.get(j);
                String singleType = singleTypeElement.text();
                typeBuffer.append(singleType);
                typeBuffer.append("-");
            }
            //类型
            String[] types = typeBuffer.toString().split("-");
            //id初级
            String idWith = movie.getElementsByClass("meta").first().getElementsByTag("h1").first().getElementsByTag("a").attr("href");
            String id = "";
            pattern = Pattern.compile("\\d+");
            matcher = pattern.matcher(idWith);
            //Id
            while (matcher.find()) {
                id = matcher.group();
            }
            //清晰度
            Elements spans = movie.getElementsByTag("span");
            String quality = "";
            if (spans.size() > 0) {
                 quality = spans.first().text();
            }
            //封面地址
            String coverUrl = movie.getElementsByTag("img").first().attr("data-original");
            MovieBean movieBean = new MovieBean();
            KLog.d(title);
            KLog.d(coverUrl);
            KLog.d(id);
            KLog.d(quality);
            KLog.d(rank);
            KLog.d(typeBuffer);
            movieBean.setName(title)
                    .setCoverUrl(coverUrl)
                    .setID(id)
                    .setQuality(quality)
                    .setRank(rank)
                    .setTypes(types);
            list.add(movieBean);
        }

        return list;
    }

    /**
     * 解析推荐资源
     *
     * @param mainDocuments
     * @return
     */
    private List<RecommendBean> getRecommonds(Document mainDocuments) {
        List<RecommendBean> list = new ArrayList<>();
        Elements movies = mainDocuments.getElementsByClass("col-xs-3");
        for (int i = 0; i < 4; i++) {
            Element movie = movies.get(i);
            String title = movie.getElementsByTag("a").attr("title");
            String idWith = movie.getElementsByTag("a").attr("href");
            String imageUrl = movie.getElementsByTag("img").attr("src");
            String id = "";
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(idWith);
            while (matcher.find()) {
                id = matcher.group();
            }
            RecommendBean bean = new RecommendBean();
            KLog.d(id);
            KLog.d(title);
            KLog.d(imageUrl);
            bean.setId(id)
                    .setName(title)
                    .setPicUrl(imageUrl);
            list.add(bean);
        }
        return list;
    }

    /**
     * 解析轮播图
     *
     * @param mainDocument
     * @return
     */
    private List<BannerBean> getBanners(Document mainDocument) {
        Element bannerElements = mainDocument.getElementsByClass("carousel-inner").first();
        Elements bannersNoraml = bannerElements.getElementsByClass("item");
        List<BannerBean> banners = new ArrayList<>();
        for (int i = 0; i < bannersNoraml.size(); i++) {
            Element element = bannersNoraml.get(i);
            String urlWith = element.getElementsByTag("a").first().attr("href");
            String src = element.getElementsByTag("img").first().attr("src");
            String nameWith = element.getElementsByTag("img").first().attr("alt");
            String name = nameWith.substring(0, nameWith.length() - 1 - 3);
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(urlWith);
            String url = "";
            while (matcher.find()) {
                url = matcher.group();
            }
            KLog.d(url);
            KLog.d(src);
            KLog.d(name);
            BannerBean bean = new BannerBean(src, name, url);
            banners.add(bean);
        }
        return banners;
    }
}
