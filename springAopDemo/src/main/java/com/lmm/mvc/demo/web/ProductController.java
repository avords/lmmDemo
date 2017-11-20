package com.lmm.mvc.demo.web;

import com.lmm.mvc.demo.model.ProdProduct;
import com.lmm.mvc.demo.service.ProductService;
import com.lmm.mvc.demo.util.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/11/25.
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @RequestMapping("getProduct/{productId}")
    @ResponseBody
    public ProdProduct getProduct(@PathVariable Long productId){
        ApplicationContext applicationContext = BeanFactory.getApplicationContext();
        ProductService productService = (ProductService) applicationContext.getBean("productServiceImpl");
        return productService.getByProductId(productId);
    }

    @RequestMapping("updateName")
    @ResponseBody
    public Integer getProduct(String productName,Long productId){
        int i =  productService.updateNameByProductId(productName,productId);
        return i;
    }
}
