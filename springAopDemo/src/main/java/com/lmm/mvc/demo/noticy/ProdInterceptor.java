package com.lmm.mvc.demo.noticy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
		MappedStatement.class, Object.class }) })
public class ProdInterceptor implements Interceptor,ApplicationContextAware {
	private static ApplicationContext applicationContext;
	
	private static final Log LOG = LogFactory.getLog(ProdInterceptor.class);
	
	//拦截的表名
	private static final String[] INTERCEPT_TABLES = new String[] {
		"PROD_PRODUCT","SUPP_GOODS"
	};
	//产品表获取的参数名称
	private static final String PARAM_PRODUCT_ID = "productId";
	private static final String PARAM_BIZ_CATEGORY_ID = "bizCategoryId";
	private static final String PARAM_CATEGORY_ID = "categoryId";
	//商品表获取的参数名称
	private static final String SUPP_GOODS_ID = "suppGoodsId";
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object result= invocation.proceed();
		//1、获取传参
		MappedStatement mappedStatement=(MappedStatement)invocation.getArgs()[0];
		Object paramObject=invocation.getArgs()[1];
		BoundSql boundSql=null;
		String sql=null;
		if(mappedStatement!=null){
			boundSql = mappedStatement.getBoundSql(paramObject);
			sql = boundSql.getSql();			
		}
		//2、校验是否是 新增、修改、删除操作
		if(ProdSqlAnalyze.isWrite(sql)){
			//3、判断是否是相应的表
			String pureTableName = ProdSqlAnalyze.getPureTableName(ProdSqlAnalyze.getWrittenTable(sql));
			if(Arrays.asList(INTERCEPT_TABLES).contains(pureTableName)){
				
			}
		}
		return result;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
	}
}
