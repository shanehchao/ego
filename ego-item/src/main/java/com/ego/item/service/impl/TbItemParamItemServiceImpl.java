package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.item.dao.JedisDao;
import com.ego.item.pojo.ItemParam;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemParamItemDubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午5:00
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    @Resource
    private JedisDao jedisDao;

    @Value("${redis.param.key}")
    private String paramKey;

    // 根据商品id查询规则参数内容
    @Override
    public String selectByTbItemId(long tbItemId) {
        // Redis缓存中有该商品数据，则从Redis中获取
        String paramKeyTemp = paramKey+tbItemId;
        if (jedisDao.exists(paramKeyTemp)) {
            return jedisDao.get(paramKeyTemp);
        }
        // Redis缓存中没有该商品数据，则调用dubbo获取，并存入Redis缓存中
        TbItemParamItem itemParamItem = tbItemParamItemDubboService.selectByTbItemId(tbItemId);
        List<ItemParam> paramList = JsonUtils.jsonToList(itemParamItem.getParamData(), ItemParam.class);
        StringBuffer sb = new StringBuffer();
        sb.append("<table>");
        for (ItemParam itemParam : paramList) {
            sb.append("<tr>");
            for(int i=0;i<itemParam.getParams().size();i++){
                if(i==0){
                    sb.append("<td>");
                    sb.append(itemParam.getGroup());
                    sb.append("</td>");
                    sb.append("<td>"+itemParam.getParams().get(i).getK()+"</td>");
                    sb.append("<td>"+itemParam.getParams().get(i).getV()+"</td>");
                }else{
                    sb.append("<td>");
                    sb.append("</td>");
                    sb.append("<td>"+itemParam.getParams().get(i).getK()+"</td>");
                    sb.append("<td>"+itemParam.getParams().get(i).getV()+"</td>");
                }
            }
            sb.append("</tr>");

        }
        sb.append("</table>");
        jedisDao.set(paramKeyTemp,sb.toString() );
        return itemParamItem.getParamData();
    }
}
