package com.ganten.ethlistener.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CSVColumn {
    int order(); // 指定 CSV 中的顺序
}
