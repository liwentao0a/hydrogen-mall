package com.lwt.hmall.product.controller;

import com.lwt.hmall.api.bean.PmsBaseCatalog1;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.IsUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.product.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/2/26 19:54
 * @Description
 */
@RestController
@Validated
@CrossOrigin
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    /**
     * 查询1、2、3级分类信息
     * @return
     */
    @RequestMapping(value = "/listCatalogs",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog1>> listCatalogs(){
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalogService.listCatalogs();
        if (IsUtils.isBlank(pmsBaseCatalog1s)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(pmsBaseCatalog1s);
    }
}
