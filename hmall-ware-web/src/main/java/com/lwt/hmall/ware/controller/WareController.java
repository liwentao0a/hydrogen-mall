package com.lwt.hmall.ware.controller;

import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.WareClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @Author lwt
 * @Date 2020/3/4 20:08
 * @Description
 */
@RestController
@Validated
public class WareController {

    @Autowired
    private WareClient wareClient;

    /**
     * 获取sku的库存总量
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getAvailableWareSkuStockBySkuId",method = RequestMethod.GET)
    public Result<Integer> getAvailableWareSkuStockBySkuId(@RequestParam("skuId") @NotNull Long skuId){
        return wareClient.getAvailableWareSkuStockBySkuId(skuId);
    }
}
