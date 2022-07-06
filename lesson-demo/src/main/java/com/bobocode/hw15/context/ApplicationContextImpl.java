package com.bobocode.lesson15.hw15.context;

import com.bobocode.lesson15.hw15.context.exception.NoSuchBeanException;
import com.bobocode.lesson15.hw15.context.exception.NoUniqueBeanException;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationContextImpl implements ApplicationContext {

    private Map<String, Object> applicationContext = new HashMap<>();

    public ApplicationContextImpl(String packageName) {
        initiateContext(packageName);
    }

    @Override
    public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        var beans = applicationContext.values()
                .stream()
                .filter(bean -> checkIsBeanAssignableFromType(bean, beanType))
                .toList();

        if (beans.isEmpty()) {
            throw new NoSuchBeanException("Beans with type '" + beanType.getSimpleName() + "' was not found");
        }

        if (beans.size() > 1) {
            throw new NoUniqueBeanException("Several beans were found with the type '" + beanType.getSimpleName() + "'");
        }

        return beanType.cast(beans.get(0));
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) throws NoSuchBeanException {
        return Optional.ofNullable(beanType.cast(applicationContext.get(beanName)))
                .orElseThrow(() -> new NoSuchBeanException("Bean '" + beanName + "' was not found"));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return applicationContext.entrySet()
                .stream()
                .filter(entry -> checkIsBeanAssignableFromType(entry.getValue(), beanType))
                .collect(Collectors.toMap((Map.Entry::getKey), entry -> beanType.cast(entry.getValue())));
    }

    @SneakyThrows
    private void initiateContext(String packageName) {
        var reflections = new Reflections(packageName);
        var beans = reflections.getTypesAnnotatedWith(Bean.class);

        for (var bean : beans) {
            var constructor = bean.getConstructor();
            var instance = constructor.newInstance();
            var annotationValue = bean.getAnnotation(Bean.class).value();
            var beanSimpleName = bean.getSimpleName();
            var beanName = annotationValue.isBlank()
                    ? Character.toLowerCase(beanSimpleName.charAt(0)) + beanSimpleName.substring(1)
                    : annotationValue;
            applicationContext.put(beanName, instance);
        }
    }

    private boolean checkIsBeanAssignableFromType(Object bean, Class<?> type) {
        var exactClass = bean.getClass().isAssignableFrom(type);
        var implementInterface = Stream.of(bean.getClass().getInterfaces()).allMatch(type::isAssignableFrom);
        return exactClass || implementInterface;
    }

}
