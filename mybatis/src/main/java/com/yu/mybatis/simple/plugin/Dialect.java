package com.yu.mybatis.simple.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * Created By Yu On 2018/8/13
 * Description：
 **/
@SuppressWarnings({"rawtypes"})
public interface Dialect {
    /**
     * 跳过count和分页查询
     *
     * @param msId            执行的Mybatis方法全名
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @return true 跳过，范湖默认查询结果，false则执行分页查询
     */
    boolean skip(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * 执行分页前，返回true则会进行count查询，返回false会继续下面的beforeCount
     *
     * @param msId            执行的Mybatis方法全名
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @return
     */
    boolean beforeCount(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * 生成count查询sql
     *
     * @param boundSql        绑定sql对象
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @param countKey        count缓存Key
     * @return
     */
    String getCountSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey);

    /**
     * 执行完count查询后
     *
     * @param count           查询结果总数
     * @param parameterObject 几口参数
     * @param rowBounds       分页参数
     */
    void afterCount(long count, Object parameterObject, RowBounds rowBounds);

    /**
     * 执行分页前，返回true会进行分页查询，返回false会返回默认查询结果
     *
     * @param msId            执行的Mybatis方法全名
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @return
     */
    boolean beforePage(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * 生成分页查询sql
     *
     * @param boundSql        绑定sql对象
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @param pageKey         分页缓存Key
     * @return
     */
    String getPageSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey pageKey);

    /**
     * 分页查询后，处理结果分页，拦截器中直接return 该方法的返回值
     *
     * @param pageList        分页查询结果
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @return
     */
    Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds);

    /**
     * 设置参数
     *
     * @param properties 插件属性
     */
    void setProperties(Properties properties);
}
