package com.lmm.ignite;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Created by arno.yan on 2018/12/29.
 */
public class User implements Serializable {
    
    @QuerySqlField
    private Long id;

    @QuerySqlField
    private String name;

    @QuerySqlField
    private String idCard;

    @QuerySqlField
    private String school;

    @QuerySqlField
    private Integer age;

    public User(Long id, String name, String idCard, String school, Integer age) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.school = school;
        this.age = age;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
