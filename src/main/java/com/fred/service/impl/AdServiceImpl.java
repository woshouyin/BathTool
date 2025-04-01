package com.fred.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fred.entity.Ad;
import com.fred.repo.mapper.AdMapper;
import com.fred.service.intf.AdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {

    @Override
    public List<String> getL4CodeByL3(String l3AdCode) {
        List<Ad> ads = this.baseMapper.selectList(
                new LambdaQueryWrapper<Ad>()
                        .select(Ad::getAdCode)
                        .likeRight(Ad::getAdCode, l3AdCode.substring(0, 4))
                        .eq(Ad::getLevel, 4)

        );
        return ads.stream().map(Ad::getAdCode).collect(Collectors.toList());
    }

    @Override
    public List<Ad> getL4AdByL3(String l3AdCode) {
        List<Ad> ads = this.baseMapper.selectList(
                new LambdaQueryWrapper<Ad>()
                        .select(Ad::getAdCode,Ad::getAdName)
                        .likeRight(Ad::getAdCode, l3AdCode.substring(0, 4))
                        .eq(Ad::getLevel, 4)

        );
        return ads;
    }

    @Override
    public String getPrefixByL3AdCode(String l3AdCode) {
        Ad l3 = this.baseMapper.selectOne(
                new LambdaQueryWrapper<Ad>()
                        .select(Ad::getAdCode, Ad::getAdName)
                        .eq(Ad::getAdCode, l3AdCode)
                        .eq(Ad::getLevel, 3).last("limit 1")
        );
        Ad l2 = this.baseMapper.selectOne(
                new LambdaQueryWrapper<Ad>()
                        .select(Ad::getAdCode, Ad::getAdName)
                        .likeRight(Ad::getAdCode, l3AdCode.substring(0, 2))
                        .eq(Ad::getLevel, 2).last("limit 1")
        );

        String l2AdName = l2.getAdName();
        String l3AdName = l3.getAdName();
        //如果L2中含有省市 自治区 特别行政区 则去除
        l2AdName = l2AdName.replaceAll("省|市|自治区|特别行政区", "");

        //如果L2中含有 市 自治州 地区 则去除
        l3AdName = l3AdName.replaceAll("市|自治州|地区", "");
        return l2AdName + "/"+ l3AdName;
    }

}
