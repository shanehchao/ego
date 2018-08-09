package com.ego.commons.pojo;

/**
 * 后台接口返回数据类型
 */
public class EgoResult {
    private int status;

    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
