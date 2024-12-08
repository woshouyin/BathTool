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
}
