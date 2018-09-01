package com.ego.portal.dao;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/1 下午5:06
 */
public interface JedisDao {
    // 判断key是否存在
    boolean exists(String key);

    // 取值
    String get(String key);

    // 删除
    long del(String key);

    // 存入
    String set(String key, String value);
}
