package com.brioal.xunyingwang.detail.model;

import com.brioal.xunyingwang.bean.DetailBean;
import com.brioal.xunyingwang.bean.DownLoadBean;
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
    private String URL_DETAIL = "http://www.xunyingwang.com/%s/%s.html";


    @Override
    public void loadDetail(String type, String id, final OnNetDataLoadListener<DetailBean> loadListener) {
        if (loadListener == null) {
            return;
        }
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(String.format(URL_DETAIL, type, id))
                    .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; letv x501 Build/LMY48Z) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/39.0.0.0 Mobile Safari/537.36")
                    .build();
            KLog.e("DETAIL_URL:"+request.url());
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
                    detailBean
                            .setCoverUrl(getCover(document))
                            .setInfo(getInfo(document))
                            .setOnLineUrl(getOnLineUrl(document))
                            .setData(getData(document))
                            .setDownLoadBean(getPanBean(document))
                    ;
                    loadListener.success(detailBean);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            loadListener.failed(e.getMessage());
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
            url = document.getElementsByClass("online-play-btn").first().attr("href");
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
            Elements elements = document.getElementsByClass("text-break");
            for (int i = 0; i < elements.size(); i++) {
                Element tr = elements.get(i);
                String title = tr.getElementsByTag("a").first().text();
                String url = tr.getElementsByTag("a").first().attr("href");
                DownLoadBean bean = new DownLoadBean();
                bean.setTitle(title)
                        .setUrl(url)
                ;
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
    private String getData(Document document) {
        String types = null;
        try {
            Element elements = document.getElementsByClass("am-u-sm-7").first();
            types = elements.text();
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
            info = document.getElementsByClass("am-u-sm-12").first().text();
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
     * 获取封面下载地址
     *
     * @param document
     * @return
     */
    private String getCover(Document document) {
        String url = "";
        try {
            url = document.getElementsByClass("img-responsive").first().attr("src");
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
