package com.shgs.lodge.auth;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHeader {
    // name 和 value 互为别名,当只有一个参数时,可以省略 value,直接("xxx") 就可以了
    /**
     * Alias for {@link #name}.
     */
    @AliasFor("name")
    String value() default "";

    /**
     * The name of the request header to bind to.
     * @since 4.2
     */
    @AliasFor("value")
    String name() default "";

    // 默认情况下,如果请求头中缺少了指定的 name ,那么将会报错
    /**
     * Whether the header is required.
     * <p>Defaults to {@code true}, leading to an exception being thrown
     * if the header is missing in the request. Switch this to
     * {@code false} if you prefer a {@code null} value if the header is
     * not present in the request.
     * <p>Alternatively, provide a {@link #defaultValue}, which implicitly
     * sets this flag to {@code false}.
     */
    boolean required() default true;

    // 如果请求头中缺少了指定的 name ,那么会报错,可以使用 defaultValue 这个属性指定默认值,就可以避免报错
    // 如果请求头缺少指定 name ,该属性设置的值将会作为默认值,如果该属性不设置值,它有自己的默认值 DEFAULT_NONE
    /**
     * The default value to use as a fallback.
     * <p>Supplying a default value implicitly sets {@link #required} to
     * {@code false}.
     */
    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
