package com.ego.service;

import com.ego.pojo.TbUser;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午2:18
 */
public interface TbUserDubboService {

    // 根据用户信息查询用户
    TbUser selectByTbUser(TbUser tbUser);
}
