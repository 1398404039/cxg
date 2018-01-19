package com.cxg.interactiveweb.pojo;

import com.cxg.core.annotation.po.TableName;
import com.cxg.core.beans.Constraint;

/**
 * (LOGIN)
 * 
 * @author bianj
 * @version 1.0.0 2017-05-23
 */
@TableName(name="login")
public class Loginpo extends Constraint {
   
    /**  */
    private Integer id;
    
    /**  */
    private String user;
    
    /**  */
    private String password;
    
    /**
     * 获取
     * 
     * @return 
     */
    public Integer getId() {
        return this.id;
    }
     
    /**
     * 设置
     * 
     * @param id
     *          
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getUser() {
        return this.user;
    }
     
    /**
     * 设置
     * 
     * @param user
     *          
     */
    public void setUser(String user) {
        this.user = user;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getPassword() {
        return this.password;
    }
     
    /**
     * 设置
     * 
     * @param password
     *          
     */
    public void setPassword(String password) {
        this.password = password;
    }
}