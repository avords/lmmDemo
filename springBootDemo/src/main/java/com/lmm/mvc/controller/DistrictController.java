package com.lmm.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.lmm.data.Area;
import com.lmm.data.AreaRepository;
import com.lmm.data.City;
import com.lmm.data.CityRepository;
import com.lmm.data.Province;
import com.lmm.data.ProvinceRepository;
import com.lmm.mvc.vo.SearchVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/district")
public class DistrictController {
    @Autowired
    private AreaRepository areaRepository;
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private ProvinceRepository provinceRepository;
    
    @RequestMapping("/areaAdd")
    public String areaAdd(){
        String areaJson = getJson("areas.txt");
        if(StringUtils.isNotBlank(areaJson)){
            List<Area> areaList = JSON.parseArray(areaJson, Area.class);
            areaList.forEach(area -> {
                areaRepository.save(area);
            });
        }
        return "success";
    }

    @RequestMapping("/cityAdd")
    public String cityAdd(){
        String areaJson = getJson("cities.txt");
        if(StringUtils.isNotBlank(areaJson)){
            List<City> areaList = JSON.parseArray(areaJson, City.class);
            areaList.forEach(area -> {
                cityRepository.save(area);
            });
        }
        return "success";
    }

    @RequestMapping("/provinceAdd")
    public String provinceAdd(){
        String areaJson = getJson("provinces.txt");
        if(StringUtils.isNotBlank(areaJson)){
            List<Province> areaList = JSON.parseArray(areaJson, Province.class);
            areaList.forEach(area -> {
                provinceRepository.save(area);
            });
        }
        return "success";
    }
    
    private String getJson(String fileName){
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource(fileName).getPath()));
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("/test")
    public Map test(SearchVo vo,
                    @RequestParam Integer pageNum,
                    @RequestParam Integer pageSize,
                    @RequestParam double amount){
        
        Map<String,Object> map = new HashMap<>();
        
        map.put("searchVo", vo);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("amount", amount);
        return map;
    }

    public static void main(String[] args) {
        String str = "afdsfdsf";
        System.out.println(Arrays.toString(str.split(";")));
    }
}
