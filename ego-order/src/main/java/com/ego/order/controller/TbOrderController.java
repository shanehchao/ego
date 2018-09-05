package com.ego.order.controller;

import com.ego.order.pojo.MyOrder;
import com.ego.order.service.TbOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 下午5:11
 */
@Controller
public class TbOrderController {

    @Resource
    private TbOrderService tbOrderService;

    // 提交订单
    @RequestMapping("/order/create.html")
    public String showOrderSuccess(MyOrder myOrder, Model model) {
        // 插入订单信息
        Map<String, Object> map = tbOrderService.insertOrder(myOrder);
        model.addAttribute("orderId", map.get("orderId"));
        model.addAttribute("date", map.get("date"));
        model.addAttribute("payment", myOrder.getPayment());
        return "/WEB-INF/jsp/success.jsp";
    }
}
