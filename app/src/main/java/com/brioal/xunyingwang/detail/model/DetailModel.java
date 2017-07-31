package com.brioal.xunyingwang.detail.model;

import com.brioal.xunyingwang.bean.DetailBean;
import com.brioal.xunyingwang.bean.DownLoadBean;
import com.brioal.xunyingwang.bean.VideoFunData;
import com.brioal.xunyingwang.detail.contract.DetailContract;
import com.brioal.xunyingwang.interfaces.OnNetDataLoadListener;
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
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/31.
 */

public class DetailModel implements DetailContract.Model {
    private String URL_DETAIL = "http://www.xunyingwang.com/movie/467369.html";

    @Override
    public void loadDetail(final OnNetDataLoadListener loadListener) {
        if (loadListener == null) {
            return;
        }
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL_DETAIL)
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    loadListener.failed(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String content = response.body().string();
                    KLog.d(content);
                    Document document = Jsoup.parse(content);
                    DetailBean detailBean = new DetailBean();
                    detailBean.setYear(getYear(document))
                            .setCoverUrl(getConver(document))
                            .setUpdateTime(getUpdateTime(document))
                            .setVideoFunData(getFunData(document))
                            .setInfo(getInfo(document))
                            .setTypes(getTypes(document))
                            .setDownLoadBean(getPanBean(document))
                            .setOnLineUrl(getOnLineUrl(document))
                    ;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取在线播放的地址
     *
     * @param document
     * @return
     */
    private String getOnLineUrl(Document document) {
        String url = "";
        try {
            url = document.select("online-play-btn").first().attr("href");
            if (url != null && !url.isEmpty()) {
                return url;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * 返回网盘信息
     *
     * @param document
     * @return
     */
    private List<DownLoadBean> getPanBean(Document document) {
        List<DownLoadBean> list = new ArrayList<>();
        try {
            Elements elements = document.select("table").get(1).select("tr");
            for (int i = 0; i < elements.size(); i++) {
                Element tr = elements.get(i);
                String type = tr.select("td").first().select("span").text();
                String url = tr.select("td").get(1).getElementsByTag("a").first().attr("href");
                String pass = tr.select("td").get(1).select("strong").text();
                DownLoadBean bean = new DownLoadBean();
                bean.setType(type)
                        .setUrl(url)
                        .setPass(pass);
                list.add(bean);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回类型
     *
     * @param document
     * @return
     */
    private String[] getTypes(Document document) {
        String[] types = null;
        try {
            Elements elements = document.getElementsByClass("col-xs-12 tags").select("a");
            types = new String[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                String value = elements.get(i).text();
                types[i] = value.substring(1, value.length() - 1);
            }
            return types;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取介绍
     *
     * @param document
     * @return
     */
    private String getInfo(Document document) {
        String info = "";
        try {
            info = document.getElementsByClass("col-xs-12 movie-introduce").first().getElementsByTag("p").text();
            if (info != null && !info.isEmpty()) {
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * 获取表格数据
     *
     * @param document
     * @return
     */
    private VideoFunData getFunData(Document document) {
        try {

            Elements elements = document.select("table").first().select("tbody").select("tr");
            VideoFunData data = new VideoFunData();
            for (int i = 0; i < elements.size(); i++) {
                Element tr = elements.get(i);
                String key = tr.select("td").first().select("span").text();
                String value = tr.select("td").get(1).select("a").text();
                String value1 = tr.select("td").get(1).text();
                switch (key) {
                    case "导演":
                        data.setDirector(value.isEmpty()?value1:value);
                        break;
                    case "编剧":
                        data.setEditor(value.isEmpty()?value1:value);
                        break;
                    case "主演":
                        data.setActors(value.isEmpty()?value1:value);
                        break;
                    case "类型":
                        data.setType(value.isEmpty()?value1:value);
                        break;
                    case "地区":
                        data.setArea(value.isEmpty()?value1:value);
                        break;
                    case "语言":
                        data.setLanguage(value.isEmpty()?value1:value);
                        break;
                    case "上映时间":
                        data.setTime(value.isEmpty()?value1:value);
                        break;
                    case "片长":
                        data.setLength(value.isEmpty()?value1:value);
                        break;
                    case "又名":
                        data.setAnotherName(value.isEmpty()?value1:value);
                        break;
                    case "评分":
                        data.setRank(value.isEmpty()?value1:value);
                        break;
                }
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    private String getUpdateTime(Document document) {
        String time = "";
        try {
            time = document.getElementsByClass("col-xs-4 padding-right-0").first().getElementsByTag("em").first().text();
            if (time != null && !time.isEmpty()) {
                return time;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解析年份
     *
     * @param document
     * @return
     */
    private String getYear(Document document) {
        String year = "";
        try {
            year = document.getElementsByClass("year").first().text();
            if (year != null && !year.isEmpty()) {
                return year;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getConver(Document document) {
        String url = "";
        try {
            url = document.getElementsByClass("img-thumbnail").first().attr("src");
            if (url != null && !url.isEmpty()) {
                return url;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
