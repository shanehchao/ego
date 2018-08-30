package com.ego.service.impl;

import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import com.ego.service.TbContentCategoryDubboService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午5:55
 */
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    // 根据内容分类pid查询所有子分类
    @Override
    public List<TbContentCategory> selectByPid(long pid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.setOrderByClause("sort_order asc");
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    // 新增内容分类
    @Override
    public int insertTbContentCategory(TbContentCategory tbContentCategory) {
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        return tbContentCategoryMapper.insertSelective(tbContentCategory);
    }

    // 查询（同一父类目下是否具有同名的子类目）
    @Override
    public List<TbContentCategory> selectByTbContentCategory(TbContentCategory tbContentCategory) {
        TbContentCategoryExample example = new TbContentCategoryExample();

        if (tbContentCategory.getSortOrder()!=null) {
            example.setOrderByClause("sort_order asc");
        }

        TbContentCategoryExample.Criteria criteria = example.createCriteria();

        Long id = tbContentCategory.getId();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        Long parentId = tbContentCategory.getParentId();
        if (!StringUtils.isEmpty(parentId)) {
            criteria.andParentIdEqualTo(parentId);
        }

        String name = tbContentCategory.getName();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameEqualTo(name);
        }

        Integer status = tbContentCategory.getStatus();
        if (!StringUtils.isEmpty(status)) {
            criteria.andStatusEqualTo(status);
        }

        Boolean isParent = tbContentCategory.getIsParent();
        if (!StringUtils.isEmpty(isParent)) {
            criteria.andIsParentEqualTo(isParent);
        }

        return tbContentCategoryMapper.selectByExample(example);
    }

    // 修改内容分类
    @Override
    public int updateTbContentCategory(TbContentCategory tbContentCategory) {
        Date date = new Date();
        tbContentCategory.setUpdated(date);
        return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }

}
