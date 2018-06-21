package com.lmm.mvc.vo;

import java.io.Serializable;

public class SearchVo implements Serializable {

    private static final long serialVersionUID = -1078132017747508906L;
    
    private String name;
    
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
