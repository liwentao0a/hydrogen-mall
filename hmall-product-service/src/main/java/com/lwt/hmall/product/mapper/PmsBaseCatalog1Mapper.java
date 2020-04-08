package com.lwt.hmall.product.mapper;

import com.lwt.hmall.api.bean.PmsBaseCatalog1;
import com.lwt.hmall.service.tk.TkMapper;

import java.util.List;

public interface PmsBaseCatalog1Mapper extends TkMapper<PmsBaseCatalog1> {

    List<PmsBaseCatalog1> selectAllCatalog();
}
