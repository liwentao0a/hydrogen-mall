package com.lwt.hmall.common.client;

import com.lwt.hmall.api.bean.PmsBaseCatalog1;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.common.client.fallback.CatalogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "hmall-product-service",contextId = "CatalogClient",fallbackFactory = CatalogFallbackFactory.class)
public interface CatalogClient {

    /**
     * 查询1、2、3级分类信息
     * @return
     */
    @RequestMapping(value = "/listCatalogs",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog1>> listCatalogs();
}
