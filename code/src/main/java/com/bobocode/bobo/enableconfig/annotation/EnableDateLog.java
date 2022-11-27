package com.bobocode.bobo.enableconfig.annotation;

import com.bobocode.bobo.enableconfig.config.DateLogConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DateLogConfiguration.class)
//@Import(DateLogBeanPostProcessorConfig.class) // works too with @Component
public @interface EnableDateLog {
}
