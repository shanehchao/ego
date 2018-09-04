package com.ego.service.impl;

import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import com.ego.service.TbUserDubboService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午2:19
 */
public class TbUserDubboServiceImpl implements TbUserDubboService {

    @Resource
    private TbUserMapper tbUserMapper;

    // 根据用户信息查询用户
    @Override
    public TbUser selectByTbUser(TbUser tbUser) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(tbUsers)) {
            return tbUsers.get(0);
        }
        return null;
    }
}
