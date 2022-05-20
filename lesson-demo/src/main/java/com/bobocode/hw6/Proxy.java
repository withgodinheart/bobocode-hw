package com.bobocode.hw6;

import lombok.NoArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class Proxy {

    public static void main(String[] args) {
        final GreetingService helloService = createMethodLoggingProxy(GreetingService.class);
        helloService.hello(); // logs nothing to the console
        helloService.gloryToUkraine(); // logs method invocation to the console
    }

    @NoArgsConstructor
    private static class GreetingService {

        @LogInvocation
        void gloryToUkraine() {
            System.out.println("Glory to Ukraine!");
        }

        void hello() {
            System.out.println("Hello!");
        }
    }

    /**
     * Creates a proxy of the provided class that logs its method invocations. If a method that
     * is marked with {@link LogInvocation} annotation is invoked, it prints to the console the following statement:
     * "[PROXY: Calling method '%s' of the class '%s']%n", where the params are method and class names accordingly.
     *
     * @param targetClass a class that is extended with proxy
     * @param <T>         target class type parameter
     * @return an instance of a proxy class
     */
    public static <T> T createMethodLoggingProxy(Class<T> targetClass) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        final MethodInterceptor methodInterceptor = (obj, method, args, proxy) -> {
            if (method.isAnnotationPresent(LogInvocation.class)) {
                System.out.printf("[PROXY: Calling method '%s' of the class '%s']%n", method.getName(), targetClass.getSimpleName());
            }
            return proxy.invokeSuper(obj, args);
        };
        enhancer.setCallback(methodInterceptor);

        return (T) enhancer.create();
    }
}
