package com.fred.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 行政区划代码
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

    private String adCode;
    private String adName;
    /**
     * 等级
     * 2 省级
     * 3 市级
     * 4 区县
     */
    private Integer level;


}
