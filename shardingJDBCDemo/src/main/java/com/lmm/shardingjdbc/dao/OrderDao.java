package com.lmm.shardingjdbc.dao;

import com.lmm.shardingjdbc.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}