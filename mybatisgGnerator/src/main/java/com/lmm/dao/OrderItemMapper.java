package com.lmm.dao;

import com.lmm.model.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}