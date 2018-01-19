package com.cxg.interactiveweb.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.cxg.core.beans.Method;
import com.cxg.core.sql.where.Constant;
import com.cxg.core.util.Formatter;
import com.cxg.core.util.FormatterSql;
import com.cxg.interactiveweb.dao.impl.LoginDaoImpl;
import com.cxg.interactiveweb.pojo.Loginpo;
import com.cxg.interactiveweb.service.LoginService;
import com.cxg.service.impl.GeneralServiceImpl;

@Service
public class LoginServiceImpl extends GeneralServiceImpl<Loginpo>  implements LoginService {

	
	@Resource
	private LoginDaoImpl userDao;

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public int addList() {
		
		List<Loginpo> users = new ArrayList<>();
		int add=0;
		add += userDao.add(users);
		return add;
	}


	

	@Override
	public List<Map> queryuserAndImg() {
		return userDao.queryuserAndImg();
	}
}
