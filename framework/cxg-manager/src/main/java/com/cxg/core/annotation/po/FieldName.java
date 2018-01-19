package com.cxg.core.annotation.po;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @ClassName: FieldName
* @Description: 实体类字段约束注解
 * 标有此注解的字段对应数据库中的字段名强制约束为该注解中的name值
* @author haipeng
* @date 2017年1月2日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldName {

	String name() default PUBVALUE.FIELD_NAME_DEFAUL_VALUE;
}
