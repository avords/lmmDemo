package com.lmm.dbDemo.utils;

public class DBPaginationUtils {
    public static String getOracleSQL(String sql,int page,int pageSize) {
        StringBuilder result = new StringBuilder(sql);
        int start = (page-1)*pageSize+1;
        int end = start+pageSize-1;
        result.append("select * from (select  t.*,rownum rn from (").append(sql).append(") t)  rn between "+start+" and "+end);
        return result.toString();
    }
    public static String getMysqlSQL(String sql,int page,int pageSize) {
        StringBuilder result = new StringBuilder(sql);
        int start = (page-1)*pageSize;
        result.append("select  t.* from (").append(sql).append(") t limit "+start+","+pageSize+"");
        return result.toString();
    }
}
