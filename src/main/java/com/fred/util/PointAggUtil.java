package com.fred.util;

import com.fred.entity.Cluster;
import com.fred.entity.Poi;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Slf4j
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

    public static List<Cluster> aggregateWithVariableThreshold(List<Poi> points, double initThreshold) {
        List<Cluster> aggregate = new ArrayList<>();
        Function<Integer, Double> thresholdFunc = n -> initThreshold + n * 5;
        // 遍历所有点，创建Cluster并添加到聚合列表中
        while (!points.isEmpty()) {
            Cluster cluster = createCluster(points, thresholdFunc, initThreshold);
            aggregate.add(cluster);
        }
        return aggregate;
    }

    /**
     * 从points中取出一个作为中心，然后将points中距离该中心小于等于threshold的点添加到该中心的Cluster中
     */
    private static Cluster createCluster(List<Poi> points, Function<Integer, Double> thresholdFunc, double initThreshold) {
        Poi centroid = points.remove(0);
        String centerLatLon = centroid.getLocation();
        List<Poi> poisInCluster = new ArrayList<>();
        poisInCluster.add(centroid);
        HashMap<String, Double> centerMap = new HashMap<>();
        centerMap.put("Lat", centroid.getLat());
        centerMap.put("Lon", centroid.getLon());
        double threshold = initThreshold;
        List<Poi> remainingPoints = new ArrayList<>(points);
        points.clear();

        while (!remainingPoints.isEmpty()) {
            boolean pointAdded = addPointsToCluster(remainingPoints, poisInCluster, centerLatLon, threshold, thresholdFunc, centerMap);
            if (!pointAdded) {
                break;
            }
        }

        points.addAll(remainingPoints);
        Double sumLat = centerMap.get("Lat");
        Double sumLon = centerMap.get("Lon");
        return buildCluster(poisInCluster, sumLat, sumLon);
    }

    /**
     * 从remainingPoints中添加符合条件的点到poisInCluster中，并更新centerLatLon和threshold
     * 如果没有添加点至poisInCluster，则返回false
     * @return
     */
    private static boolean addPointsToCluster(List<Poi> remainingPoints, List<Poi> poisInCluster, String centerLatLon, double threshold, Function<Integer, Double> thresholdFunc, HashMap<String,Double> centerMap) {
        boolean pointAdded = false;
        Double sumLat = centerMap.get("Lat");
        Double sumLon = centerMap.get("Lon");
        for (int i = 0; i < remainingPoints.size(); ) {
            Poi currentPoint = remainingPoints.get(i);
            double distance = DistanceCalUtils.calculateDistance(currentPoint.getLocation(), centerLatLon);
            if (distance <= threshold) {
                poisInCluster.add(currentPoint);
                sumLat += currentPoint.getLat();
                sumLon += currentPoint.getLon();
                remainingPoints.remove(i);
                int size = poisInCluster.size();
                String centerLon = String.format("%.8f", sumLon / size);
                String centerLat = String.format("%.8f", sumLat / size);
                centerLatLon = centerLon + "," + centerLat;
                threshold = thresholdFunc.apply(size);
                pointAdded = true;
            } else {
                i++;
            }
        }
        centerMap.put("Lat", sumLat);
        centerMap.put("Lon", sumLon);
        return pointAdded;
    }

    private static Cluster buildCluster(List<Poi> poisInCluster, double sumLat, double sumLon) {
        int size = poisInCluster.size();
        String centerLon = String.format("%.8f", sumLon/size );
        String centerLat = String.format("%.8f", sumLat/size );
        String centerLatLon = centerLon + "," + centerLat;

        Cluster cluster = new Cluster();
        cluster.setCenter(centerLatLon);
        cluster.setName("");
        cluster.setPoiList(poisInCluster);
        cluster.setSize(size);
        cluster.setAddress("");
        cluster.setLon(centerLon);
        cluster.setLat(centerLat);
        return cluster;
    }



}
