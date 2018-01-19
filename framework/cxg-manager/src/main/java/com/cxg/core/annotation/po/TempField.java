package com.cxg.core.annotation.po;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @ClassName: TempField
* @Description: 标识某个字段不录入数据库中
 * 标识该注解的字段将没有增删改查的功能。
* @author haipeng
* @date 2017年1月1日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TempField {

	 
}
