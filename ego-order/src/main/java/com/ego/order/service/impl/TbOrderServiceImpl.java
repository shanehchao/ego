package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.IDUtils;
import com.ego.order.pojo.MyOrder;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.service.TbOrderDubboService;
import com.ego.service.TbOrderItemDubboService;
import com.ego.service.TbOrderShippingDubboService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:11
 */
@Service
public class TbOrderServiceImpl implements TbOrderService {

    @Reference
    private TbOrderDubboService tbOrderDubboService;

    @Reference
    private TbOrderItemDubboService tbOrderItemDubboService;

    @Reference
    private TbOrderShippingDubboService tbOrderShippingDubboService;

    // 插入订单信息
    @Override
    public Map<String, Object> insertOrder(MyOrder myOrder) {
        String orderId = IDUtils.genItemId() + "";

        // 新增订单信息
        TbOrder tbOrder = new TbOrder();
        tbOrder.setOrderId(orderId);
        tbOrder.setPayment(myOrder.getPayment());
        tbOrder.setPaymentType(myOrder.getPaymentType());
        tbOrderDubboService.insertTbOrder(tbOrder);

        // 新增订单商品信息
        for (TbOrderItem tbOrderItem : myOrder.getOrderItems()) {
            tbOrderItem.setId(IDUtils.genItemId()+"");
            tbOrderItem.setOrderId(orderId);
            tbOrderItemDubboService.insertTbOrderItem(tbOrderItem);
        }

        // 新增订单发货信息
        TbOrderShipping orderShipping = myOrder.getOrderShipping();
        orderShipping.setOrderId(orderId);
        tbOrderShippingDubboService.insertTbOrderShipping(orderShipping);

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        map.put("date", calendar.getTime());
        return map;
    }
}
