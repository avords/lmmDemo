package com.lmm.mvc.demo.service.impl;

import com.lmm.mvc.demo.dao.ProductDao;
import com.lmm.mvc.demo.model.ProdProduct;
import com.lmm.mvc.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/25.
 */
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDao productDao;
    @Override
    public ProdProduct getByProductId(Long productId) {
        //判断是否有事务
        boolean flag = TransactionSynchronizationManager.isActualTransactionActive();
        return productDao.getByProductId(productId);
    }

    @Override
    public int updateNameByProductId(String productName, Long productId) {
        //判断是否有事务
        boolean flag = TransactionSynchronizationManager.isActualTransactionActive();
        Map map = new HashMap();
        map.put("productName",productName);
        map.put("productId",productId);
        return productDao.updateNameByProductId(map);
    }
}
