package com.fred.service.intf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fred.entity.Ad;

import java.util.List;

public interface AdService extends IService<Ad> {

    List<String> getL4CodeByL3(String l3AdCode);

    List<Ad> getL4AdByL3(String l3AdCode);

    String getPrefixByL3AdCode(String l3AdCode);
}
