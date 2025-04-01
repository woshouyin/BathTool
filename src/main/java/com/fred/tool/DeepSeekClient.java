package com.fred.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeepSeekClient {

    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions"; // 替换为实际地址
    private static final String API_KEY = "sk-79dd767cb20d40aa9ac3e7f2d9623366"; // 替换为你的API密钥

    public static void main(String[] args) {
        try {
            String response = sendRequestToDeepSeek();
            System.out.println("API Response:\n" + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendRequestToDeepSeek() throws IOException {
        // 1. 创建 OkHttp 客户端
        OkHttpClient client = new OkHttpClient();

        // 2. 构建请求体（JSON）
        JSONObject requestBody = new JSONObject();
        //TODO 由方法参数提供
        requestBody.put("model", "deepseek-chat"); // 根据文档调整模型名称

        // 构建 messages 数组
        List<JSONObject> messages = new ArrayList<>();

        // 添加系统指令（可选）List
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "用简体中文回答");
        messages.add(systemMessage);
        JSONObject systemMessage2 = new JSONObject();
        systemMessage2.put("role", "system");
        systemMessage2.put("content", "你是一个市场名称分析专家 回答是只需告诉我市场名称 该市场名称至少在上述文本中出现占比超过20%  " +
                "如有多个市场或市场有多期 如果有多期不需要重复标注市场名称,只需要告知其有的几期 例如豪德贸易广场(一期,二期)" +
                "请提供最合适的一到两个市场名称 " +
                "请尽量避免出现两个名字较为相近的市场名称");
        messages.add(systemMessage2);

        // 添加用户消息
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        //TODO 由店铺和地址组成
        userMessage.put("content", "这是一串店铺的地址 吕蒙乡豪德一期中心街115号 豪德贸易广场二期西街567号店 豪德贸易广场一期四街112号 豪德市场二期一街106-108号 吕蒙乡豪德广场二期一街81号 豪德贸易广场四街19号 豪德二期三街69号 豪德贸易广场二期中心街7号 豪德贸易广场二期一街77号 豪德二期中心街41-45号 景德镇豪德贸易广场二期西2门西南80米 豪德一期二街东 吕蒙乡豪德一期六街 景德镇豪德贸易广场1期东街19号 吕蒙乡景德镇豪德贸易广场2期金域名都 豪德广场一期仓库C区广东雅诚卫浴 四街与西街交叉口东南100米 六街豪德贸易广场-二期 三街与东街交叉口西北80米 金域名都1号 豪德贸易广场一期六街89号 景德西大道金域名都一层43号 四街与西街交叉口东南20米 东街与七街交叉口东北40米 中心街与东二街交叉口东南80米 豪德贸易广场一期中心街177-199号 景德镇豪德贸易广场二期西3门东北190米 官庄村豪德南二街107号 景德镇豪德贸易广场二期西3门西南340米 万象广场西门东140米     以下是这些店铺的名称    TOTO(中心街店)  帝王洁具(西街店)  箭牌卫浴(中心街店)  安华卫浴(景德西大道店)  景岗卫浴(景德镇豪德贸易广场2期店)  汉舍卫浴华盛建材控股(四街店)  英王世家卫浴(景德镇豪德贸易广场2期店)  中博卫浴(景德镇豪德贸易广场2期店)  宏林卫浴批发(景德镇豪德贸易广场2期店)  欧派卫浴(景德镇豪德贸易广场2期店)  中圣卫浴  JOMOO九牧管业  东鹏整装卫浴(景德镇豪德贸易广场1期店)  九牧卫浴景德镇总代理  法恩莎卫浴(豪德店)  广东雅诚卫浴批发  恒通卫浴  欧际卫浴  皇尔卫浴  景德镇科勒卫浴  东鹏瓷砖·卫浴旗舰店  A.O.史密斯金域名都旗舰店  天陶卫浴  洲光卫浴御翔洁具  汉斯格雅(中心街店)  雪雨卫浴(中心街店)  广东大红鹰整体卫浴  爱浪卫浴(景德镇豪德贸易广场1期店)  航邦卫浴(万象广场店)  英皇卫浴运营中心    请归纳总结出其市场名称 ");
        messages.add(userMessage);

        requestBody.put("messages", messages);
//        requestBody.put("max_tokens", 100);

        // 3. 构建请求
        Request request = new Request.Builder()
                .url(API_URL)
//                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(RequestBody.create(
                        MediaType.parse("application/json"),
                        requestBody.toJSONString()
                ))
                .build();

        // 4. 发送请求并解析响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("请求失败: " + response.code() + " " + response.message());
            }
            return JSON.parseObject(response.body().string()).toJSONString();
        }
    }
}