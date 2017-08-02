package com.brioal.xunyingwang.commune.model;

import com.brioal.xunyingwang.bean.CommuneBean;
import com.brioal.xunyingwang.commune.contract.CommuneContract;
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

public class CommuneModel implements CommuneContract.Model {

    private String URL_COMMUNE = "http://www.xunyingwang.com/group/?page=%s";

    @Override
    public void loadCommune(int pager,final CommuneContract.OnCommuneLoadListener loadListener) {

        OkHttpClient client = new OkHttpClient();
        String REAL_URL=String.format(URL_COMMUNE,pager);
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
                    //解析交流圈列表界面
                    Document document = Jsoup.parse(content);

                    List<CommuneBean> list = new ArrayList<>();

                    Elements communedocument = document.getElementsByClass("list-group");
                    Elements mdocument = communedocument.first().getElementsByClass("list-group-item");

                    for (int i = 0; i < mdocument.size(); i++) {
                        Element communeDocument = mdocument.get(i);
                        //动态标题
                        String title = communeDocument
                                .getElementsByClass("list-post-title").first().getElementsByTag("a").text();

                        //动态类型
                        String type = communeDocument
                                .getElementsByClass("list-meta-node").first().getElementsByTag("a").text();

                        //动态时间
                        String time = communeDocument
                                .getElementsByClass("list-time").first().text();

                        //动态作者
                        String author = communeDocument
                                .getElementsByClass("list-username").first().getElementsByTag("a").text();

                        //作者头像地址
                        String topUrlWith = communeDocument.getElementsByTag("a").first()
                                .getElementsByClass("avatar-middle").first().attr("src");
                        String topUrlWith1="http://www.xunyingwang.com%s";
                        String top =topUrlWith.substring(0,4);
                        String top1="http";
                        String topUrl="";
                        if(!top.equals(top1)) {
                            topUrl = String.format(topUrlWith1, topUrlWith);
                        }else{
                            topUrl = topUrlWith;
                        }


                        //ID
                        String ID = communeDocument
                                .getElementsByClass("list-post-title").first().getElementsByTag("a").attr("href");

                        CommuneBean communeBean = new CommuneBean();
                        KLog.e(title);
                        KLog.e(type);
                        KLog.e(time);
                        KLog.e(author);
                        KLog.e(topUrl);

                        communeBean.setTittle(title)
                                .setType(type)
                                .setTime(time)
                                .setAuthor(author)
                                .setTopUrl(topUrl)
                                .setID(ID);

                        list.add(communeBean);
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
