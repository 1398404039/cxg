package com.cxg.interactiveweb.service;

import java.util.List;
import java.util.Map;

import com.cxg.interactiveweb.pojo.Loginpo;
import com.cxg.service.IGeneralService;


public interface LoginService extends IGeneralService<Loginpo> {

	/**
	 * 添加一些用于测试的记用户录到数据库
	 * @return
	 */
	int addList();
	
	
	
	
	List<Map>  queryuserAndImg();
	
}
