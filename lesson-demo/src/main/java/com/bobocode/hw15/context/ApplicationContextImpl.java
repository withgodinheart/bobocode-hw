package com.bobocode.hw15.context;

import com.bobocode.hw15.context.exception.NoSuchBeanException;
import com.bobocode.hw15.context.exception.NoUniqueBeanException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationContextImpl implements ApplicationContext {

    private Map<String, Object> applicationContext = new HashMap<>();

    public ApplicationContextImpl(String packageName) {
        initiateContext(packageName);
    }

    @Override
    public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        return getBeanByType(beanType);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) throws NoSuchBeanException {
        return Optional.ofNullable(beanType.cast(applicationContext.get(beanName)))
                .orElseThrow(() -> new NoSuchBeanException("Bean '" + beanName + "' was not found"));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        return applicationContext.entrySet()
                .stream()
                .filter(entry -> checkIsBeanAssignableFromType(entry.getValue(), beanType))
                .collect(Collectors.toMap((Map.Entry::getKey), entry -> beanType.cast(entry.getValue())));
    }

    private <T> T getBeanByType(Class<T> beanType) {
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

    private void initiateContext(String packageName) {
        var reflections = new Reflections(packageName);
        var beans = reflections.getTypesAnnotatedWith(Bean.class);

        try {
            for (var bean : beans) {
                var constructor = bean.getConstructor();
                var instance = constructor.newInstance();
                var annotationValue = bean.getAnnotation(Bean.class).value();
                var beanName = annotationValue.isBlank()
                        ? transformBeanNameFromType(bean)
                        : annotationValue;
                applicationContext.put(beanName, instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        injectFields();
    }

    private void injectFields() {
        applicationContext.values()
                .forEach(bean -> Arrays.stream(bean.getClass().getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(Inject.class))
                        .forEach(field -> {
                            var beanType = field.getType();
                            var beanObj = getBeanByType(beanType);
                            if (Objects.nonNull(beanObj)) {
                                var isPrivate = Modifier.isPrivate(field.getModifiers());
                                if (isPrivate) {
                                    field.setAccessible(true);
                                }
                                try {
                                    field.set(bean, beanType.cast(beanObj));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (isPrivate) {
                                        field.setAccessible(false);
                                    }
                                }
                            }
                        }));
    }

    private String transformBeanNameFromType(Class<?> type) {
        var simpleBeanName = type.getSimpleName();
        return Character.toLowerCase(simpleBeanName.charAt(0)) + simpleBeanName.substring(1);
    }

    private boolean checkIsBeanAssignableFromType(Object bean, Class<?> type) {
        var exactClass = bean.getClass().isAssignableFrom(type);
        var implementInterface = Stream.of(bean.getClass().getInterfaces()).anyMatch(type::isAssignableFrom);
        return exactClass || implementInterface;
    }

}
