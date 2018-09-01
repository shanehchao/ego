package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.service.TbContentDubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/1 下午4:10
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboService;

    @Resource
    private JedisCluster jedisCluster;

    @Value("${redis.bigpic.key}")
    private String bigpic;

    @Value("${bigpic.id}")
    private long categoryId;

    // 查询当前类目下的所有内容
    @Override
    public String showBigPic() {
        // jedis缓存中获取
        String json = jedisCluster.get(bigpic);
        // jedis中没有数据则调用dubbo从数据库中获取，并存入Redis中
        if (StringUtils.isEmpty(json)) {
            List<TbContent> tbContents = tbContentDubboService.selectByCategoryId(categoryId);
            json = JsonUtils.objectToJson(tbContents);
            jedisCluster.set(bigpic, json);
        }

        /**
         * 数据转化
         * var data = [{
         *     "srcB": "http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg",
         *     "height": 240,
         *     "alt": "",
         *     "width": 670,
         *     "src": "http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg",
         *     "widthB": 550,
         *     "href": "http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE",
         *     "heightB": 240
         * }, {
         *     "srcB": "http://image.ego.com/images/2015/03/03/2015030304353109508500.jpg",
         *     "height": 240,
         *     "alt": "",
         *     "width": 670,
         *     "src": "http://image.ego.com/images/2015/03/03/2015030304353109508500.jpg",
         *     "widthB": 550,
         *     "href": "http://sale.jd.com/act/UMJaAPD2VIXkZn.html?cpdad=1DLSUE",
         *     "heightB": 240
         * }]
         */
        List<TbContent> jsonList = JsonUtils.jsonToList(json, TbContent.class);
        List<Map<String, Object>> list = new ArrayList<>();
        for (TbContent tbContent : jsonList) {
            Map<String, Object> map = new HashMap<>();
            map.put("srcB", tbContent.getPic2());
            map.put("height", 240);
            map.put("alt", "图片正在维护");
            map.put("width", 670);
            map.put("src", tbContent.getPic());
            map.put("widthB", 670);
            map.put("href", tbContent.getUrl());
            map.put("heightB", 240);
            list.add(map);
        }
        return JsonUtils.objectToJson(list);
    }
}
