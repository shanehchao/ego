package com.ego.cart.dao.impl;

import com.ego.cart.dao.JedisDao;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/1 下午5:06
 */
public class JedisDaoImpl implements JedisDao {

    @Resource
    private JedisCluster jedisCluster;

    @Override
    public boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public long del(String key) {
        return jedisCluster.del(key);
    }

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }
}
