package com.fred.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fred.entity.Poi;
import com.fred.repo.mapper.PoiMapper;
import com.fred.service.intf.PoiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PoiServiceImpl extends ServiceImpl<PoiMapper, Poi> implements PoiService {

    //获取所有城市中的点
    public List<Poi> getPoiByCityName(String cityName){
        List<Poi> poiList = this.baseMapper.selectList(new LambdaQueryWrapper<Poi>().eq(Poi::getCityname, cityName));
        return poiList;
    }

    //获取所有县/区中的点
    public List<Poi> getPoiByAdName(String adName){
        List<Poi> poiList = this.baseMapper.selectList(new LambdaQueryWrapper<Poi>().eq(Poi::getAdname, adName));
        return poiList;
    }

    @Override
    public List<Poi> getPoiByAdCode(String adCode) {
        List<Poi> poiList = this.baseMapper.selectList(new LambdaQueryWrapper<Poi>().eq(Poi::getAdcode, adCode));
        return poiList;
    }

}
