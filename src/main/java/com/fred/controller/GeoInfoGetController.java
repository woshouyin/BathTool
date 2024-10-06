package com.fred.controller;

import com.fred.entity.Cluster;
import com.fred.entity.Poi;
import com.fred.service.intf.PoiService;
import com.fred.tool.geo.GeoUtil;
import com.fred.util.PointAggUtil;
import com.fred.util.StreamUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GeoInfoGetController {

    @Resource
    PoiService poiService;

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
