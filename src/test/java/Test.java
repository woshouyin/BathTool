import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fred.entity.Poi;
import com.hankcs.hanlp.seg.common.Term;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.hankcs.hanlp.HanLP;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
//        JSONObject jsonObject = GetOnePage("南昌市西湖区", "1");
//        System.out.println(jsonObject);
        HanLPTest();
    }

    public static void jsonHandler(){
        String pageStr = "{\"count\":\"25\",\"infocode\":\"10000\",\"pois\":[{\"parent\":\"\",\"address\":\"二环北路88号5联印象城一楼\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"方太体验店\",\"location\":\"120.570802,30.022274\",\"id\":\"B0IR550HY6\"},{\"parent\":\"B0HDK5D5VO\",\"address\":\"二环北路80号居然之家1层\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;购物相关场所;购物相关场所\",\"typecode\":\"060000\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"科勒(居然之家店)\",\"location\":\"120.570088,30.021311\",\"id\":\"B0FFFPPKOE\"},{\"parent\":\"\",\"address\":\"二环北路58号正大装饰商城北门A1号恒洁卫浴\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"恒洁卫浴(正大装饰商城店)\",\"location\":\"120.565588,30.023156\",\"id\":\"B0FFLI72QN\"},{\"parent\":\"B0FFJKNLN9\",\"address\":\"鑫亿家居装饰城A幢\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"惠达卫浴城市展厅(绍兴鑫亿家居装饰城店)\",\"location\":\"120.610928,30.066105\",\"id\":\"B0H2VDVUUF\"},{\"parent\":\"B0FFGCTCSX\",\"address\":\"正大装饰商城南楼一楼1008号\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;建材五金市场\",\"typecode\":\"060603\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"松下浴霸集成吊顶旗舰店\",\"location\":\"120.566232,30.021871\",\"id\":\"B0HDCAIN1A\"},{\"parent\":\"B0FFHIL9K2\",\"address\":\"88号五联印象城一楼恒洁卫浴\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"恒洁卫浴(5联印象城店)\",\"location\":\"120.571065,30.022238\",\"id\":\"B0FFHVXG0F\"},{\"parent\":\"B0FFHIL9K2\",\"address\":\"正大装饰商城南一楼1041号\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"方太旗舰店(正大南一店)\",\"location\":\"120.565696,30.021368\",\"id\":\"B0FFGBZ341\"},{\"parent\":\"B0FFJKNLN9\",\"address\":\"群贤路9号绍兴鑫亿家居装饰城\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;购物相关场所;购物相关场所\",\"typecode\":\"060000\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"埃美柯卫浴(绍兴鑫亿家居装饰城店)\",\"location\":\"120.610970,30.066440\",\"id\":\"B0ID4DNJ4C\"},{\"parent\":\"B0HDK5D5VO\",\"address\":\"二环北路80号居然之家一楼北门\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"箭牌卫浴(绍兴市居然店)\",\"location\":\"120.570238,30.022352\",\"id\":\"B0IKVCFS6F\"},{\"parent\":\"B0FFGCTCSX\",\"address\":\"二环北路正大装饰城装饰城南2楼\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"帅丰集成灶(正大装饰商城南楼店)\",\"location\":\"120.566231,30.021948\",\"id\":\"B0FFK7ORJC\"},{\"parent\":\"B0FFJ145IJ\",\"address\":\"二环北路明辉国际家居二楼\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"誉博橱柜(明辉国际家居店)\",\"location\":\"120.559716,30.025650\",\"id\":\"B0FFJO51Z9\"},{\"parent\":\"\",\"address\":\"二环北路金德隆北区\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"森歌集成灶(绍兴店)\",\"location\":\"120.567232,30.024028\",\"id\":\"B0IG4D0UOM\"},{\"parent\":\"B023F02KEM\",\"address\":\"二环北路正大装饰商城南一楼1032-1033号\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"A.O.史密斯(绍兴市正大装饰商城朝皇路旗舰店)\",\"location\":\"120.566477,30.021086\",\"id\":\"B0IBFZ0YMV\"},{\"parent\":\"B023F036NV\",\"address\":\"霞西路387号绍兴好贝生活用品广场F1层\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"大师傅厨房设备(绍兴好贝生活用品广场店)\",\"location\":\"120.545142,30.030002\",\"id\":\"B0FFG36I4R\"},{\"parent\":\"\",\"address\":\"明辉国际家居西区建材综合区c26-27\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"赛尔顿卫浴\",\"location\":\"120.559722,30.023804\",\"id\":\"B0FFM9MO40\"},{\"parent\":\"\",\"address\":\"车站北路19号寨下农贸市场北门斜对面\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"樱花(车站北路店)\",\"location\":\"120.574850,30.019849\",\"id\":\"B0H35Z2JNE\"},{\"parent\":\"\",\"address\":\"二环北路46号新大家居广场二楼\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"谷朵卫浴\",\"location\":\"120.560629,30.025333\",\"id\":\"B0G0DU9YF4\"},{\"parent\":\"\",\"address\":\"鑫亿建材城恒洁卫浴\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"恒洁卫浴袍江店\",\"location\":\"120.611762,30.066228\",\"id\":\"B0FFK2SBBD\"},{\"parent\":\"B0FFJKNLN9\",\"address\":\"鑫亿装饰城A区一楼69号(美大集成灶)\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"美大集成灶(鑫亿店)\",\"location\":\"120.611881,30.066214\",\"id\":\"B0H645IM9J\"},{\"parent\":\"B023F01ZY0\",\"address\":\"二环北路58号正大装饰商城东楼2楼2006\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"雅克橱柜(正大装饰商城东楼店)\",\"location\":\"120.566640,30.022717\",\"id\":\"B0FFGBWVV0\"},{\"parent\":\"B023F02U42\",\"address\":\"百盛街36号金桥商务楼F1层\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;家具城\",\"typecode\":\"060602\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"宏发家具卫浴灯饰城(金桥商务楼店)\",\"location\":\"120.608331,30.084559\",\"id\":\"B023F0PKL2\"},{\"parent\":\"B023F02QGR\",\"address\":\"丹桂公寓北门旁\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;建材五金市场\",\"typecode\":\"060603\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"亚育五金电料卫浴商行(丹桂公寓店)\",\"location\":\"120.604365,30.071614\",\"id\":\"B0FFFOPSQT\"},{\"parent\":\"\",\"address\":\"二环北路80号居然之家北门旁独立门头\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;建材五金市场\",\"typecode\":\"060603\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"德国旭勒橱柜\",\"location\":\"120.570162,30.022343\",\"id\":\"B0JGCHOR7E\"},{\"parent\":\"B0HDK5D5VO\",\"address\":\"二环北路80号\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;家具城\",\"typecode\":\"060602\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"大卫国际卫浴(居然之家店)\",\"location\":\"120.569836,30.022426\",\"id\":\"B0HKFH1Q5M\"},{\"parent\":\"B0FFHNE8AN\",\"address\":\"百盛街132号\",\"distance\":\"\",\"pcode\":\"330000\",\"adcode\":\"330602\",\"pname\":\"浙江省\",\"cityname\":\"绍兴市\",\"type\":\"购物服务;家居建材市场;厨卫市场\",\"typecode\":\"060604\",\"adname\":\"越城区\",\"citycode\":\"0575\",\"name\":\"老板(袍江百盛店)\",\"location\":\"120.611398,30.084307\",\"id\":\"B0FFL43RA2\"}],\"status\":\"1\",\"info\":\"OK\"}";
        JSONObject page = JSON.parseObject(pageStr);
        JSONArray pois = page.getJSONArray("pois");
        List<Poi> poiList = pois.toJavaList(Poi.class);
        System.out.println(poiList);
        poiList.forEach( item -> {
            System.out.println(JSONObject.toJSONString(item));
        });
    }

    public static void test2() {
        String region = "绍兴";
        List<Poi> allList = new ArrayList<>();

        //拉取全部数据
        for (int i = 1; i <100; i++) {
            JSONObject page = GetOnePage(region, String.valueOf(i));
            System.out.println(page);
//            assert page != null;
            if(page.getInteger("count")<=0){
                break;
            }
            JSONArray pois = page.getJSONArray("pois");
            List<Poi> poiList = pois.toJavaList(Poi.class);
            allList.addAll(poiList);
        }
        allList.forEach( item -> {
            System.out.println(JSONObject.toJSONString(item));
        });
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

    public static void HanLPTest(){
        String text = "自然语言处理是计算机科学领域与人工智能领域中的一个重要方向。它研究能实现人与计算机之间用自然语言进行有效通信的各种理论和方法。";
        StringBuilder sb = new StringBuilder();
        sb.append("TOTO(丁卯桥路店)");
        sb.append("振华厨房设备");
        sb.append("牧野卫浴(亿都家居建材城店)");
        sb.append("浪鲸卫浴(丁卯桥路店)");
        sb.append("南阳厨具");
        sb.append("樱花(昆山三代专卖店)");
        sb.append("苏州市集美厨具有限公司");
        sb.append("        吉祥厨具");
        sb.append("佳联佳联厨具(汇龙新城店)");
        sb.append("梁丰厨具商店");
        sb.append("森通建陶(白马泾路店)");
        sb.append("万润厨具(泰华苑店)");
        sb.append("永丽橱柜");
        sb.append("华扬太阳能(阳光北苑店)");
        sb.append("樱花卫厨(昆山亿丰专卖店)");
        sb.append("樱花卫厨(中山路专卖店)");
        sb.append("板桥汇瑞鑫厨房设备");
        sb.append("        亿鑫厨具");
        sb.append("苏州广运厨具(常熟店)");
        sb.append("国周厨具烧烤");
        sb.append("        佳达厨业");
        sb.append("江南厨具");
        sb.append("        常熟蓝天厨具");
        sb.append("日利达太阳能(常熟专卖店)");
        sb.append("苏州新东方厨具(琴湖路店)");
        sb.append("日利达太阳能(樾阁北街)");
        sb.append("万家乐(金门路)");
        sb.append("港城橱柜");
        sb.append("金胜橱柜(柏庐路店)");
        sb.append("祥龙厨具");
        sb.append("佳佳橱柜(长寿东路)");
        sb.append("A.O.史密斯太仓县府西街专卖店");
        sb.append("皇明太阳能(东方中路店)");
        sb.append("东浩五金卫浴(江南春堤1期店)");
        sb.append("金牌厨柜(仲英大道店)");
        text = sb.toString();
        // 使用HanLP进行分词
        List<String> words = HanLP.segment(text).stream().map(Term::toString).collect(Collectors.toList());

        // 创建一个HashMap来存储词频
        Map<String, Integer> wordFreq = new HashMap<>();

        // 统计词频
        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }

        // 排序并打印高频词
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFreq.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }





}
