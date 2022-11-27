package com.bobocode.bobo;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Proxy {

    public static void main(String[] args) {
//        var proxy = createProxy(PrintInterface.class);
        var proxy = createProxy(PrintService.class);
        proxy.greeting();
        proxy.info();
    }

    private static <T> T createProxy(Class<T> target) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(target);

        MethodInterceptor interceptor = (obj, method, args, proxy) -> {
            if (method.isAnnotationPresent(LogAnnotation.class)) {
                System.out.printf("[PROXY: Calling method '%s' of the class '%s']%n", method.getName(), target.getSimpleName());
            }
            return proxy.invokeSuper(obj, args);
        };

        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }

    public interface PrintInterface {

        @LogAnnotation("greeting")
        void greeting();

        void info();
    }

    @NoArgsConstructor
    @ToString
    private static class PrintService {

        @LogAnnotation("greeting")
        public void greeting() {
            System.out.println("Hello");
        }

        public void info() {
            System.out.println("Some info");
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface LogAnnotation {
        String value() default "";
    }
}
