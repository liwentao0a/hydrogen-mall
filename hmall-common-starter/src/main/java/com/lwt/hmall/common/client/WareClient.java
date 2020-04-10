package com.lwt.hmall.common.client;

import com.lwt.hmall.api.bean.OmsOrderItem;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.fallback.WareFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@FeignClient(name = "hmall-ware-service",contextId = "WareClient",fallbackFactory = WareFallbackFactory.class)
public interface WareClient {

    /**
     * 获取sku的库存总量
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getAvailableWareSkuStockBySkuId",method = RequestMethod.GET)
    public Result<Integer> getAvailableWareSkuStockBySkuId(@RequestParam("skuId") @NotNull Long skuId);

    /**
     * 锁定库存
     * @param omsOrderItems
     * @return
     */
    @RequestMapping(value = "/lockWareSkuByOrderItems",method = RequestMethod.PUT)
    public Result lockWareSkuByOrderItems(@RequestBody @NotNull @Size(min = 1) List<OmsOrderItem> omsOrderItems);
}
