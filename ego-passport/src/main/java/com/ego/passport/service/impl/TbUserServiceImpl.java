package com.ego.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.passport.dao.JedisDao;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.service.TbUserDubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午1:57
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Reference
    private TbUserDubboService tbUserDubboService;

    @Resource
    private JedisDao jedisDao;

    @Value("${redis.user.key}")
    private String userKey;

    @Value("${cookie.name}")
    private String cookieName;

    // 登录
    @Override
    public EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();
        TbUser user = tbUserDubboService.selectByTbUser(tbUser);
        if (user != null) {
            // cookie添加值
            String uuid = UUID.randomUUID().toString();
            CookieUtils.setCookie(request, response, cookieName, uuid);

            // Redis缓存中添加值
            jedisDao.set(userKey+uuid, JsonUtils.objectToJson(user));
            er.setStatus(200);
            er.setMsg("OK");
        }
        return er;
    }

    // 通过token查询用户信息
    @Override
    public TbUser selectByToken(String token) {
        String jsonStr = jedisDao.get(userKey + token);
        if (!StringUtils.isEmpty(jsonStr)) {
            return JsonUtils.jsonToPojo(jsonStr, TbUser.class);
        }
        return null;
    }

    // 退出
    @Override
    public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();
        // 删除cookie
        CookieUtils.deleteCookie(request, response, cookieName);
        // 删除Redis
        long index = jedisDao.del(userKey + token);
        if (index > 0) {
            er.setStatus(200);
        }
        return er;
    }
}
