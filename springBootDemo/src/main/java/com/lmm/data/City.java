package com.lmm.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
@Document(indexName="districtcity",type="city",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class City implements Serializable {

    private static final long serialVersionUID = 257059499473183045L;
    
    @Id
    private String code;
    
    private String name;
    
    private String provinceCode;

    public City() {
    }

    public City(Long id, String code, String name, String provinceCode) {
        this.code = code;
        this.name = name;
        this.provinceCode = provinceCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
