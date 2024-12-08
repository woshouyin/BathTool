package com.fred.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fred.entity.Ad;
import com.fred.entity.Cluster;
import com.fred.entity.Poi;
import com.fred.service.intf.AdService;
import com.fred.service.intf.PoiService;
import com.fred.tool.geo.GeoUtil;
import com.fred.util.PointAggUtil;
import com.fred.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GeoInfoGetController {

    @Resource
    PoiService poiService;

    @Resource
    AdService adService;

    /**
     * 获取数据并写入数据库
     */
    @PostMapping("/writeIntoDb")
    public void writeIntoDb(@RequestParam("region") String region) {
        List<Poi> areaPoiList = GeoUtil.getAreaPoiList(region);
        List<Poi> areaPoiListFiltered = areaPoiList.stream().filter(StreamUtils.distinctByKey(Poi::getId)).collect(Collectors.toList());
        poiService.saveOrUpdateBatch(areaPoiListFiltered);
    }

    /**
     * 1. 从高德获取数据
     * 2.写入数据库
     * 3.做聚合并
     */
    @PostMapping("/writeIntoDbByAdCode")
    public List<Cluster> writeIntoDbByAdCode(@RequestParam("adCode") String adCode,@RequestParam("threshold") Double threshold,@RequestParam("minAmount") Integer minAmount) {
        List<Poi> areaPoiList = GeoUtil.getAreaPoiList(adCode);
        List<Poi> areaPoiListFiltered = areaPoiList.stream().filter(StreamUtils.distinctByKey(Poi::getId)).collect(Collectors.toList());
        poiService.saveOrUpdateBatch(areaPoiListFiltered);
        return poiHandler(areaPoiListFiltered,threshold,minAmount);
    }


    @PostMapping("/pointAgg")
    public List<Cluster> pointAgg(@RequestParam("region") String region,@RequestParam("threshold") Double threshold) {
        List<Poi> poiList = poiService.getPoiByAdName(region);
        return poiHandler(poiList,threshold);
    }

    @PostMapping("/pointAggAdCode")
    public List<Cluster> pointAggAdCode(@RequestParam("adCode") String adCode,@RequestParam("threshold") Double threshold,@RequestParam("minAmount") Integer minAmount) {
        List<Poi> poiList = poiService.getPoiByAdCode(adCode);
        return poiHandler(poiList,threshold,minAmount);
    }

    @PostMapping("/pointAggAdCodeExport")
    public void pointAggAdCodeExport(@RequestParam("adCode") String adCode, @RequestParam("threshold") Double threshold, @RequestParam("minAmount") Integer minAmount, HttpServletResponse response) throws IOException {
        List<Poi> poiList = poiService.getPoiByAdCode(adCode);
        List<Cluster> clusters = poiHandler(poiList, threshold, minAmount);
        shopNameWrite2Name(clusters);
        EasyExcel.write(response.getOutputStream(), Cluster.class)
                .sheet("sheet1")
                .doWrite(clusters);
    }

    @PostMapping("/pointAggAdCodeL3")
    public void pointAggAdCodeL3(@RequestParam("adCode") String L3AdCode,@RequestParam("threshold") Double threshold,@RequestParam("minAmount") Integer minAmount, HttpServletResponse response) throws IOException {
        //通过L3级Ad 获取到对应的所有L4级AD
        List<Ad> l4Ads = adService.getL4AdByL3(L3AdCode);
        //每个L4级别的AD，都查询到对应的cluster
        HashMap<String, List<Cluster>> L4ClustersMap = new HashMap<>();
        for (Ad l4Ad : l4Ads) {
            List<Cluster> clusters = pointAggAdCode(l4Ad.getAdCode(), threshold, minAmount);
            L4ClustersMap.put(l4Ad.getAdName() + l4Ad.getAdCode(),clusters);
        }
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(),Cluster.class).build();
        L4ClustersMap.forEach((k,v) -> {
            shopNameWrite2Name(v);
            shopColorGenerate(v);
            WriteSheet sheet = EasyExcel.writerSheet(k).build();
            excelWriter.write(v, sheet);
        });
        excelWriter.finish();
    }

    @PostMapping("/write2DBAdCodeL3")
    public HashMap<String, List<Cluster>> write2DBAdCodeL3(@RequestParam("adCode") String L3AdCode,@RequestParam("threshold") Double threshold,@RequestParam("minAmount") Integer minAmount, HttpServletResponse response) throws IOException {
        //通过L3级Ad 获取到对应的所有L4级AD
        List<Ad> l4Ads = adService.getL4AdByL3(L3AdCode);
        //每个L4级别的AD，都查询到对应的cluster
        HashMap<String, List<Cluster>> L4ClustersMap = new HashMap<>();
        for (Ad l4Ad : l4Ads) {
            List<Cluster> clusters = writeIntoDbByAdCode(l4Ad.getAdCode(), threshold, minAmount);
            L4ClustersMap.put(l4Ad.getAdName() + l4Ad.getAdCode(),clusters);
        }
        return L4ClustersMap;
    }

    private void shopColorGenerate(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            Integer size = cluster.getSize();
            if      (size>= 0 && size < 5){ cluster.setColor("#9AFFBE=1");}
            else if (size>= 5 && size <10){ cluster.setColor("#56E91C=2");}
            else if (size>=10 && size <15){ cluster.setColor("#7E97FF=3");}
            else if (size>=15 && size <20){ cluster.setColor("#5A78EF=4");}
            else if (size>=20 && size <30){ cluster.setColor("#F6FB3C=5");}
            else if (size>=30 && size <40){ cluster.setColor("#FFB625=6");}
            else                          { cluster.setColor("#FF6666=7");}
        }
    }

    private void shopNameWrite2Name(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            StringBuilder shopNameList = new StringBuilder();
            List<String> shopNames = cluster.getShopName();
            for (String shopName : shopNames) {
                shopNameList.append(shopName).append("\n");
            }
            cluster.setName(shopNameList.toString());
        }
    }

    public List<Cluster> poiHandler(List<Poi> poiList,Double threshold){
        return poiHandler(poiList,threshold,5);
    }
    public List<Cluster> poiHandler(List<Poi> poiList,Double threshold,int minAmount){
        poiList = poiList.stream().filter(
                poi -> {
                    return poi.getName().contains("卫浴")
                            ||poi.getName().contains("洁具")
                            ||poi.getName().contains("九牧")
                            ||poi.getName().contains("恒洁")
                            ||poi.getName().contains("华帝")
                            ||poi.getName().contains("埃美柯")
                            ||poi.getName().contains("浪鲸")
                            ||poi.getName().contains("史密斯")
                            ||poi.getName().contains("汉斯格雅")
                            ||poi.getName().contains("东资")
                            ||poi.getName().contains("四季沐歌")
                            ||poi.getName().contains("美拉奇")
                            ||poi.getName().contains("中宇")
                            ||poi.getName().contains("惠达")
                            ||poi.getName().contains("欧路莎")
                            ||poi.getName().contains("科勒")
                            ||poi.getName().contains("TOTO");
                }
        ).collect(Collectors.toList());

        // 将点位划分到不同集合并计算出其中心点
        List<Cluster> aggregate = PointAggUtil.aggregate(poiList,threshold);

        for (Cluster cluster : aggregate) {
            List<Poi> poiListClus = cluster.getPoiList();
            ArrayList<String> shopName = new ArrayList<>();
            poiListClus.forEach( poi -> {
                shopName.add(poi.getName());
                poi.setMarketCenter(cluster.getCenter());
            });
            cluster.setShopName(shopName);
            poiService.updateBatchById(poiListClus);
        }


        aggregate.forEach(item -> item.setPoiList(null));

        List<Cluster> result = aggregate.stream().filter(item -> item.getSize() >= minAmount).sorted(Comparator.comparing(Cluster::getSize).reversed()).collect(Collectors.toList());
        return result;
    }


}
