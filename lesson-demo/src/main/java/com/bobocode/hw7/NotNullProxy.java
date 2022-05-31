package com.bobocode.hw7;

import lombok.NoArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Parameter;

public class NotNullProxy {

    public static void main(String[] args) {
        var proxy = createProxy(GreetingService.class);
        proxy.hello("Ivan");
        proxy.hello(null);
    }

    @NoArgsConstructor
    private static class GreetingService {
        void hello(@NotNull String name) {
            System.out.println("Hello, " + name);
        }
    }

    private static <T> T createProxy(Class<T> targetClass) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        final MethodInterceptor methodInterceptor = (obj, method, args, proxy) -> {
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(NotNull.class) && args[i] == null) {
                    throw new NullPointerException();
                }
            }

            return proxy.invokeSuper(obj, args);
        };
        enhancer.setCallback(methodInterceptor);

        return (T) enhancer.create();
    }
}
