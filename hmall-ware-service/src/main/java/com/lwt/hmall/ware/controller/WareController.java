package com.lwt.hmall.ware.controller;

import com.lwt.hmall.api.bean.OmsOrderItem;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.ware.service.WareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/4 18:07
 * @Description
 */
@RestController
@Validated
@CrossOrigin
public class WareController {

    @Autowired
    private WareService wareService;

    /**
     * 获取sku的库存总量
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getAvailableWareSkuStockBySkuId",method = RequestMethod.GET)
    public Result<Integer> getAvailableWareSkuStockBySkuId(@RequestParam("skuId") @NotNull Long skuId){
        int totalStock = wareService.getAvailableWareSkuStockBySkuId(skuId);
        return ResultUtils.success(totalStock);
    }

    /**
     * 锁定库存
     * @param omsOrderItems
     * @return
     */
    @RequestMapping(value = "/lockWareSkuByOrderItems",method = RequestMethod.PUT)
    public Result lockWareSkuByOrderItems(@RequestBody @NotNull @Size(min = 1) List<OmsOrderItem> omsOrderItems){
        if(wareService.lockWareSkuByOrderItems(omsOrderItems)){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

}
