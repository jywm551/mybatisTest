package com.yu.mybatis.simple.model;

/**
 * Created By Yu On 2018/8/2
 * Description：
 **/

public class Country {
    private Long id;
    private String country_name;
    private String country_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}
