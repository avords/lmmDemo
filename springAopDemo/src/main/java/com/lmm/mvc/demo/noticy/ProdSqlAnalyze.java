package com.lmm.mvc.demo.noticy;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class ProdSqlAnalyze {
	private static final String SCHEMA_SEPRATOR = "\\.";
	private static final Logger LOG = Logger.getLogger(ProdSqlAnalyze.class);

	public static boolean isWrite(String sql) {
		boolean isWrite = false;
		try {
			isWrite = (isUpdate(sql) || isInsert(sql) || isDelete(sql));
		} catch (Exception e) {
			LOG.error("isWrite error, sql:" + sql, e);
		}
		return isWrite;
	}

	// 封装获取 表名
	public static String getWrittenTable(String sql) {
		String tableNamesStr = null;
		try {
			if (!isWrite(sql)) {
				return null;
			}
			//换行符
			sql = sql.replace("\n\t", " ");
			sql = sql.toUpperCase().trim();

			if (isUpdate(sql)) {
				tableNamesStr = getUpdatTable(sql);
			} else if (isInsert(sql)) {
				tableNamesStr = getInsertTable(sql);
			} else if (isDelete(sql)) {
				tableNamesStr = getDeleteTable(sql);
			}
		} catch (Exception e) {
			LOG.error("getWrittenTable error, sql:" + sql, e);
		}

		if (tableNamesStr == null) {
			return null;
		} else {
			return tableNamesStr.trim();
		}
	}

	public static String getSchema(String tableNameWithSchema) {
		if (StringUtils.isEmpty(tableNameWithSchema)) {
			return null;
		}

		String[] schemaAndTable = tableNameWithSchema.split(SCHEMA_SEPRATOR);
		if (schemaAndTable.length != 2) {
			return null;
		} else {
			return schemaAndTable[0];
		}
	}

	public static String getPureTableName(String tableNameWithSchema) {
		if (StringUtils.isEmpty(tableNameWithSchema)) {
			return null;
		}

		String[] schemaAndTable = tableNameWithSchema.split(SCHEMA_SEPRATOR);
		if (schemaAndTable.length == 1) {
			return schemaAndTable[0];
		} else if (schemaAndTable.length == 2) {
			return schemaAndTable[1];
		} else {
			return null;
		}
	}

	private static String getDeleteTable(String sql) {
		sql = sql.toUpperCase().trim();

		String tmp = StringUtils.substringAfter(sql, "FROM").trim();

		String tableNamesStr;
		tableNamesStr = StringUtils.substringBefore(tmp, " ");
		if (StringUtils.isEmpty(tableNamesStr)) {
			// 没有where条件时
			tableNamesStr = tmp;
		}
		return tableNamesStr;
	}

	public static String getInsertTable(String sql) {
		sql = sql.toUpperCase().trim();

		String tmp = StringUtils.substringAfter(sql, "INTO").trim();
		
		String s1 = StringUtils.substringBefore(tmp, "(");
		String s2 = StringUtils.substringBefore(tmp, " ");
		return getShortestStr(Arrays.asList(new String[] { s1, s2 }));
	}

	private static String getUpdatTable(String sql) {
		sql = sql.toUpperCase().trim();

		String tmp = StringUtils.substringAfter(sql, "UPDATE").trim();
		return StringUtils.substringBefore(tmp, " ");
	}

	public static boolean isUpdate(String sql) {
		return (sql != null && (sql.trim().toUpperCase().startsWith("UPDATE")));
	}

	public static boolean isInsert(String sql) {
		return (sql != null && (sql.trim().toUpperCase().startsWith("INSERT")));
	}

	public static boolean isDelete(String sql) {
		return (sql != null && (sql.trim().toUpperCase().startsWith("DELETE")));
	}

	//insert into aaa(xx,yy) values(xx,yy)
	//insert into aa values (xx,yy,zz)
	private static String getShortestStr(List<String> list) {
		String tmp = null;
		for (String s : list) {
			if (s == null || s.length() == 0) {
				continue;
			}

			if (tmp == null || tmp.length() > s.length()) {
				tmp = s;
			}
		}

		return tmp;
	}

	public static void main(String args[]) throws Exception {
		String[] sqls = new String[] {
				" update lmm_super.mybatis_test set name_='a' where id_=1       ",
				" update lmm_super.mybatis_test  set name_='a' where id_=1      ",
				" update lmm_super.mybatis_test t set t.name_='a' where id_=1   ",
				" update lmm_super.mybatis_test  t set t.name_='a' where id_=1  ",
				" insert into lmm_super.mybatis_test(id_,name_) values(1,'a')   ",
				" insert into lmm_super.mybatis_test (id_,name_) values(1,'a')  ",
				" insert into lmm_super.mybatis_test values(1,'a')              ",
				" insert into  lmm_super.mybatis_test t values(1,'a')           ",
				" insert into lmm_super.mybatis_test  values(1,'a')             ",
				" insert into lmm_super.mybatis_test select 1,'a' from dual     ",
				" delete from lmm_super.mybatis_test  t  where t.id_=1          ",
				" delete from lmm_super.mybatis_test  where id_=1               ",
				" delete   from  lmm_super.mybatis_test                            ",
				" delete  from  lmm_super.mybatis_test",
				" delete  from  mybatis_test " };
		for (String sql : sqls) {
			System.out.println(getSchema(getWrittenTable(sql)) + "****"
					+ getPureTableName(getWrittenTable(sql))+"****"+sql);
		}
	}

}
