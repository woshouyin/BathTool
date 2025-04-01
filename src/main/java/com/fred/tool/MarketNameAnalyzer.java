package com.fred.tool;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketNameAnalyzer {

    public static void main(String[] args) {
        String name = "ARROW箭牌卫浴(红星美凯龙店)";
        String bracketContent = name.replaceAll(".*\\(([^)]*?)店\\)", "$1");
        System.out.println(bracketContent);
        // 原始数据
        List<String> addresses = Arrays.asList(
                "贸易广场北六街8号", "游园金梦东北门东南80米", "北一街西南30米",
                "贸易广场中心街28-30号", "文昌大道与文孝路交叉口西180米",
                "抚州贸易广场北四街13号", "东街抚州贸易广场", "东街游园金梦",
                "南六街抚州贸易广场", "ARROW箭牌卫浴(红星美凯龙店)",
                "东街抚州贸易广场北五街8号", "迎宾大道76号",
                "贸易广场东路与南门路交叉口东南240米", "文昌大道抚州贸易广场",
                "贸易广场西街张家新村8栋一楼", "贸易广场东路与南六街交叉口北40米",
                "文昌大道22-24号"
        );

        List<String> shopNames = Arrays.asList(
                "双峰卫浴批发部九牧卫浴美的厨热电器", "彩图卫浴", "奥聪卫浴批发",
                "乐谷卫浴", "河成卫浴批发", "九牧卫浴", "梵兰卡整体卫浴",
                "闻洲卫浴(文昌大道店)", "四季沐歌卫浴全浴室空间定制",
                "箭牌卫浴(抚州市红星店)", "世丰管·卫浴抚州总代理",
                "潜水艇卫浴(东方首府店)", "东鹏整装卫浴", "榕呐卫浴",
                "潜水艇卫浴(抚州贸易广场店)", "恒洁卫浴(抚州贸易广场店)",
                "双华卫浴批发配送"
        );

        // 多维度分析
        String result = analyzeRepresentative(addresses, shopNames);
        System.out.println("统一代表词: " + result);
    }

    public static String analyzeRepresentative(List<String> addresses, List<String> shopNames) {
        // 维度1：地址模式挖掘
        Map<String, Integer> addressPatterns = extractAddressPatterns(addresses);

        // 维度2：店铺名称分析
        Map<String, Integer> shopKeywords = extractShopKeywords(shopNames);

        // 维度3：交叉验证
        return getBestRepresentative(addressPatterns, shopKeywords);
    }

    // 地址模式提取（使用HanLP）
    private static Map<String, Integer> extractAddressPatterns(List<String> addresses) {
        Map<String, Integer> patterns = new HashMap<>();

        addresses.forEach(address -> {
            // 使用HanLP进行地址成分分析
            List<Term> terms = HanLP.segment(address);
            terms.stream()
                    .filter(term -> term.nature.toString().startsWith("ns")) // 筛选地名
                    .map(term -> term.word.replaceAll("市|区|县|街|大道|路", ""))
                    .filter(word -> word.length() > 1)
                    .forEach(word -> patterns.put(word, patterns.getOrDefault(word, 0) + 1));
        });

        return patterns;
    }

    // 店铺关键词提取
    private static Map<String, Integer> extractShopKeywords(List<String> shopNames) {
        Map<String, Integer> keywords = new HashMap<>();

        shopNames.forEach(name -> {
            // 提取括号内容
            String bracketContent = name.replaceAll(".*\\(([^)]*?)店\\)", "$1");
            if (!bracketContent.equals(name)) {
                Arrays.stream(bracketContent.split("店"))
                        .forEach(part -> keywords.put(part, keywords.getOrDefault(part, 0) + 1));
            }

            // 整体分词分析
            List<Term> terms = HanLP.segment(name);
            terms.stream()
                    .filter(term -> term.nature.toString().startsWith("n"))
                    .map(term -> term.word)
                    .filter(word -> word.length() > 1)
                    .forEach(word -> keywords.put(word, keywords.getOrDefault(word, 0) + 1));
        });
        System.out.println(keywords);
        return keywords;
    }

    // 综合决策
    private static String getBestRepresentative(Map<String, Integer> addressPatterns,
                                                Map<String, Integer> shopKeywords) {
        // 合并权重
        Map<String, Integer> combined = new HashMap<>();
        addressPatterns.forEach((k, v) -> combined.put(k, combined.getOrDefault(k, 0) + v * 2));
        shopKeywords.forEach((k, v) -> combined.put(k, combined.getOrDefault(k, 0) + v));

        // 排除通用词
        List<String> stopWords = Arrays.asList("卫浴", "批发", "店");
        stopWords.forEach(combined::remove);

        // 获取最佳候选
        return combined.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("无统一标识");
    }

}
