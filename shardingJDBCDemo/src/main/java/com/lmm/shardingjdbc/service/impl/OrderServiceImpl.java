package com.lmm.shardingjdbc.service.impl;

import com.lmm.shardingjdbc.dao.OrderDao;
import com.lmm.shardingjdbc.model.Order;
import com.lmm.shardingjdbc.model.OrderItem;
import com.lmm.shardingjdbc.service.OrderItemService;
import com.lmm.shardingjdbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/11/6.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemService orderItemService;
    @Override
    public void save(Order order) {
        orderDao.insert(order);
        //插入orderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(System.currentTimeMillis());
        orderItem.setOrderId(order.getOrderId());
        orderItem.setUserId(order.getUserId());
        orderItemService.save(orderItem);
    }
}
