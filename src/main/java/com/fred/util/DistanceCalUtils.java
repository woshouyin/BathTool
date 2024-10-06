package com.fred.util;

import com.fred.entity.Poi;

public class DistanceCalUtils {


    private static final int EARTH_RADIUS_KM = 6371000; // 地球半径，单位：米

    /**
     * 计算两个经纬度点之间的距离（千米）
     *
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     * @return 两个点之间的距离（千米）
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_KM * c;

        return distance;
    }

    public static double calculateDistance(String latLon1,String latLon2){
        return calculateDistance(getLat(latLon1),getLon(latLon1),getLat(latLon2),getLon(latLon2));
    }

    public static double getLat(String latLon){
        String[] split = latLon.split(",");
        return Double.parseDouble(split[0]);
    }

    public static double getLon(String latLon){
        String[] split = latLon.split(",");
        return Double.parseDouble(split[1]);
    }

    public static void main(String[] args) {
        double lat1 = 39.9042; // 北京的纬度
        double lon1 = 116.4074; // 北京的经度
        double lat2 = 31.2304; // 上海的纬度
        double lon2 = 121.4737; // 上海的经度

        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        System.out.println("北京到上海的距离约为：" + distance + " 米");
    }

}
