package com.yu.mybatis.simple.plugin;


import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.*;

/**
 * Created By Yu On 2018/8/13
 * Description：
 **/

@Intercepts(
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}
        )
)
@SuppressWarnings({"unchecked", "rawtypes"})
public class CamelHumpInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 先执行得到结果，再对结果进行处理
        List<Object> list = (List<Object>) invocation.proceed();
        for (Object o : list) {
            if (o instanceof Map) {
                processMap((Map) o);
            } else {
                break;
            }
        }
        return null;
    }

    private void processMap(Map<String, Object> map) {
        Set<String> keySet = new HashSet<>(map.keySet());
        for (String key : keySet) {
            // 将以大写开头的字符串转换成小写，如果包含下划线也会处理成驼峰
            // 此处只通过这两个简单的标识来判断是否进行转换
            if ((key.charAt(0) >= 'A' && key.charAt(0) <= 'Z') || key.contains("_")) {
                Object value = map.get(key);
                map.remove(key);
                map.put(underlineToCamelHump(key), value);
            }
        }
    }

    private String underlineToCamelHump(String inputString) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
            } else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
