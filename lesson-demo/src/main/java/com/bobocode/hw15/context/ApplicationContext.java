package com.bobocode.hw15.context;

import com.bobocode.hw15.context.exception.NoSuchBeanException;
import com.bobocode.hw15.context.exception.NoUniqueBeanException;

import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException;

    <T> T getBean(String beanName, Class<T> beanType) throws NoSuchBeanException;

    <T> Map<String, T> getAllBeans(Class<T> beanType);
}
