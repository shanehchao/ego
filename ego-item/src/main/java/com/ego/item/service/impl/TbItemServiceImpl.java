package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.item.dao.JedisDao;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.service.TbItemDubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午3:44
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Resource
    private JedisDao jedisDao;

    @Value(("${redis.item.key}"))
    private String itemKey;

    @Value(("${redis.desc.key}"))
    private String descKey;

    @Value(("${redis.param.key}"))
    private String paramKey;

    // 根据商品id查询商品详情
    @Override
    public TbItemChild selectByTbItemId(long id) {
        // Redis缓存中有该商品数据，则从Redis中获取
        String itemKeyTemp = itemKey+id;
        if (jedisDao.exists(itemKeyTemp)) {
            String jsonStr = jedisDao.get(itemKeyTemp);
            if (!StringUtils.isEmpty(jsonStr)) {
                return JsonUtils.jsonToPojo(jsonStr, TbItemChild.class);
            }
        }
        // Redis缓存中没有该商品数据，则调用dubbo获取，并存入Redis缓存中
        TbItem tbItem = tbItemDubboService.selectTbItemById(id);
        TbItemChild child = new TbItemChild();
        BeanUtils.copyProperties(tbItem, child);
        String image = tbItem.getImage();
        child.setImages(StringUtils.isEmpty(image)?new String[1]:image.split(","));
        //存入Redis缓存中
        jedisDao.set(itemKeyTemp, JsonUtils.objectToJson(child));
        return child;
    }
}
