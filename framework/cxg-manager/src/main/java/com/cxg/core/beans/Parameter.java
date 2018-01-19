package com.cxg.core.beans;

/**
 * @ClassName: Parameter
 * @Description: POJO字段封装类
 * @author haipeng
 * @date 2017年1月8日
 */
public class Parameter {

	private String file;

	private Object value;

	public Parameter() {
	}

	public Parameter(String file, Object value) {
		this.file = file;
		this.value = value;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
