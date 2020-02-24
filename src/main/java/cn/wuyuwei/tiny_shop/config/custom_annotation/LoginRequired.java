package cn.wuyuwei.tiny_shop.config.custom_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* @Target 标注可能出现在Java程序中出现的位置
* Target注解决定 UserLoginToken 注解可以加在哪些成分上，如加在类身上，或者属性身上，或者方法身上等成分
*
* RetentionPolicy 与@Retention元注解一起指定注释要保留多长时间（UserLoginToken 的生命周期）
* RUNTIME 表示在JVM运行时这个注解也存在
* */
/*
 * @Retention(RetentionPolicy.SOURCE)
 * 这个注解的意思是让MyAnnotation注解只在java源文件中存在，编译成.class文件后注解就不存在了
 * @Retention(RetentionPolicy.CLASS)
 * 这个注解的意思是让MyAnnotation注解在java源文件(.java文件)中存在，编译成.class文件后注解也还存在，
 * 被MyAnnotation注解类标识的类被类加载器加载到内存中后MyAnnotation注解就不存在了
 */

/**
 * @author wuyuwei
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    boolean required() default true;
}