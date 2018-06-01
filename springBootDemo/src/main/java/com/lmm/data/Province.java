package com.lmm.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName="districtprovince",type="province",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class Province implements Serializable {

    private static final long serialVersionUID = 3224516108102364839L;
    
    @Id
    private String code;
    
    private String name;

    public Province() {
    }

    public Province(Long id, String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
