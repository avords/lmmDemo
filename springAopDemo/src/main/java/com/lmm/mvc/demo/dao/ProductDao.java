package com.lmm.mvc.demo.dao;

import com.lmm.mvc.demo.model.ProdProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/25.
 */
@Repository
public interface ProductDao {
    ProdProduct getByProductId(Long productId);
    int updateNameByProductId(Map map);
    List<ProdProduct> findProductsByIds(@Param("productIds") List<Long> productIds);
}
