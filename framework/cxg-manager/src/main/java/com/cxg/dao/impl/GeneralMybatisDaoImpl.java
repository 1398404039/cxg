package com.cxg.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cxg.core.annotation.po.FieldName;
import com.cxg.core.beans.Constraint;
import com.cxg.core.beans.FmtParm;
import com.cxg.core.beans.Method;
import com.cxg.core.beans.Parameter;
import com.cxg.core.beans.WherePrams;
import com.cxg.core.sql.where.Constant;
import com.cxg.core.sql.where.SqlUtil;
import com.cxg.core.util.Formatter;
import com.cxg.core.util.GenericsUtils;
import com.cxg.dao.IGeneralMybatisDao;

/**
 * @ClassName: GeneralDaoMybatisImpl
* @Description: 通用Dao实现类
* @author haipeng
* @date 2017年1月1日
 */
@SuppressWarnings("unused")
@Repository("generalMybatisDao")
public class GeneralMybatisDaoImpl<T extends Constraint> implements IGeneralMybatisDao<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	private Class<T> entityClass;
	
	
	private String tableName;
	
	private List<Parameter> sqlParms;
	
	
	private List<Parameter> selectSqlParms;
	
	private SqlUtil<T> sqlUtil;

	@SuppressWarnings("unchecked")
	public GeneralMybatisDaoImpl(){
		super();
		
		this.sqlUtil = new SqlUtil<T>();
		
        this.entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass());
        
        this.sqlParms = this.sqlUtil.getParameterList(this.entityClass);
        
        this.selectSqlParms = this.sqlUtil.getParameterListOfSelect(this.entityClass);
        								   
        
        this.tableName = this.sqlUtil.getTableName(this.entityClass);
        
	}
        
	
	@Override
	public int addLocal(T po) {
		
		String sql = "insert into " + tableName + " (";
		String prams = "";
		String values = "";
		
		List<Parameter> pramList = SqlUtil.getParameterListofStatic(po);
		
		int index = 0;
		for (int i = 0; i < pramList.size(); i++) {
			if (pramList.get(i).getValue() == null || (pramList.get(i).getValue() + "") .equals("0")) {
				continue;
			}else{
				if(index > 0){
					prams += ",";
					values += ",";
				}
				prams += pramList.get(i).getFile();
				Object value = pramList.get(i).getValue();
				if (value instanceof byte[] ) {
					values += "'" + new String((byte[]) value) + "'";
				}else if(value instanceof Boolean){
					values += "'" + ((boolean)value == true ? 1 : 0) + "'";
				}else{
					values += "'" + value + "'";
				}
				
				index ++;
			}
		}
		sql += prams + ") value (" + values +");";

		SqlUtil.setFileValue(po, "id", nextId());
		
		logger.debug(sql);
		return sqlSessionTemplate.insert("addLocal", sql);
		
	}
	 /**
     * 获取某表的下一个Id
     */
    public long nextId(){
        String sql = "SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_NAME='" + tableName + "'";
        Long idVal = sqlSessionTemplate.selectOne("fetchSeqNextval", sql);
        if (null == idVal) {
            logger.error("/********************************");
            logger.error("/获取id失败，" + tableName + "表异常。请检查是否存在或者配为自增主键");
            logger.error("/********************************");
            return 0;
        }
        return idVal;

    }

	@Override
	public int add(T po) {
		String sql = "insert into " + tableName + " (";
		String prams = "";
		String values = "";
		
		List<Parameter> pramList = SqlUtil.getParameterListofStatic(po);
		
		for (int i = 0; i < pramList.size(); i++) {
			prams += pramList.get(i).getFile();
			if (pramList.get(i).getValue() == null) {
				values += "null";
			}else if(pramList.get(i).getValue() instanceof Boolean){
				values += "'" + ((boolean)pramList.get(i).getValue() == true ? 1 : 0) + "'";
			}else{
				values += "'" + pramList.get(i).getValue() + "'";
			}
			
			if(i < pramList.size() -1){
				prams += ",";
				values += ",";
			}
		}
		sql += prams + ") value (" + values +");";
		
		SqlUtil.setFileValue(po, "id", nextId());
		
		return sqlSessionTemplate.insert("add", sql);
	}


	
	
	@Override
	public T get(Long j) {
		String sql = "select ";
		for (int i = 0; i < selectSqlParms.size(); i++) {
			sql += selectSqlParms.get(i).getFile();
			if(i < selectSqlParms.size() -1){
				sql += ",";
			}else{
				sql += " ";
			}
		}
		sql += " from " + tableName + " where id='" + j + "';";
		Map<String, Object> resultMap = sqlSessionTemplate.selectOne(
	                "getById", sql);

		return handleResult(resultMap, this.entityClass);
	}

	@Override
	public Serializable getField(Long id, String fileName) {
		String field = fileName;
		String tabField = "";
		Field f = sqlUtil.getField(this.entityClass, fileName);
		if (null == f) {
			logger.error("查询字段失败(无法找到" + this.entityClass + "中的" + fileName + "字段)");
		}
		FieldName annotation = f.getAnnotation(FieldName.class);
		if (null == annotation) {
			tabField = sqlUtil.toTableString(fileName) + " as " + fileName;
		}else{
			tabField = annotation.name() + " as " + fileName;
		}
		
		String sql = "select ";
		sql += tabField + " from " + tableName + " where id='" + id + "';";
		Map<String, Object> resultMap = sqlSessionTemplate.selectOne(
                "getFieldById", sql);
		return (Serializable) resultMap.get(fileName);
	}

	@Override
	public T get(WherePrams where) {
		String sql = "select ";
		for (int i = 0; i < selectSqlParms.size(); i++) {
			sql += selectSqlParms.get(i).getFile();
			if(i < selectSqlParms.size() -1){
				sql += ",";
			}else{
				sql += " ";
			}
		}
		sql += "from " + tableName + where.getWherePrams() + ";";
		
		Map<String, Object> resultMap = sqlSessionTemplate.selectOne(
                "getByParm", sql);
		
		return handleResult(resultMap, this.entityClass);
	}

	@Override
	public Map<String, Object> getFile(WherePrams where, String fileName) {
		String field = fileName;
		String tabField = "";
		Field f = sqlUtil.getField(this.entityClass, fileName);
		if (null == f) {
			logger.error("查询字段失败(无法找到" + this.entityClass + "中的" + fileName + "字段)");
		}
		FieldName annotation = f.getAnnotation(FieldName.class);
		if (null == annotation) {
			tabField = sqlUtil.toTableString(fileName) + " as " + fileName;
		}else{
			tabField = annotation.name() + " as " + fileName;
		}
		
		String sql = "select ";
		sql += tabField + " from " + tableName + where.getWherePrams() + ";";
		Map<String, Object> resultMap = sqlSessionTemplate.selectOne(
                "getFieldByParm", sql);
		return resultMap;
	}

	@Override
	public List<T> list(WherePrams where) {
		
		String sql = "select ";
		for (int i = 0; i < selectSqlParms.size(); i++) {
			sql += selectSqlParms.get(i).getFile();
			if(i < selectSqlParms.size() -1){
				sql += ",";
			}else{
				sql += " ";
			}
		}
		sql += "from " + tableName + where.getWherePrams() + ";";
		
		List<Map<String, Object>> selectList = sqlSessionTemplate.selectList("selectList", sql);
		
		List<T> list = new ArrayList<>();
		for (Map<String, Object> map : selectList) {
			T t = handleResult(map, this.entityClass);
			list.add(t);
		}
		
		return list;
		
	}

	@Override
	public List<Map<String, Object>> listFile(WherePrams where, String fileName) {
		
		String field = fileName;
		String tabField = "";
		Field f = sqlUtil.getField(this.entityClass, fileName);
		if (null == f) {
			logger.error("查询指定字段集失败(无法序列化" + this.entityClass + "中的" + fileName + "字段)");
		}
		FieldName annotation = f.getAnnotation(FieldName.class);
		if (null == annotation) {
			tabField = sqlUtil.toTableString(fileName) + " as " + fileName;
		}else{
			tabField = annotation.name() + " as " + fileName;
		}
		
		String sql = "select ";
		sql += tabField + " from " + tableName + where.getWherePrams() + ";";
		List<Map<String, Object>> resultMap = sqlSessionTemplate.selectList("selectListField", sql);
		

		return resultMap;
	}

	@Override
	public List<Map<String, Object>> listFiles(WherePrams where, String[] files) {
		String tabField = "";
		int index = 1;
		for (String field : files) {
			Field f = sqlUtil.getField(this.entityClass, field);
			if (null == f) {
				logger.error("查询指定字段集失败(无法序列化" + this.entityClass + "中的" + field + "字段)");
			}
			FieldName annotation = f.getAnnotation(FieldName.class);
			if (null == annotation) {
				tabField += sqlUtil.toTableString(field) + " as " + field;
			}else{
				tabField += annotation.name() + " as " + field;
			}
			if (index < files.length) {
				tabField += ",";
			}
			
			index ++;
		}
		
		String sql = "select ";
		sql += tabField + " from " + tableName + where.getWherePrams() + ";";
		List<Map<String, Object>> resultMap = sqlSessionTemplate.selectList("selectListField", sql);
		
		return resultMap;
	}

	@Override
	public int updateLocal(T po) {
		
		Serializable id = sqlUtil.getFileValue(po, "id");
		
		if(null == id){
			return 0;
		}
		String sql = "update " + tableName + " set ";
		List<Parameter> prams = sqlUtil.getParameterList(po);
		for (int i = 0; i < prams.size(); i++) {
			if(null != prams.get(i).getValue()){
				sql += prams.get(i).getFile() + "=";
				Object value = prams.get(i).getValue();
				if (value instanceof byte[] ) {
					sql += "'" + new String((byte[]) value) + "'";
				}else if(value instanceof Boolean){
					sql += "'" + ((boolean)value == true ? 1 : 0) + "'";
				}else{
					sql += "'" + value + "'";
				}
				
				if (i < prams.size() -1) {
					sql += ",";
				}
			}
		}
		sql += " where id='" + id +"';";
		
		return sqlSessionTemplate.update("updateLocal", sql);
	}

	@Override
	public int update(T po) {
		
		Serializable id = sqlUtil.getFileValue(po, "id");
		
		if(null == id){
			return 0;
		}
		String sql = "update " + tableName + " set ";
		
		List<Parameter> prams = sqlUtil.getParameterList(po);
		
		for (int i = 0; i < prams.size(); i++) {
			if(null != prams.get(i).getValue()){
				sql += prams.get(i).getFile() + "=";
				Object value = prams.get(i).getValue();
				if (value instanceof byte[] ) {
					sql += "'" + new String((byte[]) value) + "'";
				}else if(value instanceof Boolean){
					sql += "'" + ((boolean)value == true ? 1 : 0) + "'";
				}else{
					sql += "'" + value + "'";
				}
				if (i < prams.size() -1) {
					sql += ",";
				}
			}else{
				sql += prams.get(i).getFile() + "=null";
				if (i < prams.size() -1) {
					sql += ",";
				}
			}
		}
		sql += " where id='" + id +"';";
		
		return sqlSessionTemplate.update("update", sql);
	}

	@Override
	public int updateLocal(T po, WherePrams where) {
		
		String sql = "update " + tableName + " set ";
		List<Parameter> prams = sqlUtil.getParameterList(po);
		for (int i = 0; i < prams.size(); i++) {
			if(null != prams.get(i).getValue()){
				sql += prams.get(i).getFile() + "=";
				Object value = prams.get(i).getValue();
				if (value instanceof byte[] ) {
					sql += "'" + new String((byte[]) value) + "'";
				}else if(value instanceof Boolean){
					sql += "'" + ((boolean)value == true ? 1 : 0) + "'";
				}else{
					sql += "'" + value + "'";
				}
//				sql += prams.get(i).getFile() + "='" + prams.get(i).getValue() + "'";
				if (i < prams.size() -1) {
					sql += ",";
				}
			}
		}
		sql += where.getWherePrams() +"';";
		
		return sqlSessionTemplate.update("updateLocalByPram", sql);
		
	}

	@Override
	public int update(T po, WherePrams where) {
		
		String sql = "update " + tableName + " set ";
		Object[] o = new Object[sqlParms.size()];
		for (int i = 0; i < sqlParms.size(); i++) {
			if(null != sqlParms.get(i).getValue()){
				if(sqlParms.get(i).getValue() instanceof Boolean){
					sql += sqlParms.get(i).getFile() + "='" + (((boolean)sqlParms.get(i).getValue()) == true ? 1 : 0) + "'";
				}else{
					sql += sqlParms.get(i).getFile() + "='" + sqlParms.get(i).getValue() + "'";
				}
				
				
				if (i < sqlParms.size() -1) {
					sql += ",";
				}
			}else{
				sql += sqlParms.get(i).getFile() + "=null";
				if (i < sqlParms.size() -1) {
					sql += ",";
				}
			}
		}
		sql += where.getWherePrams() + "';";
		
		return sqlSessionTemplate.update("updateByPram", sql);
		
	}

	@Override
	public int del(Long id) {
		String sql = "delete from " + tableName + " where id=" + id;
		
		return sqlSessionTemplate.delete("deleteById", sql);
	}

	@Override
	public int del(WherePrams where) {
		
		String sql = "delete from " + tableName + where.getWherePrams();
		
		return sqlSessionTemplate.delete("deleteByparm", sql);
	}




	@Override
	public long count(WherePrams where) {
		
		String sql = "select count(*) from ";
		
		sql += tableName + where.getWherePrams();
		
		long count = sqlSessionTemplate.selectOne("selectCountByParm", sql);
		
		return count;
	}

	@Override
	public long size() {
		String sql = "select count(*) from " + tableName;
		long count = sqlSessionTemplate.selectOne("selectCount", sql);
		return count;
	}

	@Override
	public boolean isExist(T po) {
		WherePrams wherePrams = Method.createDefault();

		List<Parameter> list = SqlUtil.getParameterListofStatic(po);
		
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				wherePrams = Method.where(list.get(i).getFile(), Constant.EQ, (Serializable)list.get(i).getValue());
			}else{
				wherePrams.and(list.get(i).getFile(), Constant.EQ, (Serializable)list.get(i).getValue());
			}
		}
		
		
		return count(wherePrams) > 0;
	}

	@Override
	public boolean isExist(WherePrams where) {
		return count(where) > 0;
	}

	
	
	private T handleResult(Map<String, Object> resultMap, Class<T> tClazz) {
		if (null == resultMap) {
			return null;
		}
        T t = null;
        try {
            t = tClazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("/********************************");
            logger.error("实例化Bean失败(" + this.entityClass + ")!"
                    + e.getMessage());
            logger.error("/********************************");
        } catch (IllegalAccessException e) {
            logger.error("/********************************");
            logger.error("实例化Bean失败(" + this.entityClass + ")!"
                    + e.getMessage());
            logger.error("/********************************");
        }
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            String key = entry.getKey();
            Serializable val = (Serializable) entry.getValue();
			try {
				SqlUtil.setFileValue(t, key, val);
			} catch (Exception e) {
				logger.error("/********************************");
				logger.error("/ʵ�л�����(" + this.entityClass + ")ʱ���ֶθ�ֵ�쳣(" + key + "):"
						+ e.getMessage());
				logger.error("/********************************");
			}
        }
        return t;
    }
	
	


	
	
	/**
	 * 是否为SQL表达符号
	 * @param c
	 * @return
	 */
	private boolean isC(String c){
		switch (c) {
		case "=":
			return true;
		case "<":
			return true;
		case ">":
			return true;
		case "<>":
			return true;
		case "like":
			return true;
		case "in":
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * 从List<String>集合中检查是否有存在的元素
	 * @param list
	 * @param tabName
	 * @return
	 */
	private boolean isExcTab (List<String> list, String tabName){
		for (String string : list) {
			if (tabName .equals( string)) {
				return true;
			}
		}
		return false;
	}





}
