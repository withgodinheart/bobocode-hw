package com.bobocode.hw15.av;

import com.bobocode.hw15.yv.context.exception.NoSuchBeanException;
import com.bobocode.hw15.yv.context.exception.NoUniqueBeanException;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> ctx = new HashMap<>();

    public ApplicationContextImpl(String packageName) {
        initiateBean(packageName);
    }

    @Override
    public <T> T getBean(final Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        var list = ctx.values().stream()
                .filter(beanType::isInstance)
                .toList();

        if (list.size() > 1) throw new NoUniqueBeanException();
        else if (list.isEmpty()) throw new NoSuchBeanException();

        return beanType.cast(list.get(0));
    }

    @Override
    public <T> T getBean(final String name, final Class<T> beanType) throws NoSuchBeanException {
        return Optional.ofNullable(beanType.cast(ctx.get(name)))
                .orElseThrow(NoSuchBeanException::new);
    }

    @Override
    public <T> Map<String, T> getAllBeans(final Class<T> beanType) {
        return ctx.entrySet().stream()
                .filter(beanType::isInstance)
                // ? ASK .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }

    @SneakyThrows
    private void initiateBean(final String packageName) {
        // 1. scan packages
        final var reflections = new Reflections(packageName);
        // 2. check annotation
        final var targetClasses = reflections.getTypesAnnotatedWith(BoboBean.class);
        // 3. create instances
        for (var targetClass : targetClasses) {
            var instance = targetClass.getConstructor().newInstance();
            // 4. resolve bean name
            var annotationValue = targetClass.getAnnotation(BoboBean.class).value();
            var simpleName = targetClass.getSimpleName().substring(0, 1).toLowerCase() +
                    targetClass.getSimpleName().substring(1);
            var beanName = annotationValue.isBlank() ? simpleName : annotationValue;
            // 5. store bean in ctx
            ctx.put(beanName, instance);
        }
    }
}
