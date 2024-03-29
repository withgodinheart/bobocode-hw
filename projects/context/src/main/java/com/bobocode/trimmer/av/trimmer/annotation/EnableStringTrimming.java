package com.bobocode.trimmer.av.trimmer.annotation;

import com.bobocode.trimmer.av.trimmer.config.StringTrimmingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(StringTrimmingConfiguration.class)
public @interface EnableStringTrimming {
}
