package com.cxg.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cxg.core.beans.Constraint;
import com.cxg.core.beans.WherePrams;
import com.cxg.core.util.Formatter;

/**
 * @ClassName: IGeneralDaoMybatis
* @Description: 公共数据库操作层
* @author haipeng
* @date 2017年1月3日
 */
public interface IGeneralMybatisDao<T extends Constraint> {

	 /**
     * 添加不为空的记录（只将不为空字段入库，效率高）
     * @param po
     * @return 受改变的记录数
     */
    public int addLocal(T po);

    /**
     * 记录添加（所有字段入库，效率中）
     * @param po
     * @return
     */
    public int add(T po);

    /**
     * 通过主键获取某个记录
     * @param id 主键
     * @return T
     */
    public T get(Long id);

    /**
     * 通过主键获取某个字段的值
     * @param id
     * @param fileName
     * @return
     */
    public Serializable getField(Long id, String fileName);

    /**
     * 条件获取一条记录
     * @param t
     * @param 条件表达式
     * @return PO
     */
    public T get(WherePrams where);

    /**
     * 条件获取某个记录字段
     * @param where
     * @param fileName
     * @return
     */
    public Map<String,Object> getFile(WherePrams where, String fileName);

    /**
     * 条件查询列表
     * @param where 条件表达式
     * @return PO列表
     */
    public List<T> list(WherePrams where);

    /**
     * 查询某个字段列表
     * @param where 条件表达式
     * @param fileName 要查询的字段
     * @return
     */
    public List<Map<String, Object>> listFile(WherePrams where, String fileName);

    /**
     * 查询某些字段
     * @param where 条件表达式
     * @param files 要查询的字段集
     * @return 查询的PO字段列表
     */
    public List<Map<String, Object>> listFiles(WherePrams where, String[] files);

    /**
     * 更新不为null的PO字段
     * @param po
     * @return 受影响的行数
     */
    public int updateLocal(T po);

    /**
     * 更新PO的所有字段
     * @param po
     * @return 受影响的行数
     */
    public int update(T po);

    /**
     * 条件更新不为null的字段
     * @param po
     * @param 条件表达式
     * @return 受影响的行数
     */
    public int updateLocal(T po, WherePrams where);

    /**
     * 条件更新所有字段
     * @param po
     * @param 条件表达式
     * @return 受影响的行数
     */
    public int update(T po, WherePrams where);

    /**
     * 删除某个记录
     * @param id 主键
     * @return 受影响的行数
     */
    public int del(Long id);

    /**
     * 条件删除某个记录
     * @param where 条件表达式
     * @return 受影响的行数
     */
    public int del(WherePrams where);

   

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
     * @param po 参照实体
     * @return
     */
    public boolean isExist(T po);

    /**
     * 是否存在指定条件的记录
     * @param where 条件表达式
     * @return
     */
    public boolean isExist(WherePrams where);
}
