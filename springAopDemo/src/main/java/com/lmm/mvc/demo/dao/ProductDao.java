package com.lmm.mvc.demo.dao;

import com.lmm.mvc.demo.model.ProdProduct;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/25.
 */
@Repository
public interface ProductDao {
    ProdProduct getByProductId(Long productId);
    int updateNameByProductId(Map map);
}
