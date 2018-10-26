package com.lmm.test.serialize;

import java.io.Serializable;

/**
 * Created by arno.yan on 2018/9/19.
 */
public class User implements Serializable {
    private static final long serialVersionUID = -864394908436476003L;
    
    public String name;
    
    public String school;

    public User(String name, String school) {
        this.name = name;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
