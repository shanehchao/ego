package com.ego.service.impl;

import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.ego.service.TbContentDubboService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.Date;
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

    // 新增内容管理
    @Override
    public int insertTbContent(TbContent tbContent) {
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        return tbContentMapper.insertSelective(tbContent);
    }

    // 修改内容管理
    @Override
    public int updateTbContent(TbContent tbContent) {
        Date date = new Date();
        tbContent.setUpdated(date);
        return tbContentMapper.updateByPrimaryKeySelective(tbContent);
    }

    // 删除内容管理
    @Override
    public int deleteTbContent(long id) {
        return tbContentMapper.deleteByPrimaryKey(id);
    }

    // 查询当前类目下的所有内容
    @Override
    public List<TbContent> selectByCategoryId(long categoryId) {
        TbContentExample example = new TbContentExample();
        if (categoryId!=0) {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        return tbContentMapper.selectByExampleWithBLOBs(example);
    }

}
