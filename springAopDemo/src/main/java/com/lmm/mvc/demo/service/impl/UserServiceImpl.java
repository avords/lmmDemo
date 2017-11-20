package com.lmm.mvc.demo.service.impl;

import com.lmm.mvc.demo.model.User;
import com.lmm.mvc.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/15.
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User getUserById(Long userId) {
        User user = new User();
        user.setName("雷涛");
        user.setAge(30);
        user.setId("1234854654165415");
        logger.info("==============================getUserById execute=======================================");
        return user;
    }
}
