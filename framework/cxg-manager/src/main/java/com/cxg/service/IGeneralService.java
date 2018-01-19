package com.cxg.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cxg.core.beans.Constraint;
import com.cxg.core.beans.WherePrams;

/**
 * @ClassName: IGeneralService
* @Description: 通用的service
* @author haipeng
* @date 2017年1月3日
 */
public interface IGeneralService<T> {
	 /**
     * 添加不为空的记录（只将不为空字段入库，效率高）
     * @param t 实体对象
     * @return 插入成功的条数,1:成功  0:失败
     */
    public  int insertNotNull(T t);

    /**
     * 记录添加（所有字段入库，效率中）
     * @param t
     * @return 插入成功的条数,0:失败,1:插入成功
     */
    public  int insert(T t);

    
    /**
     * 批量插入
     * @param t
     * @return 插入成功的条数,0:失败,1:插入成功
     */
    public int insertByList(List<T> t);
    /**
     * 通过主键获取某个记录
     * @param id 主键
     * @return 实体对象
     */
    public T  getById(Long id);

    /**
     * 通过主键获取某个字段的值
     * @param id id主键
     * @param fileName 要查询的字段
     * @return
     */
    public Serializable getFieldById(Long id, String fileName);

    /**
     * 通过条件获取一条实体数据
     * @param T
     * @param 条件表达式
     * @return PO
     */
    public T getByCondition(WherePrams where);

    /**
     * 条件获取某个记录字段
     * @param where  条件
     * @param fileName 要查询的字段
     * @return
     */
    public Map<String, Object> getFileByCondition(WherePrams where, String fileName);

    /**
     * 通过条件查询列表
     * @param where 条件表达式
     * @return List<T> 实体集合
     */
    public List<T> queryToListByCondition(WherePrams where);

  
  
    /**
     * 通过条件查询一些字段的值
     * @param where 条件表达式
     * @param files 要查询的字段集
     * @return List<Map> 查询的PO字段列表
     */
    public List<Map<String, Object>> queryFilesByCondition(WherePrams where, String[] files);

    /**
     * 更新不为null的PO字段
     * @param t 实体对象
     * @return 受影响的行数 1:成功   0:不成功
     */
    public int updateNotNULL(T t);

    /**
     * 更新实体的所有字段
     * @param t
     * @return 受影响的行数 1:成功  0:不成功
     */
    public int update(T t);
    
   

    /**
     * 通过条件更新不为null的字段
     * @param t 实体对象
     * @param where条件表达式
     * @return 受影响的行数 1:成功  0:不成功
     */
    public int updateNotNullByCondition(T t, WherePrams where);

    /**
     * 通过条件更新所有字段
     * @param t 实体对象
     * @param where条件表达式
     * @return 受影响的行数 1:成功  0:不成功
     */
    public int update(T t, WherePrams where);

    /**
     * 删除某个记录
     * @param id 主键
     * @return 受影响的行数 1:成功  0:失败
     */
    public int deleteById(Long id);

    /**
     * 通过条件删除某条记录
     * @param where 条件表达式
     * @return 受影响的行数 1:成功  0:不成功
     */
    public int deleteByCondition(WherePrams where);

   

    /**
     * 获取指定条件的记录数
     * @param where 条件表达式
     * @return 查询到的记录数 
     */
    public long count(WherePrams where);

    /**
     * 获取对应表中的记录数
     * @return 表中的条数
     */
    public long size();

    /**
     * 是否存在字段相同的记录（ID以及不为空的字段除外）
     * @param po 实体对象
     * @return boolean true:存在   fasle:不存在
     */
    public boolean isExist(T t);

    /**
     * 是否存在指定条件的记录
     * @param where 条件表达式
     * @return boolean true:存在   fasle:不存在
     */
    public boolean isExist(WherePrams where);


    
}
