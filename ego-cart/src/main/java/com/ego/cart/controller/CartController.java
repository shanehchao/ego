package com.ego.cart.controller;

import com.ego.cart.pojo.Cart;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/5 上午9:20
 */
@Controller
public class CartController {

    @Resource
    private CartService cartService;

    // 跳转添加购物车成功页面
    @RequestMapping("/cart/add/{id}.html")
    public String showCartSuccess(@PathVariable long id, @RequestParam(defaultValue = "1") int num, HttpServletRequest request) {
        cartService.addCart(id, num, request);
        return "/WEB-INF/jsp/cartSuccess.jsp";
    }

    // 显示购物车详情
    @RequestMapping("/cart/cart.html")
    public String showCartDetail(HttpServletRequest request, Model model) {
        List<Cart> cartList =  cartService.showCartDetail(request);
        model.addAttribute("cartList", cartList);
        return "/WEB-INF/jsp/cart.jsp";
    }

    // 修改购物车商品数量
    @RequestMapping("/cart/update/num/{id}/{num}.action")
    @ResponseBody
    public EgoResult updateNum(@PathVariable long id, @PathVariable int num, HttpServletRequest request) {
        return cartService.updateNum(id, num, request);
    }

    // 删除购物车信息
    @RequestMapping("/cart/delete/{id}.action")
    @ResponseBody
    public EgoResult deleteCart(@PathVariable long id, HttpServletRequest request) {
        return cartService.deleteCart(id, request);
    }

    // 核对订单信息
    @RequestMapping("/order/order-cart.html")
    public String showOrder(@RequestParam("id") List<Long> ids, Model model, HttpServletRequest request) {
        // 根据选中的商品id查询购物车信息
        List<Cart> cartList = cartService.selectByTbItemId(ids, request);
        if (CollectionUtils.isEmpty(cartList)) {
            model.addAttribute("message", "库存不足！");
            return "/WEB-INF/jsp/info/info.jsp";
        }
        model.addAttribute("cartList", cartList);
        return "/WEB-INF/jsp/order-cart.jsp";
    }
}
