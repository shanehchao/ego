package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.service.TbItemDescDubboService;
import com.ego.service.TbItemDubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Value("${vsftpd.host}")
    String host;
    @Value("${vsftpd.port}")
    int port;
    @Value("${vsftpd.username}")
    String username;
    @Value("${vsftpd.password}")
    String password;
    @Value("${vsftpd.basePath}")
    String basePath;
    @Value("${vsftpd.filePath}")
    String filePath;

    @Value("${nginx.url}")
    String nginxUrl;

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
    public int updateTbItemStatusById(String ids, byte status) {
        String[] idsStr = ids.split(",");
        int index = 0;
        for (String id : idsStr) {
            index += tbItemDubboService.updateTbItemStatusById(Long.parseLong(id), status);
        }
        if (index != idsStr.length) {
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

    //新增商品
    @Override
    public int insertTbItem(TbItem tbItem, String itemDesc) {
        Date date = new Date();
        long id = IDUtils.genItemId();
        tbItem.setId(id);
        tbItem.setStatus((byte)1);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        int index = tbItemDubboService.insertTbItem(tbItem);
        TbItemDesc tbitemdesc = new TbItemDesc();
        tbitemdesc.setItemId(id);
        tbitemdesc.setItemDesc(itemDesc);
        tbitemdesc.setCreated(date);
        tbitemdesc.setUpdated(date);
        index += tbItemDescDubboService.insertTbItemDesc(tbitemdesc);
        if(index == 2) {
            return 1;
        }
        return 0;
    }

}
