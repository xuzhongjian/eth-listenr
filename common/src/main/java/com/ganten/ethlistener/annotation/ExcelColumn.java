package com.ganten.ethlistener.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String name() default "";
    boolean need() default true;
}
