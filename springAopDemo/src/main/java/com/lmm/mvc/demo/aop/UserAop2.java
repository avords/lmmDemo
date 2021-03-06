package com.lmm.mvc.demo.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/11/15.
 */
public class UserAop2 implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UserAop2.class);
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        logger.info("============================UserAop2 start=====================================");
        String targetName = methodInvocation.getThis().getClass().getName();
        String methodName = methodInvocation.getMethod().getName();
        Object[] arguments = methodInvocation.getArguments();
        logger.info(targetName+":"+methodName+":"+ Arrays.toString(arguments));
        Object result = methodInvocation.proceed();
        logger.info("============================UserAop2 end=======================================");
        return result;
    }
}
