package com.lmm.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName="districtarea",type="area",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class Area implements Serializable {

    private static final long serialVersionUID = -4806864866448058212L;
    
    @Id
    private String code;
    
    private String cityCode;
    
    private String name;

    public Area() {
    }

    public Area(Long id, String code, String cityCode, String name) {
        this.code = code;
        this.cityCode = cityCode;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setName(String name) {
        this.name = name;
    }
}
