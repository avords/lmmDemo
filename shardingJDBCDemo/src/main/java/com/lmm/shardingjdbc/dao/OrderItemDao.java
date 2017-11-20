package com.lmm.shardingjdbc.dao;


import com.lmm.shardingjdbc.model.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao {
    int deleteByPrimaryKey(Integer itemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}