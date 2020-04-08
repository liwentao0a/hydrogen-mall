package com.lwt.hmall.product.controller;

import com.lwt.hmall.api.bean.Page;
import com.lwt.hmall.api.bean.PmsSkuInfo;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.SkuClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/2 15:15
 * @Description
 */
@RestController
@Validated
public class SkuController {

    @Autowired
    private SkuClient skuClient;

    /**
     * 分页查找skuInfo
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/pageSkuInfos",method = RequestMethod.GET)
    public Result<Page<PmsSkuInfo>> pageSkuInfos(@RequestParam("pageNum") @NotNull Integer pageNum){
        int pageSize=10;
        return skuClient.pageSkuInfos(pageNum, pageSize);
    }

    /**
     * 分页查找skuInfo
     * @param catalog3Id
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/pageSkuInfosByCatalog3",method = RequestMethod.GET)
    public Result<Page<PmsSkuInfo>> pageSkuInfosByCatalog3(@RequestParam(value = "catalog3Id",required = false) Long catalog3Id,
                                                           @RequestParam("pageNum") @NotNull Integer pageNum){
        int pageSize=10;
        return skuClient.pageSkuInfosByCatalog3(catalog3Id,pageNum, pageSize);
    }

    /**
     * 分页查找skuInfo
     * @param keyword
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/pageSkuInfosByKeyword",method = RequestMethod.GET)
    public Result<Page<PmsSkuInfo>> pageSkuInfosByKeyword(@RequestParam("keyword") @NotNull String keyword,
                                                          @RequestParam("pageNum") @NotNull Integer pageNum){
        int pageSize=10;
        return skuClient.pageSkuInfosByKeyword(keyword,pageNum, pageSize);
    }

    /**
     * 获取sku相关的所有信息(info,image,saleAttr,Attr)
     * @param productId
     * @return
     */
    @RequestMapping(value = "/listSkusByProductId",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkusByProductId(@RequestParam("productId") @NotNull Long productId){
        return skuClient.listSkusByProductId(productId);
    }

    /**
     * 获取sku信息
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuBySkuId",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuBySkuId(@RequestParam("skuId") @NotNull Long skuId){
        return skuClient.getSkuBySkuId(skuId);
    }

    /**
     * 获取skuInfo信息
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuInfoBySkuId",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuInfoBySkuId(@RequestParam("skuId") @NotNull Long skuId) {
        return skuClient.getSkuInfoBySkuId(skuId);
    }
}
