package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午1:57
 */
public interface TbUserService {

    // 登录
    EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response);

    // 通过token查询用户信息
    TbUser selectByToken(String token);

    // 退出
    EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response);
}
