package com.lmm.shardingjdbc.web;

import com.lmm.shardingjdbc.model.Order;
import com.lmm.shardingjdbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/**
 * Created by Administrator on 2017/11/6.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @RequestMapping("/testShardingJDBC")
    @ResponseBody
    public String testShardingJDBC(){
        Random random = new Random();
        for(Long orderId=1l;orderId<=1000;orderId++){
            Order order = new Order();
            order.setOrderId(orderId);
            order.setUserId(Long.valueOf(random.nextInt(1024)));
            orderService.save(order);
        }
        return "成功";
    }
}
