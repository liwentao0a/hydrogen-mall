package com.lwt.hmall.product.controller;

import com.lwt.hmall.api.bean.PmsBaseCatalog1;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.CatalogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/2/26 20:03
 * @Description
 */
@RestController
@Validated
public class CatalogController {

    @Autowired
    private CatalogClient catalogClient;

    /**
     * 查询1、2、3级分类信息
     * @return
     */
    @RequestMapping(value = "/listCatalogs",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog1>> listCatalogs(){
        Result<List<PmsBaseCatalog1>> listResult = catalogClient.listCatalogs();
        return listResult;
    }
}
