package com.bobocode.trimmer.yv.bpp;

import com.bobocode.trimmer.yv.annotation.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        var beanType = bean.getClass();

        if (beanType.isAnnotationPresent(Trimmed.class)) {
            return processTrimmedAnnotationLogic(beanType);
        }

        return bean;
    }

    private Object processTrimmedAnnotationLogic(Class<?> beanType) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(beanType);

        var methodInterceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                var returnValue = methodProxy.invokeSuper(o, trimStringArgs(objects));
                return checkIfStringInstance(returnValue) ? trimValue(returnValue) : returnValue;
            }
        };

        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }

    private Object[] trimStringArgs(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (checkIfStringInstance(args[i])) {
                args[i] = trimValue(args[i]);
            }
        }
        return args;
    }

    private boolean checkIfStringInstance(Object obj) {
        return (obj instanceof String);
    }

    private Object trimValue(Object obj) {
        return ((String) obj).trim();
    }
}
