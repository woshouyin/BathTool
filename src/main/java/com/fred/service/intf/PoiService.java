package com.fred.service.intf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fred.entity.Poi;

import java.util.List;

public interface PoiService extends IService<Poi> {

    List<Poi> getPoiByCityName(String cityName);

    List<Poi> getPoiByAdName(String adName);

    List<Poi> getPoiByAdCode(String adCode);
}
