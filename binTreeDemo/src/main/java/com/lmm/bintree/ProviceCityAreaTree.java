package com.lmm.bintree;

/**
 * Created by arno.yan on 2018/12/25.
 */
public class ProviceCityAreaTree implements BaseTree{
    
    private Long parentId;
    
    private Long objectId;
    
    private String name;

    public ProviceCityAreaTree(Long parentId, Long objectId, String name) {
        this.parentId = parentId;
        this.objectId = objectId;
        this.name = name;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public Long getObjectId() {
        return objectId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
