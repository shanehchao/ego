package com.ego.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatDubboService {
    //根据父类目id查询所有子类目
    List<TbItemCat> selectTbItemCatByPid(long pid);

    //根据商品类目id查询类目
    TbItemCat selectTbItemCatById(long id);

    //查询所有商品类目
    List<TbItemCat> selectAllCat();
}
