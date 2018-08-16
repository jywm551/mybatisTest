package com.yu.mybatis.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.yu.mybatis.springboot.model.Country;

import java.util.List;

/**
 * Created By Yu On 2018/8/16
 * Description：
 **/
@Mapper
public interface CountryMapper {
    List<Country> selectAll();
}
