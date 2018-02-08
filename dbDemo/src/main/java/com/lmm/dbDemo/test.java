package com.lmm.dbDemo;

import com.lmm.dbDemo.dao.BaseDao;

/**
 * Created by Administrator on 2016/12/26.
 */
public class test {
    public static void main(String[] args) throws Exception {
        BaseDao baseDao = new BaseDao();
        for (int i=1001;i<=2000;i++){
            String sql = "INSERT  INTO mycattest(ID,name,school,create_date) VALUES ('"+i+"','薛少飞','渭南师范',sysdate())";
            baseDao.insert(sql);
        }
    }
}
