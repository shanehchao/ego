package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.*;
import com.ego.manage.dao.JedisDao;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemCatDubboService;
import com.ego.service.TbItemDescDubboService;
import com.ego.service.TbItemDubboService;
import com.ego.service.TbItemParamItemDubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Resource
    private JedisDao jedisDao;

    @Value("${vsftpd.host}")
    private String host;
    @Value("${vsftpd.port}")
    private int port;
    @Value("${vsftpd.username}")
    private String username;
    @Value("${vsftpd.password}")
    private String password;
    @Value("${vsftpd.basePath}")
    private String basePath;
    @Value("${vsftpd.filePath}")
    private String filePath;

    @Value("${nginx.url}")
    private String nginxUrl;

    @Value("${redis.item.key}")
    private String itemKey;

    @Value("${search.url}")
    private String searchUrl;

    //分页查询
    @Override
    public EasyUiDataGrid selectTbItemByPage(int page, int rows) {
        return tbItemDubboService.selectTbItemByPage(page, rows);
    }

    /**
     * 根据商品id修改商品状态
     *
     * @param ids
     * @param status
     * @return
     */
    @Override
    public int updateTbItemStatusById(String ids, byte status) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        String[] idsStr = ids.split(",");
        int result = 0;
        for (String id : idsStr) {
            int index = tbItemDubboService.updateTbItemStatusById(Long.parseLong(id), status);
            if (index > 0) {
                String itemKeyTemp = itemKey+id;
                // 删除和下架，删除solr中的数据
                if (status==2 || status==3) {
                    Map<String, String> param = new HashMap<>();
                    param.put("id", id);
                    String jsonStr = HttpClientUtil.doPost(searchUrl + "/solr/delete", param);
                    EgoResult er = JsonUtils.jsonToPojo(jsonStr, EgoResult.class);
                    if (er.getStatus() != 200) {
                        // 清空solr数据
                        HttpClientUtil.doPost(searchUrl+"/solr/deleteall");
                    }
                    // 同步Redis数据
                    jedisDao.del(itemKeyTemp);
                }
                // 上架，同步solr中数据
                if (status == 1) {
                    Map<String, String> param = new HashMap<>();
                    param.put("id", id);
                    long idNum = Long.parseLong(id);
                    // 根据id查询商品信息
                    TbItem tbItem = tbItemDubboService.selectTbItemById(idNum);
                    param.put("item_title", tbItem.getTitle());
                    param.put("item_sell_point", tbItem.getSellPoint());
                    param.put("item_price", tbItem.getPrice()+"");
                    param.put("item_image", tbItem.getImage());

                    TbItemCat tbItemCat = tbItemCatDubboService.selectTbItemCatById(tbItem.getCid());
                    if (tbItemCat != null) {
                        param.put("item_category_name", tbItemCat.getName());
                    }
                    TbItemDesc tbItemDesc = tbItemDescDubboService.selectByTbItemId(idNum);
                    if (tbItemDesc != null) {
                        param.put("item_desc", tbItemDesc.getItemDesc());
                    }
                    String jsonStr = HttpClientUtil.doPost(searchUrl + "/solr/insert", param);
                    EgoResult er = JsonUtils.jsonToPojo(jsonStr, EgoResult.class);
                    if (er.getStatus() != 200) {
                        // 清空solr数据
                        HttpClientUtil.doPost(searchUrl+"/solr/deleteall");
                    }
                    // 同步Redis数据
                    Map<String, Object> map = BeanUtils.bean2Map(tbItem);
                    String image = tbItem.getImage();
                    map.put("images", StringUtils.isEmpty(image)?new String[1]:image.split(","));
                    jedisDao.set(itemKeyTemp, JsonUtils.objectToJson(map));
                }
                result += index;
            }
        }
        if (result != idsStr.length) {
            return 0;
        }
        return 1;
    }

    //上传文件
    @Override
    public Map<String, Object> upload(MultipartFile uploadFile) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String fileName = IDUtils.genImageName() + uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
        boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fileName, uploadFile.getInputStream());
        if (result) {
            map.put("error", 0);
            map.put("url", nginxUrl + fileName);
        } else {
            map.put("error", 1);
            map.put("message", "上传失败！");
        }
        return map;
    }

    // 新增商品
    @Override
    public int insertTbItem(TbItem tbItem, String itemDesc, String itemParams) {
        // 新增商品
        Date date = new Date();
        long id = IDUtils.genItemId();
        tbItem.setId(id);
        tbItem.setStatus((byte)1);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        int index = tbItemDubboService.insertTbItem(tbItem);

        // 新增商品描述
        TbItemDesc tbitemdesc = new TbItemDesc();
        tbitemdesc.setItemId(id);
        tbitemdesc.setItemDesc(itemDesc);
        tbitemdesc.setCreated(date);
        tbitemdesc.setUpdated(date);
        index += tbItemDescDubboService.insertTbItemDesc(tbitemdesc);

        // 新增规格参数商品内容
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        index += tbItemParamItemDubboService.insertTbItemParamItem(tbItemParamItem);

        if(index == 3) {
            return 1;
        }
        return 0;
    }

}
