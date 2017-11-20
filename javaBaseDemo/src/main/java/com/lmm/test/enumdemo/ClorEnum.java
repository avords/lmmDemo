package com.lmm.test.enumdemo;

/**
 * Created by Administrator on 2016/11/28.
 */
public enum  ClorEnum {
    BLANK("黑色"),WITCH("白色"),YELLOW("黄色"),RED("红色"),BLUE("蓝色"),ORANGE("橘色"),VIOLET("紫色");
    private String remark;

    ClorEnum(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
