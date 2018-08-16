package com.yu.mybatis.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.yu.mybatis.springboot.mapper.CountryMapper;

/**
 * Created By Yu On 2018/8/16
 * Description：
 **/
@SpringBootApplication
@MapperScan(value = {
        "com.yu.mybatis.springboot.mapper",
        "com.yu.mybatis.simple.mapper"},
        nameGenerator = MapperNameGenerator.class)

public class Application implements CommandLineRunner {

    @Autowired
    private CountryMapper countryMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        countryMapper.selectAll();
    }
}
