package com.ego.cart.service;

import com.ego.cart.pojo.Cart;
import com.ego.commons.pojo.EgoResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午5:54
 */
public interface CartService {

    // 向Redis中添加购物车信息
    void addCart(long id, int num, HttpServletRequest request);

    // 显示购物车详情
    List<Cart> showCartDetail(HttpServletRequest request);

    // 修改购物车商品数量
    EgoResult updateNum(long id, int num, HttpServletRequest request);

    // 删除购物车信息
    EgoResult deleteCart(long id, HttpServletRequest request);

    // 根据选中的商品id查询购物车信息
    List<Cart> selectByTbItemId(List<Long> ids, HttpServletRequest request);
}
