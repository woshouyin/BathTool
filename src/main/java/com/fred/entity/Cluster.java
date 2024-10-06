package com.fred.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Cluster {

    private String center;

    private String name;

    private List<Poi> poiList;

    private Integer size;

    private List<String> shopName;

}
