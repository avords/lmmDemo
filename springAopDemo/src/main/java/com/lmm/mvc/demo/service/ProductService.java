package com.lmm.mvc.demo.service;

import com.lmm.mvc.demo.model.ProdProduct;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */
public interface ProductService {
    ProdProduct getByProductId(Long productId);
    int updateNameByProductId(String productName,Long productId);
    List<ProdProduct> findProductsByIds(List<Long> productIds);
}
