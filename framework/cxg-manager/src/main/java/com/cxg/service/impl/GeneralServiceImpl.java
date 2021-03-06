package com.cxg.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.cxg.core.beans.Constraint;
import com.cxg.core.beans.WherePrams;
import com.cxg.core.util.Formatter;
import com.cxg.dao.IGeneralMybatisDao;
import com.cxg.service.IGeneralService;


@SuppressWarnings("rawtypes")
@Service("generalService")
public class GeneralServiceImpl<T extends Constraint> implements IGeneralService<T> {

	@Autowired
	IGeneralMybatisDao<T> generalMybatisDao;

	@Override
	public int insertNotNull(T t) {
		return generalMybatisDao.addLocal(t);
	}

	@Override
	public int insert(T t) {
		return generalMybatisDao.add(t);
	}

	@Override
	public int insertByList(List<T> t) {
		int count=0;
		if (t.size()>0) {
			for (int i = 0; i <t.size(); i++) {
				generalMybatisDao.add(t.get(i));
				count++;
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Long id) {
		return (T) generalMybatisDao.get(id);
	}

	@Override
	public Serializable getFieldById(Long id, String fileName) {
		return generalMybatisDao.getField(id, fileName);
	}

	@Override
	public T getByCondition(WherePrams where) {
		return (T) generalMybatisDao.get(where);
	}

	@Override
	public Map<String,Object> getFileByCondition(WherePrams where, String fileName) {
		return generalMybatisDao.getFile(where, fileName);
	}

	@Override
	public List<T> queryToListByCondition(WherePrams where) {
		return (List<T>) generalMybatisDao.list(where);
	}

	

	@Override
	public List<Map<String, Object>> queryFilesByCondition(WherePrams where, String[] files) {
		return generalMybatisDao.listFiles(where, files);
	}

	@Override
	public int updateNotNULL(T t) {
		
		return generalMybatisDao.updateLocal(t);
	}

	@Override
	public int update(T t) {
		return generalMybatisDao.update(t);
	}

	@Override
	public int updateNotNullByCondition(T t, WherePrams where) {
		return generalMybatisDao.updateLocal(t, where);
	}

	@Override
	public int update(T t, WherePrams where) {
		return generalMybatisDao.update(t, where);
	}

	@Override
	public int deleteById(Long id) {
		return generalMybatisDao.del(id);
	}

	@Override
	public int deleteByCondition(WherePrams where) {
		return generalMybatisDao.del(where);
	}



	@Override
	public long count(WherePrams where) {
		return generalMybatisDao.count(where);
	}

	@Override
	public long size() {
		return generalMybatisDao.size();
	}

	@Override
	public boolean isExist(T t) {
		return generalMybatisDao.isExist(t);
	}

	@Override
	public boolean isExist(WherePrams where) {
		return generalMybatisDao.isExist(where);
	}

	

	
	
	
}
