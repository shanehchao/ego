package com.ego.service.impl;

import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.ego.service.TbContentDubboService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/31 下午11:01
 */
public class TbContentDubboServiceImpl implements TbContentDubboService {

    @Resource
    private TbContentMapper tbContentMapper;

    // 分页查询内容分类具体内容
    @Override
    public List<TbContent> selectByCategoryId(long categoryId, int page, int rows) {
        PageHelper.startPage(page, rows);

        TbContentExample example = new TbContentExample();
        if (categoryId!=0) {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(example);

        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);

        return pageInfo.getList();
    }

    // 根据分类id查询所有该分类的记录数
    @Override
    public long selectCountTotal(long categoryId) {
        TbContentExample example = new TbContentExample();
        if (categoryId!=0) {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        return tbContentMapper.countByExample(example);
    }
}
