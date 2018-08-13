package com.yu.mybatis.simple.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created By Yu On 2018/8/13
 * Description：
 **/
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
)
@SuppressWarnings({"unchecked", "rawtypes"})
public class PageInterceptor implements Interceptor {

    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<>();
    private Dialect dialect;
    private Field additionalParameterField;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 截取拦截方法的参数
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        // 调用方法判断是否需要分页，如果不需要，直接放回结果
        if (!dialect.skip(ms.getId(), parameterObject, rowBounds)) {
            ResultHandler resultHandler = (ResultHandler) args[3];
            // 当前的目标对象
            Executor executor = (Executor) invocation.getTarget();
            BoundSql boundSql = ms.getBoundSql(parameterObject);
            // 反射获取动态参数
            Map<String, Object> additionalParameters = (Map<String, Object>) additionalParameterField.get(boundSql);
            // 判断是否需要进行count查询
            if (dialect.beforeCount(ms.getId(), parameterObject, rowBounds)) {
                // 根据当前的ms创建一个返回值为Long类型的ms
                MappedStatement countMs = newMappedStatement(ms, Long.class);
                // 创建count 查询的缓存key
                CacheKey countKey = executor.createCacheKey(countMs, parameterObject, RowBounds.DEFAULT, boundSql);
                // 调用方言获取count sql
                String countSql = dialect.getCountSql(boundSql, parameterObject, rowBounds, countKey);
                BoundSql countBoundSql = new BoundSql(
                        ms.getConfiguration(),
                        countSql,
                        boundSql.getParameterMappings(),
                        parameterObject);
                // 当使用动态SQL时，可能会产生临时的参数
                // 这些参数需要手动设置到新的BoundSql中
                for (String key : additionalParameters.keySet()) {
                    countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                }
                // 执行count 查询
                Object countResultList = executor.query(
                        countMs,
                        parameterObject,
                        RowBounds.DEFAULT,
                        resultHandler,
                        countKey,
                        countBoundSql);
                Long count = (Long) ((List) countResultList).get(0);
                // 处理查询总数
                dialect.afterCount(count, parameterObject, rowBounds);
                if (count == 0L) {
                    // 当查询总数为0的时候，直接返回空的结果
                    return dialect.afterPage(new ArrayList(), parameterObject, rowBounds);
                }
            }

            //判断是否需要进行分页查询
            if (dialect.beforePage(ms.getId(), parameterObject, rowBounds)) {
                // 生成分页的缓存key
                CacheKey pageKey = executor.createCacheKey(ms, parameterObject, rowBounds, boundSql);
                //调用方言获取分页sql
                String pageSql = dialect.getPageSql(boundSql, parameterObject, rowBounds, pageKey);
                BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(),
                        pageSql,
                        boundSql.getParameterMappings(),
                        parameterObject);

                // 设置动态参数
                for (String key : additionalParameters.keySet()) {
                    pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                }
                // 执行分页查询
                List result = executor.query(
                        ms,
                        parameterObject,
                        RowBounds.DEFAULT,
                        resultHandler,
                        pageKey,
                        pageBoundSql);
                return dialect.afterPage(result, parameterObject, rowBounds);
            }
        }
        return invocation.proceed();
    }

    private MappedStatement newMappedStatement(MappedStatement ms, Class<?> resultType) {
        MappedStatement.Builder builder = new MappedStatement.Builder(
                ms.getConfiguration(),
                ms.getId() + "_Count",
                ms.getSqlSource(),
                ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(".");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        // Count 查询返回值int
        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap resultMap = new ResultMap.Builder(
                ms.getConfiguration(),
                ms.getId(),
                resultType,
                EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String dialectClass = properties.getProperty("dialect");
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("使用PageInterceptor分页插件是，必须设置dialect属性");
        }
        dialect.setProperties(properties);
        try {
            // 反射获取BoundSql中的additionalParameters属性
            additionalParameterField = BoundSql.class.getDeclaredField("additionalParameters");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }
}