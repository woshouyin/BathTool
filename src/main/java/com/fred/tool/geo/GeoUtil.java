package com.fred.tool.geo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fred.entity.Poi;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GeoUtil {

    public static List<Poi> getAreaPoiList(String region) {
        List<Poi> allList = new ArrayList<>();

        //拉取全部数据
        for (int i = 1; i <100; i++) {
            JSONObject page = GetOnePage(region, String.valueOf(i));
            log.info("page:{}",page);
            if(page.getInteger("count")<25){
                break;
            }
            JSONArray pois = page.getJSONArray("pois");
            List<Poi> poiList = pois.toJavaList(Poi.class);
            allList.addAll(poiList);
        }
        allList.forEach( item -> {
            log.info(JSONObject.toJSONString(item));
        });
        return allList;
    }


    public static JSONObject GetOnePage(String region,String pageNum){
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://restapi.amap.com/v5/place/text").newBuilder();
        urlBuilder.addQueryParameter("key", "139152d28932384c526b125c8118b04a");
        urlBuilder.addQueryParameter("keywords", "卫浴");
        urlBuilder.addQueryParameter("region", region);
        urlBuilder.addQueryParameter("page_size", "25");
        urlBuilder.addQueryParameter("page_num", pageNum);
        HttpUrl url = urlBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            // 打印响应内容
            String resStr = response.body().string();
            JSONObject res = JSON.parseObject(resStr);
            if (res.get("status").toString().equals("1")){
                return res;
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
