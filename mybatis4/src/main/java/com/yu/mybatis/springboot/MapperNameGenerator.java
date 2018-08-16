package com.yu.mybatis.springboot;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Yu On 2018/8/16
 * Description：
 **/
public class MapperNameGenerator implements BeanNameGenerator {
    Map<String, Integer> nameMap = new HashMap<>();

    @Override
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {
        String shortClassName = ClassUtils.getShortName(beanDefinition.getBeanClassName());

        String beanName = Introspector.decapitalize(shortClassName);

        if (nameMap.containsKey(beanName)) {
            int index = nameMap.get(beanName) + 1;
            nameMap.put(beanName, index);
            beanName += index;
        } else {
            nameMap.put(beanName, 1);
        }
        return beanName;
    }
}
