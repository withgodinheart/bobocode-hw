package com.bobocode.hw15.av;

import com.bobocode.hw15.yv.context.exception.NoSuchBeanException;
import com.bobocode.hw15.yv.context.exception.NoUniqueBeanException;

import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(final Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException;

    <T> T getBean(final String name, final Class<T> beanType) throws NoSuchBeanException;

    <T> Map<String, T> getAllBeans(final Class<T> beanType);
}
