package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITree;

import java.util.List;

public interface TbItemCatService {
    //根据父类目id查询所有子类目
    List<EasyUITree> selectTbItemCatByPid(long pid);
}