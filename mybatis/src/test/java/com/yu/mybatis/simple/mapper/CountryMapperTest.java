package com.yu.mybatis.simple.mapper;

import com.yu.mybatis.simple.model.Country;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created By Yu On 2018/8/2
 * Description：
 **/
public class CountryMapperTest extends BaseMapperTest {


    @Test
    public void testSelectAll() {

        try (SqlSession session = getSqlSessionFactory()) {
            List<Country> countryList = session
                    .selectList("com.yu.mybatis.simple.mapper.CountryMapper.selectAll");
            printCountryList(countryList);
        }
    }

    private void printCountryList(List<Country> countryList) {
        for (Country country : countryList) {
            System.out.printf("%-4d%4s%4s\n",
                    country.getId(),
                    country.getCountryName(),
                    country.getCountryCode());
        }
    }


}
