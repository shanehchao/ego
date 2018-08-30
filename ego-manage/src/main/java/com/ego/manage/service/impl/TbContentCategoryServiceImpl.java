package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryDubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午6:03
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboService;

    // 根据内容分类pid查询所有子分类
    @Override
    public List<EasyUITree> selectByPid(long pid) {
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboService.selectByPid(pid);
        List<EasyUITree> treeList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            EasyUITree tree = new EasyUITree();
            tree.setId(tbContentCategory.getId());
            tree.setText(tbContentCategory.getName());
            tree.setState(tbContentCategory.getIsParent() ? "closed" : "open");

            treeList.add(tree);
        }
        return treeList;
    }

    // 新增内容分类
    @Override
    public EgoResult insertTbContentCategory(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();

        // 查询当前类目是否有同名子类目
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboService.selectByTbContentCategory(tbContentCategory);
        if (tbContentCategories!=null && tbContentCategories.size()>0) {
            er.setStatus(400);
        } else {
            // 没有同名则新增
            tbContentCategory.setId(IDUtils.genItemId());
            tbContentCategory.setStatus(1);
            tbContentCategory.setSortOrder(1);
            tbContentCategory.setIsParent(false);
            int index = tbContentCategoryDubboService.insertTbContentCategory(tbContentCategory);
            if (index>0) {
                // 新增成功后，修改父分类isParent为true
                TbContentCategory cateParent = new TbContentCategory();
                cateParent.setId(tbContentCategory.getParentId());
                cateParent.setIsParent(true);
                int result = tbContentCategoryDubboService.updateTbContentCategory(cateParent);
                if (result>0) {
                    er.setStatus(200);
                    er.setData(tbContentCategory);
                }
            }
        }
        return er;
    }

    // 修改内容分类
    @Override
    public EgoResult updateTbContentCategory(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        // 查询当前内容分类详情
        TbContentCategory cateDetail = new TbContentCategory();
        cateDetail.setId(tbContentCategory.getId());
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboService.selectByTbContentCategory(cateDetail);
        if (!CollectionUtils.isEmpty(tbContentCategories)) {
            cateDetail = tbContentCategories.get(0);
        }

        // 查询当前类目是否有同名子类目
        TbContentCategory cateOther = new TbContentCategory();
        cateOther.setParentId(cateDetail.getParentId());
        cateOther.setName(tbContentCategory.getName());
        List<TbContentCategory> tbContentCategoryList = tbContentCategoryDubboService.selectByTbContentCategory(cateOther);

        // 没有同名则修改
        if (CollectionUtils.isEmpty(tbContentCategoryList)) {
            int index = tbContentCategoryDubboService.updateTbContentCategory(tbContentCategory);
            if (index>0) {
                er.setStatus(200);
            }
        } else {
            er.setStatus(400);
        }
        return er;
    }

    // 删除内容分类
    @Override
    public EgoResult deleteTbContentCategory(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        // 查询当前内容分类详情
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboService.selectByTbContentCategory(tbContentCategory);
        if (!CollectionUtils.isEmpty(tbContentCategories)) {
            tbContentCategory = tbContentCategories.get(0);
        }

        // 查询当前父类目是否有其他的子类目
        TbContentCategory cateOther = new TbContentCategory();
        cateOther.setParentId(tbContentCategory.getParentId());
        cateOther.setStatus(1);
        List<TbContentCategory> tbContentCategoryList = tbContentCategoryDubboService.selectByTbContentCategory(cateOther);

        // 删除当前内容分类
        tbContentCategory.setStatus(2);
        int index = tbContentCategoryDubboService.updateTbContentCategory(tbContentCategory);

        if (index>0) {
            // 有其他子类目则删除当前内容分类，无须修改父类目isParent
            if (tbContentCategoryList!=null && tbContentCategoryList.size()>1) {
                er.setStatus(200);
            // 没有其他子类目则删除当前内容分类，并且更改父类目isParent为false
            } else {
                TbContentCategory cateParent = new TbContentCategory();
                cateParent.setId(tbContentCategory.getParentId());
                cateParent.setIsParent(false);
                int result = tbContentCategoryDubboService.updateTbContentCategory(cateParent);
                if (result>0) {
                    er.setStatus(200);
                }
            }
        }
        return er;
    }

}
