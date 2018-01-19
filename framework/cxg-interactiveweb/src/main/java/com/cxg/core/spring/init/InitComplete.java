package com.cxg.core.spring.init;

import java.util.logging.Logger;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * @ClassName: InitComplete
* @Description: 初始化完毕后输出项目访问路径
* @author haipeng
* @date 2016年12月31日
 */
@Component
public class InitComplete implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		Object source = arg0.getSource();
		if (source.getClass().getName().equals("org.springframework.web.context.support.XmlWebApplicationContext")) {
			@SuppressWarnings("resource")
			XmlWebApplicationContext application = (XmlWebApplicationContext) source;
			
			Logger logger = Logger.getAnonymousLogger();
			
			String line = System.getProperty("line.separator");
			
			logger.info(line + line + "========================>>项目路径：" + application.getApplicationName() + line + line);
		}
		
		
	}

}
