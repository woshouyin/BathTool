package com.fred.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cluster {

    @ExcelIgnore
    private String center;

    @ExcelProperty("市场名称")
    private String name;

    /**
     * 经度
     */
    @ExcelProperty("经度")
    private String lon;

    /**
     * 纬度
     */
    @ExcelProperty("纬度")
    private String lat;

    @ExcelProperty("地址")
    private String MarketAddress;

    /**
     * 找到地图对应的颜色
     */
    @ExcelProperty("颜色")
    private String color;

    @ExcelProperty("图标")
    private String label;

    @ExcelProperty("图标内容")
    private String labelContent;

    @ExcelIgnore
    private List<Poi> poiList;

    @ExcelProperty("店铺数量")
    private Integer size;

    @ExcelIgnore
    private List<String> shopName;

    @ExcelProperty("文件夹")
    private String folder;

    @ExcelIgnore
    private List<String> addresses;


    @ExcelProperty("店铺地址")
    private String address;


    @ExcelProperty("店铺名称")
    private String shopNames;

}
