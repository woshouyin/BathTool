package com.fred.entity;

import lombok.Data;

@Data
public class Poi {
    private String id;// "B0IR550HY6"
    private String parent;//"B0HDK5D5VO"
    private String address;//"二环北路88号5联印象城一楼"
    private String distance;
    private String pcode;//"330000"
    private String adcode;//"330602"
    private String pname;//"浙江省"
    private String cityname;//"绍兴市"
    private String type;// "购物服务;家居建材市场;厨卫市场"
    private String typecode;// "060604"
    private String adname;// "越城区"
    private String citycode;// "0575"
    private String name;// "方太体验店"
    private String location;// "120.570802,30.022274"

    //手动添加的字段
    private String marketName;//
    private String marketCenter;//


    public double getLat(){
        String[] split = location.split(",");
        return Double.parseDouble(split[0]);
    }

    public double getLon(){
        String[] split = location.split(",");
        return Double.parseDouble(split[1]);
    }


}
