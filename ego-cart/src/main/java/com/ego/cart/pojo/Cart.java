package com.ego.cart.pojo;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/9/4 下午6:19
 */
public class Cart {
    private long id;
    private long price;
    private String title;
    // 购买商品数量
    private int num;
    private String[] images;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", num=" + num +
                '}';
    }
}

