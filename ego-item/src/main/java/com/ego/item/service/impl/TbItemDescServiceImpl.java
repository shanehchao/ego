package com.ego.item.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.item.dao.JedisDao;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import com.ego.service.TbItemDescDubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午4:36
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Resource
    private JedisDao jedisDao;

    @Value("${redis.desc.key}")
    private String descKey;

    // 根据商品id查询商品描述
    @Override
    public TbItemDesc selectByTbItemId(long tbItemId) {
        // Redis缓存中有该商品数据，则从Redis中获取
        String descKeyTemp = descKey+tbItemId;
        if (jedisDao.exists(descKeyTemp)) {
            String jsonStr = jedisDao.get(descKeyTemp);
            if (!StringUtils.isEmpty(jsonStr)) {
                return JsonUtils.jsonToPojo(jsonStr, TbItemDesc.class);
            }
        }
        // Redis缓存中没有该商品数据，则调用dubbo获取，并存入Redis缓存中
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectByTbItemId(tbItemId);
        jedisDao.set(descKeyTemp, JsonUtils.objectToJson(tbItemDesc));
        return tbItemDesc;
    }
}
