package com.codinglitch.lexiconfig.annotations;

import com.codinglitch.lexiconfig.LexiconfigApi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Lexicon {
    String name() default "";
    LexiconfigApi.Location location() default LexiconfigApi.Location.CONFIG;
    LexiconfigApi.Extension extension() default LexiconfigApi.Extension.TOML;
}
