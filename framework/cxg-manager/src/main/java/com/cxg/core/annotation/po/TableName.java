package com.cxg.core.annotation.po;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @ClassName: TableName
* @Description: 标识一个实体类对应数据库表的表名
 * 拥有该注解的PO类执行增删改差的时候将优先使用该注解内的值
* @author haipeng
* @date 2017年1月2日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableName {
	String name();
}
