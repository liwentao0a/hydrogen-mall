package com.lwt.hmall.common.client.fallback;

import com.lwt.hmall.api.bean.PmsBaseCatalog1;
import com.lwt.hmall.api.bean.Result;
import com.lwt.hmall.api.util.ResultUtils;
import com.lwt.hmall.common.client.CatalogClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/4/7 10:24
 * @Description
 */
@Component
public class CatalogFallbackFactory implements FallbackFactory<CatalogClient> {
    @Override
    public CatalogClient create(Throwable throwable) {
        return new CatalogClient() {
            @Override
            public Result<List<PmsBaseCatalog1>> listCatalogs() {
                return ResultUtils.fail();
            }
        };
    }
}
