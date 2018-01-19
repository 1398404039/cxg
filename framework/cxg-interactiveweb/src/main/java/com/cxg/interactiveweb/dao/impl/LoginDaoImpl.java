package com.cxg.interactiveweb.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cxg.dao.impl.GeneralMybatisDaoImpl;
import com.cxg.interactiveweb.dao.IloginDao;
import com.cxg.interactiveweb.pojo.Loginpo;


/**
 * @ClassName: UserDao
* @Description:UserDao的实现类，该类继承了公共Dao的原始方法。当你要给爱易REUD框架增加新功能时，不用去改源码，爱易CRUD提供了可编程式插件接口的方案。
 * 你可以编写一个接口类并实现他，以此来扩展爱易CRUD 
* @author haipeng
* @date 2017年1月8日
 */
@Repository
public class LoginDaoImpl extends GeneralMybatisDaoImpl<Loginpo> implements IloginDao /*UserDaoInterface这个接口是一个插件实现的演示。他完善了爱易CRUD的批量添加方案*/ {

	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	@Override
	public int add(List<Loginpo> list) {
		return 0;
	}


	@Override
	public List<Map> queryuserAndImg() {
		return null;
	}
	
	
	
}
