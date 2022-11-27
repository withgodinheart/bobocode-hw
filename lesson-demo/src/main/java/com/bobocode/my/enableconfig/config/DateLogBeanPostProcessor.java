package com.bobocode.my.enableconfig.config;

import com.bobocode.my.enableconfig.annotation.Dated;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.time.LocalDateTime;

public class DateLogBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        var beanType = bean.getClass();
        if (beanType.isAnnotationPresent(Dated.class)) {
            var enhancer = new Enhancer();
            enhancer.setSuperclass(beanType);

            MethodInterceptor interceptor = (obj, method, args, proxy) -> {
                System.out.println("[" + LocalDateTime.now() + "]");
                return proxy.invokeSuper(obj, args);
            };

            enhancer.setCallback(interceptor);
            return beanType.cast(enhancer.create());
        }

        return bean;
    }
}
