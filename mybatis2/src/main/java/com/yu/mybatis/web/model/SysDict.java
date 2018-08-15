package com.yu.mybatis.web.model;

import java.io.Serializable;

/**
 * Created By Yu On 2018/8/14
 * Description：
 **/
public class SysDict implements Serializable {
    private static final long serialVersionUID = -168230820656263372L;

    private Long id;
    private String code;
    private String name;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
