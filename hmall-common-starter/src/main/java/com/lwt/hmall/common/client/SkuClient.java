package com.lwt.hmall.common.client;

import com.lwt.hmall.api.bean.Page;
import com.lwt.hmall.api.bean.PmsSkuInfo;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.fallback.SkuFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "hmall-product-service",contextId = "SkuClient",fallbackFactory = SkuFallbackFactory.class)
public interface SkuClient {

    /**
     * 分页查找skuInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/pageSkuInfos",method = RequestMethod.GET)
    public Result<Page<PmsSkuInfo>> pageSkuInfos(@RequestParam("pageNum") @NotNull Integer pageNum,
                                                 @RequestParam("pageSize") @NotNull Integer pageSize);

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
                                                           @RequestParam("pageSize") @NotNull Integer pageSize);

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
                                                          @RequestParam("pageSize") @NotNull Integer pageSize);

    /**
     * 获取sku相关的所有信息(info,image,saleAttr,Attr)
     * @param productId
     * @return
     */
    @RequestMapping(value = "/listSkusByProductId",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkusByProductId(@RequestParam("productId") @NotNull Long productId);

    /**
     * 获取sku信息
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuBySkuId",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuBySkuId(@RequestParam("skuId") @NotNull Long skuId);

    /**
     * 获取skuInfo信息
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuInfoBySkuId",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuInfoBySkuId(@RequestParam("skuId") @NotNull Long skuId);

    /**
     * 获取sku价格
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getSkuPriceBySkuId",method = RequestMethod.GET)
    public Result<BigDecimal> getSkuPriceBySkuId(@RequestParam("skuId") @NotNull Long skuId);
}
