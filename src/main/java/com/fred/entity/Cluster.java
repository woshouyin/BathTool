package com.fred.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cluster {

    private String center;

    private String name;

    private List<Poi> poiList;

    private Integer size;

    private List<String> shopName;

    private String address;

    /**
     * 经度
     */
    private String lon;

    /**
     * 纬度
     */
    private String lat;

}
