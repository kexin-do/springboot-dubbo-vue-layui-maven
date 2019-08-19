package com.zrd.wh.front.web.front.login.entity;

import javax.servlet.http.HttpSession;

import com.zrd.wh.core.base.global.GlobalVariable;

import java.io.Serializable;

/**
 * 用户在线状态类
 * 2019-03-06
 * kx
 */
public class UserLoginStatus implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3033504779330169568L;

	//用户状态
    private String status;

    //用户session
    private HttpSession session;
    
    public static final String test = "123";

    public static class Inner{
    	
    }
    
    public UserLoginStatus(String status, HttpSession session) {
        this.status = status;
        this.session = session;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * 切换内存缓存用户的状态
     * @param userId 系统用户主键
     * @param userStatus 用户切换状态
     */
	public static void changeUserLoginStatus(String userId, String userStatus) {
		UserLoginStatus uls = (UserLoginStatus) GlobalVariable.loginMap.get(userId);
		if(uls != null){
			uls.setStatus(userStatus);
			GlobalVariable.loginMap.put(userId, uls);
		}		
	}
}
