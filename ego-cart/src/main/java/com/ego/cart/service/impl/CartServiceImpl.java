package com.ego.cart.service.impl;

import com.ego.cart.dao.JedisDao;
import com.ego.cart.pojo.Cart;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午5:53
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private JedisDao jedisDao;

    @Value("${redis.user.key}")
    private String userKey;

    @Value("${redis.cart.key}")
    private String cartKey;

    @Value("${redis.item.key}")
    private String itemKey;

    @Value("${cookie.name}")
    private String cookieName;

    /**
     * 向Redis中添加购物车信息
     * @param id 商品id
     * @param num
     * @param request
     */
    @Override
    public void addCart(long id, int num, HttpServletRequest request) {
        // 获取cookie
        String uuid = CookieUtils.getCookieValue(request, cookieName);
        // 获取user信息
        String userJson = jedisDao.get(userKey + uuid);
        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
        // 从Redis中获取购物车信息
        String cartKeyTemp = cartKey + user.getId();
        // Redis的购物车中存在当前商品，则更新
        if (jedisDao.exists(cartKeyTemp)) {
            String cartJson = jedisDao.get(cartKeyTemp);
            List<Cart> cartList = JsonUtils.jsonToList(cartJson, Cart.class);
            Iterator<Cart> it = cartList.iterator();
            boolean flag = false;
            while (it.hasNext()) {
                Cart next = it.next();
                if (next.getId() == id) {
                    next.setNum(next.getNum()+num);
                // Redis的购物车中不存在当前商品，则直接添加
                } else {
                    flag = true;
                }
            }
            if (CollectionUtils.isEmpty(cartList) || flag) {
                // 从Redis中获取商品信息
                String itemJson = jedisDao.get(itemKey + id);
                TbItemChild child = JsonUtils.jsonToPojo(itemJson, TbItemChild.class);

                // 添加到购物车
                Cart cart = new Cart();
                cart.setId(id);
                cart.setNum(num);
                cart.setTitle(child.getTitle());
                cart.setPrice(child.getPrice());
                cart.setImages(child.getImages());
                cartList.add(cart);
            }
            jedisDao.set(cartKeyTemp, JsonUtils.objectToJson(cartList));
        // Redis的购物车中不存在当前商品，则直接添加
        } else {
            // 从Redis中获取商品信息
            String itemJson = jedisDao.get(itemKey + id);
            TbItemChild child = JsonUtils.jsonToPojo(itemJson, TbItemChild.class);

            // 添加到购物车
            List<Cart> cartList = new ArrayList<>();
            Cart cart = new Cart();
            cart.setId(id);
            cart.setNum(num);
            cart.setTitle(child.getTitle());
            cart.setPrice(child.getPrice());
            cart.setImages(child.getImages());
            cartList.add(cart);
            jedisDao.set(cartKeyTemp, JsonUtils.objectToJson(cartList));
        }
    }

    // 显示购物车详情
    @Override
    public List<Cart> showCartDetail(HttpServletRequest request) {
        // 获取cookie
        String uuid = CookieUtils.getCookieValue(request, cookieName);
        // 根据cookie获取用户信息
        String userJson = jedisDao.get(userKey + uuid);
        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
        // 从Redis中获取购物车信息
        String jsonStr = jedisDao.get(cartKey + user.getId());
        return JsonUtils.jsonToList(jsonStr, Cart.class);
    }

    // 修改购物车商品数量
    @Override
    public EgoResult updateNum(long id, int num, HttpServletRequest request) {
        // 获取cookie
        String uuid = CookieUtils.getCookieValue(request, cookieName);
        // 获取user信息
        String userJson = jedisDao.get(userKey + uuid);
        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
        // 根据user信息获取购物车信息
        String cartKeyTemp = cartKey + user.getId();
        String cartListJson = jedisDao.get(cartKeyTemp);
        List<Cart> cartList = JsonUtils.jsonToList(cartListJson, Cart.class);
        for (Cart cart : cartList) {
            if (cart.getId() == id) {
                cart.setNum(num);
            }
        }
        // 更新Redis中购物车信息
        String result = jedisDao.set(cartKeyTemp, JsonUtils.objectToJson(cartList));
        EgoResult er = new EgoResult();
        if ("OK".equalsIgnoreCase(result)) {
            er.setStatus(200);
        }
        return er;
    }

    // 删除购物车信息
    @Override
    public EgoResult deleteCart(long id, HttpServletRequest request) {
        // 获取cookie
        String uuid = CookieUtils.getCookieValue(request, cookieName);
        // 获取user信息
        String userJson = jedisDao.get(userKey + uuid);
        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
        // 根据user信息获取购物车信息
        String cartKeyTemp = cartKey + user.getId();
        String cartListJson = jedisDao.get(cartKeyTemp);
        List<Cart> cartList = JsonUtils.jsonToList(cartListJson, Cart.class);
        Iterator<Cart> it = cartList.iterator();
        while (it.hasNext()) {
            Cart next = it.next();
            if (next.getId() == id) {
                it.remove();
            }
        }
        // 更新Redis中购物车信息
        String result = jedisDao.set(cartKeyTemp, JsonUtils.objectToJson(cartList));
        EgoResult er = new EgoResult();
        if ("OK".equalsIgnoreCase(result)) {
            er.setStatus(200);
        }
        return er;
    }

    // 根据选中的商品id查询购物车信息
    @Override
    public List<Cart> selectByTbItemId(List<Long> ids, HttpServletRequest request) {
        // 获取cookie
        String uuid = CookieUtils.getCookieValue(request, cookieName);
        // 获取user信息
        String userJson = jedisDao.get(userKey + uuid);
        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
        // 根据user信息获取购物车信息
        String cartKeyTemp = cartKey + user.getId();
        String cartListJson = jedisDao.get(cartKeyTemp);
        List<Cart> cartList = JsonUtils.jsonToList(cartListJson, Cart.class);
        List<Cart> orderCartList = new ArrayList<>();
        for (Long id : ids) {
            for (Cart cart : cartList) {
                if (cart.getId() == id) {
                    orderCartList.add(cart);
                    TbItemChild child = JsonUtils.jsonToPojo(jedisDao.get(itemKey + id), TbItemChild.class);
                    if (cart.getNum() > child.getNum()) {
                        return null;
                    }
                }
            }
        }
        return orderCartList;
    }
}
