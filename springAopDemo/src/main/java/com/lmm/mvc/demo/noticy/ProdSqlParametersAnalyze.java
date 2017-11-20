package com.lmm.mvc.demo.noticy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdSqlParametersAnalyze {
	
	private static final Log LOG = LogFactory.getLog(ProdSqlParametersAnalyze.class);
	
	/**
	 * 根据一个参数名获取值(获取第一个匹配到的值)    
	 * @param paramName
	 * @param mappedStatement
	 * @param boundSql
	 * @return
	 */
	public static Object getValueByParamName(String paramName, MappedStatement mappedStatement, BoundSql boundSql){
		Object value = null;
		Object parameterObject = boundSql.getParameterObject();
		Configuration configuration = mappedStatement.getConfiguration();
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	    if (parameterMappings != null) {
	      for (int i = 0; i < parameterMappings.size(); i++) {
	        ParameterMapping parameterMapping = parameterMappings.get(i);
	        if (parameterMapping.getMode() != ParameterMode.OUT) {
	          String propertyName = parameterMapping.getProperty();
	          //获取指定参数名称的值
	          if(paramName.equalsIgnoreCase(propertyName)){
	        	  if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
	  	            value = boundSql.getAdditionalParameter(propertyName);
	  	          } else if (parameterObject == null) {
	  	            value = null;
	  	          } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
	  	            value = parameterObject;
	  	          } else {
	  	            MetaObject metaObject = configuration.newMetaObject(parameterObject);
	  	            value = metaObject.getValue(propertyName);
	  	          }
	        	  break;
	          }
	        }
	      }
	    }
//		if(value==null){
//			LOG.warn("SqlParametersAnalyze.getValueByParamName WARN:paramName="+paramName+",Sql="+boundSql.getSql()+",parameterJSON="+JSONObject.toJSONString(parameterObject));
//		}
		return value;
	}
	
	/**
	 * 根据多个参数名获取值    
	 * @param paramNameList
	 * @param mappedStatement
	 * @param boundSql
	 * @return
	 */
	public static Map<String,Object> getValueByParamNames(List<String> paramNameList, MappedStatement mappedStatement, BoundSql boundSql){
		Map<String,Object> paramNamesMap = new HashMap<String,Object>();
		
		
		Object parameterObject = boundSql.getParameterObject();
		Configuration configuration = mappedStatement.getConfiguration();
		TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	    if (parameterMappings != null) {
	      for (int i = 0; i < parameterMappings.size(); i++) {
	        ParameterMapping parameterMapping = parameterMappings.get(i);
	        if (parameterMapping.getMode() != ParameterMode.OUT) {
	          String propertyName = parameterMapping.getProperty();
	          //获取指定参数名称的值
	          if(paramNameList.contains(propertyName)){
	        	  Object value = null;
	        	  if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
	  	            value = boundSql.getAdditionalParameter(propertyName);
	  	          } else if (parameterObject == null) {
	  	            value = null;
	  	          } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
	  	            value = parameterObject;
	  	          } else {
	  	            MetaObject metaObject = configuration.newMetaObject(parameterObject);
	  	            value = metaObject.getValue(propertyName);
	  	          }
	        	  paramNamesMap.put(propertyName, value);
	          }
	        }
	      }
	    }
		return paramNamesMap;
	}

}
