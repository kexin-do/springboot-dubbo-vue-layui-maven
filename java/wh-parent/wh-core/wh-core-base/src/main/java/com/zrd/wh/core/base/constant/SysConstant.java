package com.zrd.wh.core.base.constant;

/**
 * 常量的说明
 */
public class SysConstant {
	
	/** 登陆用户密码过期时间间隔 */
	public final static String TIMEINTERVAL = "USER.timeInterval";
	
	/** 登录用户错误次数限制 */
	public final static String LOGINERRORLIMIT = "USER.loginErrorLimit";
	
	/** 用户登录超时设置（单位：分钟）*/
	public final static String LOGINTIMEOUT = "USER.loginTimeOut";
	
	/** 接口nas文件备份路径 */
	public final static  String  INTERFILEBACKPATH = "interFileBackPath";
	
	/** 系统登录的用户在线用户量，初始化大小*/
	public final static String LOGINMAPLCAPACITY = "INIT.loginMaplCapacity";
	/** 个人复核管理 异步复核 资源NO*/
	public final static String PERSONALASYNCHRONOUSCHECKNO ="CMS003105";
	/** 企业复核管理 异步复核 资源NO*/
	public final static String BUSINESSALASYNCHRONOUSCHECKNO ="CMS003205";
	/** 复核管理 同步复核 角色ID*/
	public final static String SYNCHRONOUSCHECKID ="synchronousCompositeRole";
	/**#生成上报文件（txt）根路径 */
	public final static String TEMPFILEROOT="tempFileRoot";
	/**机构区段码*/
	public final static String ORG_SEG_CODE="orgSegCode";
}
