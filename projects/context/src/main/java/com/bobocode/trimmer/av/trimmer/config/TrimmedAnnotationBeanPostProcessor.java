package com.bobocode.trimmer.av.trimmer.config;

import com.bobocode.trimmer.av.trimmer.annotation.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.Arrays;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final var beanType = bean.getClass();

        if (beanType.isAnnotationPresent(Trimmed.class)) {
            final var enhancer = new Enhancer();
            enhancer.setSuperclass(beanType);

            final MethodInterceptor interceptor = (obj, method, args, methodProxy) -> {
                args = Arrays.stream(args).map(s -> s instanceof String ? ((String) s).trim() : s).toArray();

                var returnValue = methodProxy.invokeSuper(obj, args);
                return returnValue instanceof String ? ((String) returnValue).trim() : returnValue;
            };

            enhancer.setCallback(interceptor);
            return enhancer.create();
        }

        return bean;
    }
}
