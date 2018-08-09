package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import com.ego.service.TbItemCatDubboService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    //根据父类目id查询所有子类目
    @Override
    public List<EasyUITree> selectTbItemCatByPid(long pid) {
        List<TbItemCat> tbItemCats = tbItemCatDubboService.selectTbItemCatByPid(pid);
        List<EasyUITree> easyUITrees = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCats) {
            EasyUITree easyUITree = new EasyUITree();
            easyUITree.setId(tbItemCat.getId());
            easyUITree.setText(tbItemCat.getName());
            easyUITree.setState(tbItemCat.getIsParent() ? "closed" : "open");
            easyUITrees.add(easyUITree);
        }
        return easyUITrees;
    }
}
