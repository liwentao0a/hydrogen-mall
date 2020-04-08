package com.lwt.hmall.product.controller;

import com.lwt.hmall.api.bean.Page;
import com.lwt.hmall.api.bean.PmsSkuInfo;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.IsUtils;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.product.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/2 15:09
 * @Description
 */
@RestController
@Validated
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 分页查找skuInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/pageSkuInfos",method = RequestMethod.GET)
    public Result<Page<PmsSkuInfo>> pageSkuInfos(@RequestParam("pageNum") @NotNull Integer pageNum,
                                                 @RequestParam("pageSize") @NotNull Integer pageSize){
        Page<PmsSkuInfo> pmsSkuInfoPage = skuService.pageSkuInfos(pageNum, pageSize);
        return ResultUtils.success(pmsSkuInfoPage);
    }

    /**
     * 分页查找skuInfo
     * @param catalog3Id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/pageSkuInfosByCatalog3",method = RequestMethod.GET)
    public Result<Page<PmsSkuInfo>> pageSkuInfosByCatalog3(@RequestParam(value = "catalog3Id",required = false) Long catalog3Id,
                                                           @RequestParam("pageNum") @NotNull Integer pageNum,
                                                           @RequestParam("pageSize") @NotNull Integer pageSize){
        Page<PmsSkuInfo> pmsSkuInfoPage = skuService.pageSkuInfosByCatalog3(catalog3Id, pageNum, pageSize);
        return ResultUtils.success(pmsSkuInfoPage);
    }

    /**
     * 分页查找skuInfo
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/pageSkuInfosByKeyword",method = RequestMethod.GET)
    public Result<Page<PmsSkuInfo>> pageSkuInfosByKeyword(@RequestParam("keyword") @NotNull String keyword,
                                                          @RequestParam("pageNum") @NotNull Integer pageNum,
                                                          @RequestParam("pageSize") @NotNull Integer pageSize){
        Page<PmsSkuInfo> pmsSkuInfoPage = skuService.pageSkuInfosByKeyword(keyword, pageNum, pageSize);
        return ResultUtils.success(pmsSkuInfoPage);
    }

    /**
     * 获取sku相关的所有信息(info,image,saleAttr,Attr)
     * @param productId
     * @return
     */
    @RequestMapping(value = "/listSkusByProductId",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkuByProductId(@RequestParam("productId") @NotNull Long productId){
        List<PmsSkuInfo> pmsSkuInfos = skuService.listSkusByProductId(productId);
        if (IsUtils.isBlank(pmsSkuInfos)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(pmsSkuInfos);
    }

    /**
     * 获取sku信息
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuBySkuId",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuBySkuId(@RequestParam("skuId") @NotNull Long skuId){
        PmsSkuInfo sku = skuService.getSkuBySkuId(skuId);
        if (IsUtils.isBlank(sku)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(sku);
    }

    /**
     * 获取skuInfo信息
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuInfoBySkuId",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuInfoBySkuId(@RequestParam("skuId") @NotNull Long skuId) {
        PmsSkuInfo pmsSkuInfo = skuService.getSkuInfoBySkuId(skuId);
        if (IsUtils.isBlank(pmsSkuInfo)){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(pmsSkuInfo);
    }

    /**
     * 获取sku价格
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuPriceBySkuId",method = RequestMethod.GET)
    public Result<BigDecimal> getSkuPriceBySkuId(@RequestParam("skuId") @NotNull Long skuId){
        BigDecimal price = skuService.getSkuPriceBySkuId(skuId);
        if (price==null){
            return ResultUtils.result(CodeEnum.RETURN_NULL);
        }
        return ResultUtils.success(price);
    }
}
