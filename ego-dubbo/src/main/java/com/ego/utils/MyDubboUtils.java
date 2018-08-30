package com.ego.utils;

import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午3:01
 */
public class MyDubboUtils {

    @Resource
    private TbItemCatMapper tbItemCatMapper;

    // 根据pid递归查询所有类目
    public List<TbItemCat> selectAllCat(long pid) {
        TbItemCatExample example = new TbItemCatExample();
        example.setOrderByClause("sort_order asc");
        example.createCriteria().andStatusEqualTo(1).andParentIdEqualTo(pid);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
        for (TbItemCat tbItemCat : tbItemCats) {
            if(tbItemCat.getIsParent()) {
                tbItemCat.setTbItemCatChilren(selectAllCat(tbItemCat.getId()));
            }
        }
        return tbItemCats;
    }
}
