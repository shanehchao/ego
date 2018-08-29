package com.ego.manage.service;

import com.ego.commons.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface TbItemService {
    //分页查询
    EasyUiDataGrid selectTbItemByPage(int page, int rows);

    //根据商品id修改商品状态
    int updateTbItemStatusById(String ids, byte status);

    //上传文件
    Map<String, Object> upload(MultipartFile uploadFile) throws IOException;

    //新增商品
    int insertTbItem(TbItem tbItem, String itemDesc, String itemParams);
}
