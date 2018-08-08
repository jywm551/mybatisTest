package com.yu.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created By Yu On 2018/8/8
 * Description：
 **/
public class MyMapperProxy<T> implements InvocationHandler {

    private Class<T> mapperInterface;
    private SqlSession sqlSession;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return sqlSession.<T>selectList(
                mapperInterface.getCanonicalName() + "." + method.getName()
        );
    }

    public MyMapperProxy(Class<T> mapperInterface, SqlSession sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }
}
