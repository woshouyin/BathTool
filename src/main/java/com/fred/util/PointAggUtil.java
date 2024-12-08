package com.fred.util;

import com.fred.entity.Cluster;
import com.fred.entity.Poi;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class PointAggUtil {

    // 聚合阈值（例如：10米）
    private static final double THRESHOLD = 1000;

    public static List<Cluster> aggregate(List<Poi> points,double threshold) {
        List<Cluster> aggregate = new ArrayList<>();

        while (!points.isEmpty()) {
            // 取出第一个点作为当前聚类的中心
            Poi centroid = points.remove(0);
            String centerLatLon = centroid.getLocation();
            List<Poi> poisInCluster = new ArrayList<>();
            poisInCluster.add(centroid);
            String centerLon;
            String centerLat;
            // 遍历剩余的点，检查它们是否在当前聚类的阈值内
            for (int i = 0; i < points.size(); ) {
                Poi currentPoint = points.get(i);
                if (DistanceCalUtils.calculateDistance(currentPoint.getLocation(),centerLatLon) <= threshold) {
                    // 如果在阈值内，则添加到当前聚类中，并从原始列表中移除
                    poisInCluster.add(currentPoint);
                    points.remove(i);
                    double sumLat = 0, sumLon = 0;
                    for (Poi p : poisInCluster) {
                        sumLon += p.getLon();
                        sumLat += p.getLat();
                        centerLon = ((sumLon / poisInCluster.size()) + "000000000").substring(0, 9);
                        centerLat = ((sumLat / poisInCluster.size()) + "000000000").substring(0, 9);
                        centerLatLon = ""+centerLon+ "," +centerLat;
                    }
                } else {
                    // 如果不在阈值内，则继续检查下一个点
                    i++;
                }
            }

            // 计算当前聚类的中心点（这里简单取平均值）
            double sumLat = 0, sumLon = 0;
            for (Poi p : poisInCluster) {
                sumLat += p.getLat();
                sumLon += p.getLon();
            }
            centerLon = ((sumLon / poisInCluster.size()) + "000000000").substring(0, 9);
            centerLat = ((sumLat / poisInCluster.size()) +"000000000").substring(0,9);
            centerLatLon = ""+centerLon+ "," +centerLat;
            Cluster cluster = new Cluster();
            cluster.setCenter(centerLatLon);
            cluster.setName("");
            cluster.setPoiList(poisInCluster);
            cluster.setSize(poisInCluster.size());
            cluster.setAddress("");
            cluster.setLon(centerLon);
            cluster.setLat(centerLat);
            aggregate.add(cluster);
        }
        return aggregate;
    }



}
