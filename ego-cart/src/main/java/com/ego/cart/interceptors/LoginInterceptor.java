package com.ego.cart.interceptors;

import com.ego.cart.dao.JedisDao;
import com.ego.commons.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午5:32
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private JedisDao jedisDao;

    @Value("${redis.user.key}")
    private String userKey;

    @Value("${redirect.url}")
    private String redirectUrl;

    @Value("${cookie.name}")
    private String cookieName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie
        String uuid = CookieUtils.getCookieValue(request, cookieName);
        // 判断Redis中是否有用户信息
        String userKeyTemp = userKey + uuid;
        if (jedisDao.exists(userKeyTemp)) {
            String jsonStr = jedisDao.get(userKeyTemp);
            if (!StringUtils.isEmpty(jsonStr)) {
                return true;
            }
        }
        response.sendRedirect(redirectUrl + "/user/showLogin");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
