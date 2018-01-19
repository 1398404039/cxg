package com.cxg.core.util;

import java.util.ArrayList;
import java.util.List;

import com.cxg.core.beans.Constraint;
import com.cxg.core.beans.FmtParm;
import com.cxg.core.beans.WherePrams;

/**
 * @ClassName: FormatterSql
* @Description: SQL查询格式化工具类
* @author haipeng
* @date 2017年1月8日
 */
public class FormatterSql implements Formatter {

	/**
	 * 格式化条件
	 */
	private List<FmtParm> fmtParms = new ArrayList<>();
	
	
	@Override
	public  void addFmt(String fieldName, String select, Class<? extends Constraint> po, WherePrams where) {
		// TODO Auto-generated method stub
		fmtParms.add(new FmtParm(fieldName, select, po, where));
	}

	@Override
	public void addFmt(FmtParm parm) {
		// TODO Auto-generated method stub
		fmtParms.add(parm);
	}

	@Override
	public List<FmtParm> listFmtParm() {
		// TODO Auto-generated method stub
		return fmtParms;
	}

}
