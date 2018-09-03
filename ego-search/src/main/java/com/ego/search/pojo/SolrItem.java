package com.ego.search.pojo;

/**
 * 搜索页面返回数据pojo
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/3 上午9:50
 */
public class SolrItem {
    private long id;
    private String[] images;
    private String title;
    private long price;
    private String sellPoint;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }
}
