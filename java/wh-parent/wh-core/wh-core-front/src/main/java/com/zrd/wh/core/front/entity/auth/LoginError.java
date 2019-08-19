package com.zrd.wh.core.front.entity.auth;

import java.util.Date;

import com.zrd.wh.core.base.entity.BaseEntity;

/**
 * 登录错误次数信息表
 */
public class LoginError extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5782558055719450873L;

	//用户id
	private String userId;
	
	//登录错误次数
	private int userErrNum;
	
	//最后登录错误时间
	private Date lstTime;
	
	//最后登录ip
	private String lstIp;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUserErrNum() {
		return userErrNum;
	}

	public void setUserErrNum(int userErrNum) {
		this.userErrNum = userErrNum;
	}

	public Date getLstTime() {
		return lstTime;
	}

	public void setLstTime(Date lstTime) {
		this.lstTime = lstTime;
	}

	public String getLstIp() {
		return lstIp;
	}

	public void setLstIp(String lstIp) {
		this.lstIp = lstIp;
	}
}
