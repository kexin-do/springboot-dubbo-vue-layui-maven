package com.zrd.wh.core.base.constant;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageModel implements Serializable{
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = 846496106509262083L;
	
	public static final String LOGOUT_SUCCESS_CODE="210";
    public static final String LOGOUT_SUCCESS_MSG="退出成功";
    public static final String LOGIN_SUCCESS_CODE="211";
    public static final String LOGIN_SUCCESS_MSG="登录成功";
    public static final String SIGNUP_SUCCESS_CODE="212";
    public static final String SIGNUP_SUCCESS_MSG="注册成功";
    public static final String QUERY_SUCCESS_CODE="213";
    public static final String QUERY_SUCCESS_MSG="查询数据成功";
    public static final String DELETE_SUCCESS_CODE="214";
    public static final String DELETE_SUCCESS_MSG="删除数据成功";
    public static final String UPDATE_SUCCESS_CODE="215";
    public static final String UPDATE_SUCCESS_MSG="更新数据成功";
    public static final String ADD_SUCCESS_CODE="216";
    public static final String ADD_SUCCESS_MSG="添加数据成功";
    public static final String AUTHORIZATION_SUCCESS_CODE="217";
    public static final String AUTHORIZATION_SUCCESS_MSG="授权成功";
    public static final String AUTHENTICATION_SUCCESS_CODE="218";
    public static final String AUTHENTICATION_SUCCESS_MSG="认证成功";

    public static final String LOGIN_FAILURE_CODE="410";
    public static final String LOGIN_FAILURE_MSG="登录失败";
    public static final String SIGNUP_FAILURE_CODE="411";
    public static final String SIGNUP_FAILURE_MSG="注册失败";
    public static final String QUERY_FAILURE_CODE="412";
    public static final String QUERY_FAILURE_MSG="查询数据失败";
    public static final String DELETE_FAILURE_CODE="413";
    public static final String DELETE_FAILURE_MSG="删除数据失败";
    public static final String UPDATE_FAILURE_CODE="414";
    public static final String UPDATE_FAILURE_MSG="更新数据失败";
    public static final String ADD_FAILURE_CODE="415";
    public static final String ADD_FAILURE_MSG="添加数据失败";
    public static final String LOGIN_TIMEOUT_CODE = "416";
    public static final String LOGIN_TIMEOUT_MSG = "登录超时";


    public static final String AUTHORIZATION_FAILURE_CODE="610";
    public static final String AUTHORIZATION_FAILURE_MSG="授权失败";
    public static final String AUTHENTICATION_FAILURE_CODE="611";
    public static final String AUTHENTICATION_FAILURE_MSG="认证失败";
    
    public static final String SYSTEM_ERROR_CODE = "810";
    public static final String SYSTEM_ERROR_MSG = "系统异常";
    public static final String DATABASE_ERROR_CODE = "820";
    public static final String DATABASE_ERROR_MSG = "数据库异常";

    private Map<String, Object> data = new HashMap<>();
    private String statusCode;
    private String statusInfo;

    public MessageModel(){}

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }


}
