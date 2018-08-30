package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.pojo.CatMenu;
import com.ego.item.pojo.PortalCatMenu;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import com.ego.service.TbItemCatDubboService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: aelchao aelchao207@gmail.com
 * @Date: 2018/8/30 下午3:28
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    // 查询所有子类目
    @Override
    public CatMenu showCat() {
        List<Object> objectList = selectCats(tbItemCatDubboService.selectAllCat());
        CatMenu catMenu = new CatMenu();
        catMenu.setData(objectList);
        return catMenu;
    }

    /**
     * 递归拼接商品类目jsonp格式
     * category.getDataService({
     *     "data": [{
     *         "u": "/products/1.html",
     *         "n": "<a href='/products/1.html'>图书、音像、电子书刊</a>",
     *         "i": [{
     *             "u": "/products/2.html",
     *             "n": "电子书刊",
     *             "i": ["/products/3.html|电子书", "/products/4.html|网络原创", "/products/5.html|数字杂志", "/products/6.html|多媒体图书"]
     *         }, {
     *             "u": "/products/7.html",
     *             "n": "音像",
     *             "i": ["/products/8.html|音乐", "/products/9.html|影视", "/products/10.html|教育音像"]
     *         }]
     *     }]
     * @param list
     * @return
     */
    private List<Object> selectCats(List<TbItemCat> list) {
        List<Object> objectList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            if (tbItemCat.getIsParent()) {
                PortalCatMenu portalCatMenu = new PortalCatMenu();
                portalCatMenu.setU("/products/" + tbItemCat.getId() + ".html");
                portalCatMenu.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                portalCatMenu.setI(selectCats(tbItemCat.getTbItemCatChilren()));
                objectList.add(portalCatMenu);
            } else {
                objectList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }
        return objectList;
    }

}
