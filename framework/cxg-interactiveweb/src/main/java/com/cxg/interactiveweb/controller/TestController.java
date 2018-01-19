package com.cxg.interactiveweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxg.interactiveweb.pojo.Loginpo;
import com.cxg.interactiveweb.service.LoginService;

@Controller
public class TestController {

	@Resource
	private LoginService testService;
	
	/**
	 * 添加测试
	 * @return
	 */
	@RequestMapping("testAdd")
	@ResponseBody
	public Object testAddList(){
		
		long t = System.currentTimeMillis();
		int n = testService.addList();
		long tT = System.currentTimeMillis();
		
		double s = (tT - t) / 1000;
		
		return "共添加" + n + "条数据，用时" + s + "秒";
	}
	
	
	/**
	 * 查询测试
	 * @return
	 */
	@RequestMapping("getbyid")
	@ResponseBody
	public Map getbyid(){
		
		Long l=(long) 1;
		Loginpo user= testService.getById(l);
		Map map =new HashMap<>();
		map.put("id", user.getId());
		map.put("name",user.getUser() );
		return map;
	}
	
	


	/**
	 * 查询测试
	 * @return
	 */
	@RequestMapping("queryuserAndImg")
	@ResponseBody
	public void queryuserAndImg(){
		List<Map> mapList = testService.queryuserAndImg();
		System.out.println(mapList.size());
	}
	
	/**
	 * 查询测试
	 * @return
	 */
	@RequestMapping(value="/index" ,method=RequestMethod.GET)
	public String index(){
		System.out.println("/index");
		return "/views/index";
//		return "WEB-INF/indexeee";
	}
}
