package com.lmm.shardingjdbc.service.impl;

import com.lmm.shardingjdbc.dao.OrderItemDao;
import com.lmm.shardingjdbc.model.OrderItem;
import com.lmm.shardingjdbc.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/11/6.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    public void save(OrderItem orderItem) {
        orderItemDao.insert(orderItem);
    }
}
