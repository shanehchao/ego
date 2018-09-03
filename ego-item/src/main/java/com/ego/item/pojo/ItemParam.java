package com.ego.item.pojo;

import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 下午5:27
 */
public class ItemParam {
    private String group;

    private List<MyKeyValue> params;

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public List<MyKeyValue> getParams() {
        return params;
    }
    public void setParams(List<MyKeyValue> params) {
        this.params = params;
    }
}
