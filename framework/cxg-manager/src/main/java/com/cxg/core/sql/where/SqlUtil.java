package com.cxg.core.sql.where;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.cxg.core.annotation.po.FieldName;
import com.cxg.core.annotation.po.TableName;
import com.cxg.core.annotation.po.TempField;
import com.cxg.core.beans.Constraint;
import com.cxg.core.beans.Parameter;
import com.cxg.core.sql.exception.AiyiIdTypeException;


/**
 * @ClassName: SqlUtil
 * @Description: 反射工具类
 * @author haipeng
 * @date 2017年1月8日
 */
public class SqlUtil<T extends Constraint> {

	/**
	 * 获取实体类的某个字段
	 * 
	 * @param t
	 * @param fieldName
	 * @return
	 */
	public Field getField(Class<?> t, String fieldName) {
		Field[] fields = t.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 获取查询sql的字段列表
	 * 
	 * @param Constraint
	 * @return
	 */
	public List<Parameter> getParameterListOfSelect(Constraint Constraint) {
		List<Parameter> list = new ArrayList<>();
		Class<? extends Constraint> thisClass = Constraint.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		for (Field f : fields) {
			try {
				if (!f.isAnnotationPresent(TempField.class)) {
					String fName = f.getName();
					// 判断是否是boolean类型
					String get = "get";
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1
							|| fieldType.indexOf("Boolean") != -1) {
						get = "is";
					}
					if (f.isAnnotationPresent(FieldName.class)) {
						String fieldName = f.getAnnotation(FieldName.class)
								.name();
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, thisClass.getMethod(
								get + fName.substring(0, 1).toUpperCase()
										+ fName.substring(1)).invoke(Constraint));
						list.add(Parameter);
					} else {
						String fieldName = toTableString(fName);
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, thisClass.getMethod(
								get + fName.substring(0, 1).toUpperCase()
										+ fName.substring(1)).invoke(Constraint));
						list.add(Parameter);
					}
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获取实体类对应的表名
	 * 
	 * @param Constraint
	 * @return
	 */
	public String getTableName(Constraint Constraint) {
		Class<? extends Constraint> c = Constraint.getClass();
		if (c.isAnnotationPresent(TableName.class)) {
			return c.getAnnotation(TableName.class).name();
		} else {
			String className = Constraint.getClass().getSimpleName();
			String tName = toTableString(className);
			String ConstraintName = tName.substring(tName.length() - 2, tName.length());
			if ("Constraint".equals(ConstraintName)) {
				tName = tName.substring(0, tName.length() - 3);
			}
			return tName;
		}

	}

	

	
	public List<Parameter> getParameterList(T Constraint) {
		List<Parameter> list = new ArrayList<>();
		Class<? extends Constraint> thisClass = Constraint.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		try {
			for (Field f : fields) {
				if (!f.getName().equalsIgnoreCase("ID")
						&& !f.isAnnotationPresent(TempField.class)) {
					String fName = f.getName();

					// 判断是否是boolean类型
					String getf = "get";
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1
							|| fieldType.indexOf("Boolean") != -1) {
						getf = "is";
					}
					if (f.isAnnotationPresent(FieldName.class)) {
						String fieldName = f.getAnnotation(FieldName.class)
								.name();
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(Constraint);
						Parameter Parameter = new Parameter(fieldName, getValue);
						list.add(Parameter);
					} else {
						String fieldName = toTableString(fName);
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(Constraint);
						Parameter Parameter = new Parameter(fieldName, getValue);
						list.add(Parameter);
					}
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过Class获取生成对应Sql查询的字段
	 * 
	 * @param Constraint
	 * @return
	 */
	public List<Parameter> getParameterListOfSelect(Class<T> Constraint) {
		List<Parameter> list = new ArrayList<>();
		Class<? extends Constraint> thisClass = Constraint;
		Field[] fields = thisClass.getDeclaredFields();
		try {
			Object o = thisClass.newInstance();
			for (Field f : fields) {
				if (!f.isAnnotationPresent(TempField.class)) {
					String fName = f.getName();
					String getf = "get";
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1
							|| fieldType.indexOf("Boolean") != -1) {
						getf = "is";
					}
					if (f.isAnnotationPresent(FieldName.class)) {
						String fieldName = f.getAnnotation(FieldName.class)
								.name();
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(o);
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, getValue);
						list.add(Parameter);
					} else {
						String fieldName = toTableString(fName);
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(o);
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, getValue);
						list.add(Parameter);
					}
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过Class获取生成对应Sql查询的字段
	 * 
	 * @param Constraint
	 * @return
	 */
	public List<Parameter> getParameterListByBean(Class<T> Constraint) {
		List<Parameter> list = new ArrayList<>();
		Class<?> thisClass = Constraint;
		Field[] fields = thisClass.getDeclaredFields();
		try {
			Object o = thisClass.newInstance();
			for (Field f : fields) {
				if (!f.getName().equalsIgnoreCase("ID")
						&& !f.isAnnotationPresent(TempField.class)) {

					String fName = f.getName();

					String getf = "get";
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1
							|| fieldType.indexOf("Boolean") != -1) {
						getf = "is";
					}
					if (f.isAnnotationPresent(FieldName.class)) {
						String fieldName = f.getAnnotation(FieldName.class)
								.name();
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(o);
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, getValue);
						list.add(Parameter);
					} else {
						String fieldName = toTableString(fName);
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(o);
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, getValue);
						list.add(Parameter);
					}

				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过Class获取生成对应Sql查询的字段
	 * 
	 * @param Constraint
	 * @return
	 */
	public List<Parameter> getParameterListByBeanOfSelect(Class<T> Constraint) {
		List<Parameter> list = new ArrayList<>();
		Class<?> thisClass = Constraint;
		Field[] fields = thisClass.getDeclaredFields();
		try {
			Object o = thisClass.newInstance();
			for (Field f : fields) {
				if (!f.isAnnotationPresent(TempField.class)) {
					String fName = f.getName();
					String getf = "get";
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1
							|| fieldType.indexOf("Boolean") != -1) {
						getf = "is";
					}
					if (f.isAnnotationPresent(FieldName.class)) {
						String fieldName = f.getAnnotation(FieldName.class)
								.name();
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(o);
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, getValue);
						list.add(Parameter);
					} else {
						String fieldName = toTableString(fName);
						Method get = thisClass.getMethod(getf
								+ fName.substring(0, 1).toUpperCase()
								+ fName.substring(1));
						Object getValue = get.invoke(o);
						Parameter Parameter = new Parameter(fieldName + " as "
								+ fName, getValue);
						list.add(Parameter);
					}
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取Sql字段名
	 * 
	 * @param Constraint
	 * @return
	 */
	public String getTableName(Class<T> Constraint) {
		if (Constraint.isAnnotationPresent(TableName.class)) {
			return Constraint.getAnnotation(TableName.class).name();
		} else {
			String tName = toTableString(Constraint.getSimpleName());
			String ConstraintName = tName.substring(tName.length() - 2, tName.length());
			if ("Constraint".equals(ConstraintName)) {
				tName = tName.substring(0, tName.length() - 3);
			}
			return tName;
		}
	}
	
	/**
	 * 获取Sql字段名
	 * 
	 * @param Constraint
	 * @return
	 */
	public String getTableNameByBean(Class<T> Constraint) {
		if (Constraint.isAnnotationPresent(TableName.class)) {
			return Constraint.getAnnotation(TableName.class).name();
		} else {
			String tName = toTableString(Constraint.getSimpleName());
			if ("Constraint".equals(tName.substring(tName.length() - 3,
					tName.length() - 1))) {
				tName = tName.substring(0, tName.length() - 3);
			}
			return tName;
		}
	}

	/**
	 * 获取实体类中的某个值
	 * 
	 * @param Constraint
	 * @param fileName
	 * @return
	 */
	public static <T> Serializable getFileValue(Class<T> Constraint, String fileName) {
		try {
			Method method = Constraint.getMethod("get"
					+ fileName.substring(0, 1).toUpperCase()
					+ fileName.substring(1));
			Object o = Constraint.newInstance();
			Object invoke = method.invoke(o);
			return null == invoke ? null : (Serializable) invoke;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取字段名
	 * 
	 * @param Constraint
	 * @param fileName
	 * @return
	 */
	public Serializable getFileValue(Constraint Constraint, String fileName) {
		try {
			Class<? extends Constraint> cla = Constraint.getClass();
			Method method = cla.getMethod("get"
					+ fileName.substring(0, 1).toUpperCase()
					+ fileName.substring(1));
			Object o = Constraint;
			Object invoke = method.invoke(o);
			return null == invoke ? null : (Serializable) invoke;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将某个值反射强制赋给实体的属性
	 * 
	 * @param Constraint
	 * @param fileName
	 * @param fileValue
	 * @return
	 */
	public static boolean setFileValue(Constraint Constraint, String fileName,
			Serializable fileValue) {
		Class<? extends Constraint> thisClass = Constraint.getClass();
		try {
			if ("ID".equalsIgnoreCase(fileName)) {
				try {
					Field field = thisClass.getDeclaredField(fileName);
					String calssName = field.getType().getName();
					if (calssName.equals("int")
							|| calssName.equals("java.lang.Integer")) {
						if (Integer.MAX_VALUE > new Integer("" + fileValue)) {
							Integer val = new Integer("" + fileValue);
							Method method = thisClass.getMethod("set"
									+ fileName.substring(0, 1).toUpperCase()
									+ fileName.substring(1), field.getType());
							method.invoke(Constraint, val);
							return true;
						} else {
							throw new AiyiIdTypeException(
									"ID type is not a corresConstraintnding type at "
											+ "set"
											+ fileName.substring(0, 1)
													.toUpperCase()
											+ fileName.substring(1) + "\n"
											+ "the will give value type is "
											+ fileValue.getClass().getName()
											+ "\n" + "the filed type type is "
											+ field.getType().getName());
						}
					} else if (calssName.equals("long")
							|| calssName.equals("java.lang.Long")) {
						Long val = new Long("" + fileValue);
						Method method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), field.getType());
						method.invoke(Constraint, val);
						return true;
					} else {
						Method method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), field.getType());
						method.invoke(Constraint, fileValue);
						return true;
					}
				} catch (AiyiIdTypeException e1) {
					e1.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
			if (null != fileValue) {
				try {
					Method method = null;
					if (fileValue instanceof String) {
						method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), String.class);
					} else if (fileValue instanceof Integer) {
						method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), Integer.TYPE);
					} else if (fileValue instanceof Long) {
						method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), Long.TYPE);
					} else if (fileValue instanceof Double) {
						method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), Double.TYPE);
					} else if (fileValue instanceof Short) {
						method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), Short.TYPE);
					}

					method.invoke(Constraint, fileValue);
				} catch (NoSuchMethodException e) {

					try {
						Method method = thisClass.getMethod("set"
								+ fileName.substring(0, 1).toUpperCase()
								+ fileName.substring(1), Boolean.TYPE);
						if (fileValue instanceof Integer) {
							method.invoke(Constraint, (Integer) fileValue > 0 ? true
									: false);
						}
					} catch (NoSuchMethodException e2) {
						e2.printStackTrace();
					}
				}
			}
			return true;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 驼峰标识转下划线标识 ,即获取表名
	 * 
	 * @param text
	 * @return
	 */
	public String toTableString(String text) {
		String tName = text.substring(0, 1).toLowerCase();
		for (int i = 1; i < text.length(); i++) {
			if (!Character.isLowerCase(text.charAt(i))) {
				tName += "_";
			}
			tName += text.substring(i, i + 1);
		}
//		tName = "t_"+tName;
		return tName.toLowerCase();
	}

	public String getTableNameByClazz(Class<? extends Constraint> Constraint) {
		if (Constraint.isAnnotationPresent(TableName.class)) {
			return Constraint.getAnnotation(TableName.class).name();
		} else {
			String tName = toTableString(Constraint.getSimpleName());
			if ("Constraint".equals(tName.substring(tName.length() - 3,
					tName.length() - 1))) {
				tName = tName.substring(0, tName.length() - 3);
			}
//			tName="t_"+tName;
			return tName;
		}
	}
	
	public List<Parameter> getParameterList(Class<T> Constraint){
		List<Parameter> list = new ArrayList<>();
		Class<? extends Constraint> thisClass = Constraint;
	    Field[] fields = thisClass.getDeclaredFields();
	    	try {
	    		Object o = thisClass.newInstance();
	    		for(Field f : fields){
		    		if(!f.getName().equalsIgnoreCase("ID") && !f.isAnnotationPresent(TempField.class)){
		    			String fName = f.getName();
		    			
		    			//�ж��Ƿ���boolean����
		    			String getf = "get";
		    			String fieldType = f.getGenericType().toString();
		    			if (fieldType.indexOf("boolean") != -1 || fieldType.indexOf("Boolean") != -1) {
		    				getf = "is";
						}
		    			if (f.isAnnotationPresent(FieldName.class)) {
		    				String fieldName = f.getAnnotation(FieldName.class).name();
		    				Method get = thisClass.getMethod(getf + fName.substring(0, 1).toUpperCase() + fName.substring(1));
		    				Object getValue = get.invoke(o);
		    				Parameter Parameter = new Parameter(fieldName, getValue);
							list.add(Parameter);
		    			}else{
		    				String fieldName = toTableString(fName);
		    				Method get = thisClass.getMethod(getf + fName.substring(0, 1).toUpperCase() + fName.substring(1));
		    				Object getValue = get.invoke(o);
		    				Parameter Parameter = new Parameter(fieldName , getValue);
							list.add(Parameter);
						}
		    		}
	    		}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
	    	catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		return list;
	}
	

	public static<T extends Constraint> List<Parameter> getParameterListofStatic(Constraint Constraint){
		List<Parameter> list = new ArrayList<>();
		Class<? extends Constraint> thisClass = Constraint.getClass();
	    Field[] fields = thisClass.getDeclaredFields();
	    	try {
	    		for(Field f : fields){
		    		if(!f.getName().equalsIgnoreCase("ID") && !f.isAnnotationPresent(TempField.class)){
		    			String fName = f.getName();
		    			
		    			String getf = "get";
		    			String fieldType = f.getGenericType().toString();
		    			if (fieldType.indexOf("boolean") != -1 || fieldType.indexOf("Boolean") != -1) {
		    				getf = "is";
						}
		    			if (f.isAnnotationPresent(FieldName.class)) {
		    				String fieldName = f.getAnnotation(FieldName.class).name();
		    				Method get = thisClass.getMethod(getf + fName.substring(0, 1).toUpperCase() + fName.substring(1));
		    				Object getValue = get.invoke(Constraint);
		    				Parameter Parameter = new Parameter(fieldName, getValue);
							list.add(Parameter);
		    			}else{
		    				String fieldName = new SqlUtil<T>().toTableString(fName);
		    				Method get = thisClass.getMethod(getf + fName.substring(0, 1).toUpperCase() + fName.substring(1));
		    				Object getValue = get.invoke(Constraint);
		    				Parameter Parameter = new Parameter(fieldName, getValue);
							list.add(Parameter);
						}
		    		}
	    		}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
	    	catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		return list;
	}

}
