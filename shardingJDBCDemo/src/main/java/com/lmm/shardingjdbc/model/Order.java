package com.lmm.shardingjdbc.model;

import java.io.Serializable;

public class Order implements Serializable {
    private Long orderId;

    private Long userId;

    private static final long serialVersionUID = 1L;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}