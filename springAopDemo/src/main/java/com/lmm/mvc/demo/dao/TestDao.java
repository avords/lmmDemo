package com.lmm.mvc.demo.dao;

import com.lmm.mvc.demo.model.Test;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/12/14.
 */
@Repository
public interface TestDao {
    void saveTest(Test test);
}
