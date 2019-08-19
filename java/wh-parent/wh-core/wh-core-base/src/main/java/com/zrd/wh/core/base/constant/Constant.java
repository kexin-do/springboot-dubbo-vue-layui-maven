package com.zrd.wh.core.base.constant;

public class Constant {

	public static final String LOGIN_USER = "loginUser";
	public static final String USER_STATUS = "userStatus";
	public static final String CAN_GOTO_URL = "canGoToUrls";
	public static final String TO_INDEX_URL = "/csmsp/index";
	public static final String TO_AJAX_INDEX_URL = "toIndex#/csmsp/index";
	public static final String TO_LOGIN_URL = "/csmsp/toLogin";
	public static final String TO_AJAX_LOGIN_URL = "noLogin#/csmsp/toLogin";
	public static final String TO_ERROR_LOGIN_URL = "/csmsp/toErrorLogin";
	public static final String TO_AJAX_ERROR_LOGIN_URL = "noLogin#/csmsp/toErrorLogin";
	public static final String TO_UPDATE_PWD_URL = "/csmsp/toUpdatePwdPage";
	public static final String BACK_TASK_STOP_USER = "machineUser";
	public static final String DB_TYPE = "DB2";

	// 正常状态
	public static final String USER_STOP_STS_NORMAL = "1";
	// 系统停用
	public static final String USER_STOP_STS_BY_SYSTEM = "2";
	// 后台任务停用，原因是：长期未登陆
	public static final String USER_STOP_STS_BY_NO_LOGIN = "3";
	// 后台任务停用，原因是：长期未修改密码
	public static final String USER_STOP_STS_BY_NO_UPD_PWD = "4";

	public static final String USER_LOCK_STS_NORMAL = "0";
	public static final String USER_LOCK_STS_LOCK = "1";

	// 密码标识：正常
	public static final Integer PASSWORD_TYPE_NORMAL = 0;
	// 密码标识：即将过期
	public static final Integer PASSWORD_TYPE_WILL_PAST = 1;
	// 密码标识：已过期
	public static final Integer PASSWORD_TYPE_REAL_PAST = 2;

	public static final String PASSWORD_INVLID_NO = "0";
	public static final String PASSWORD_INVLID_YES = "1";

	/***			2019-05-21由Constant.java(缓存修改移除)迁移到当前类			***/
	// 系统日志类型
	public static String LOG_TYPE_QUERY = "0";
	public static String LOG_TYPE_UPDATE = "1";
	public static String LOG_TYPE_ADD = "2";
	public static String LOG_TYPE_DELETE = "3";

	// 服务大类 
	public static String SERVICE_TYPE_PERSON = "1";
	public static String SERVICE_TYPE_BUSINESS = "2";

	// 用户当前状态，用于全局参数
	public static final String USER_STATUS_NORMAL = "0";
	public static final String USER_STATUS_LOCK = "1";
	public static final String USER_STATUS_LOCK_MSG = "您已被停用，请联系管理员";
	public static final String USER_STATUS_STOP = "2";
	public static final String USER_STATUS_STOP_MSG = "您已被锁定，请联系管理员";
	// 用户被顶替掉
	public static final String USER_STATUS_RPLS = "3";
	
	public static final String USER_STATUS_RPLS_MSG = "您的用户已在其他电脑登录";

	// 预警信息
	/**
	 * 0：一小时内查询超量
	 */
	public static final String QUERY_WARN_HOUR = "0";
	/**
	 * 1：单日查询超量
	 */
	public static final String QUERY_WARN_DAY = "1";
	/**
	 * 2：非工作时间查询
	 */
	public static final String QUERY_WARN_NOT_IN_WORK_TIME = "2";
	/**
	 * 3：无业务背景查询
	 */
	public static final String QUERY_WARN_NO_BIZ = "3";
	/**
	 * 4：停用用户重启
	 */
	public static final String QUERY_WARN_STOP_USER_RESTART = "4";
	/**
	 * 5：日内重复查询
	 */
	public static final String QUERY_WARN_DAY_REPETITION = "5";
	/**
	 * 6：每分钟查询频率超量
	 */
	public static final String QUERY_WARN_MINUTE = "6";
	/**
	 * 7: 监管用户每分钟可查询信用报告最大笔数 超量
	 */
	public static final String QUERY_WARN_CREDIT_MINUTE = "7";
	/**
	 * 8: 一小时内允许监管用户进行信用报告查询总量 超量
	 */
	public static final String QUERY_WARN_CREDIT_HOUR = "8";
	/**
	 * 9: 允许监管用户进行信用报告查询时间 非工作时间查询
	 */
	public static final String QUERY_WARN_CREDIT_NOT_IN_WORK_TIME = "9";

	/***			2019-05-21由Constant.java(缓存修改移除)迁移到当前类			***/
	
	
	
	public static final Integer USER_LOGIN_SUCCESS = 0;
	public static final String USER_LOGIN_PWD_WILL_PAST = "您密码即将过期，请及时修改";
	public static final String USER_LOGIN_PWD_REAL_PAST = "您密码已过期，账户已被停用，请联系管理员";

	public static final Integer USER_LOGIN_PWD_ERROR = 3;
	public static final String USER_LOGIN_PWD_ERROR_MSG = "用户名或密码错误";

	public static final Integer USER_LOGIN_USR_ERROR = 4;
	public static final String USER_LOGIN_USR_ERROR_MSG = "用户名或密码错误";

	public static final Integer USER_LOGIN_ERR_MORE = 5;
	public static final String USER_LOGIN_ERR_MORE_MSG = "您登录的错误次数超过LIMIT次，账号已被锁定，请联系管理员";

	public static final Integer USER_LOGIN_USER_STS_ERROR = 6;
	public static final String USER_LOGIN_USER_STS_ERROR_MSG = "您已被停用，请联系管理员";
	public static final String USER_LOGIN_USER_STS_ERROR_MSG_NO_LOGIN = "您长期未登陆已被停用，请联系管理员";
	public static final String USER_LOGIN_USER_STS_ERROR_MSG_NO_UPD_PWD = "您长期未修改密码已被停用，请联系管理员";

	public static final Integer USER_LOGIN_LOCK_STS_ERROR = 6;
	public static final String USER_LOGIN_LOCK_STS_ERROR_MSG = "您已被锁定，请联系管理员";

	public static final Integer USER_LOGIN_ORG_ERROR = 7;
	public static final String USER_LOGIN_ORG_ERROR_MSG = "您所属机构已被停用，请联系管理员";

	public static final Integer USER_LOGIN_IP_ERROR = 8;
	public static final String USER_LOGIN_IP_ERROR_MSG = "非指定IP登录，请确认";
	/**
	 * 财务数据保存成功 1
	 */
	public static final Integer FIN_SAVE_SUCC = 1;
	/**
	 * 财务数据保存失败 0
	 */
	public static final Integer FIN_SAVE_FAIL = 0;
	/**
	 * 财务数据（企业名称+）企业身份标识类型+企业身份标识号码+报表年份+报表类型+报表类型细分+数据提供机构区段码数据重复 2
	 */
	public static final Integer FIN_SIGN_REPEAT = 2;
	/**
	 * 财务数据 界面数据没有进行任何修改 3
	 */
	public static final Integer FIN_NO_EDIT = 3;
	/**
	 * 财务数据 已存在2002版财务报表,不能填写2007版财务报表 4
	 */
	public static final Integer FIN_NO_ADD_2007 = 4;
	/**
	 * 财务数据 已存在2007版财务报表,不能填写2002版财务报表 5
	 */
	public static final Integer FIN_NO_ADD_2002 = 5;
	/**
	 * 数据校验成功 1
	 */
	public static final String CHECKBEAN_SUCC = "1";
	/**
	 * 个人/企业异议 没有查询到 数据提供机构代码 用户代码 密码 2
	 */
	public static final Integer DATAPROVIDERORGCODE_NOTEXITS = 2;
	/**
	 * 个人/企业异议 保存/更新数据出错 0
	 */
	public static final Integer SAVE_UPDATE_FAIL = 0;
	/**
	 * 不能新增个人/企业异议核查结果申请信息 -1
	 */
	public static final Integer NO_APPLICATION = -1;
	/**
	 * 不能新增个人/企业异议更正结果申请信息 (G106) -1
	 */
	public static final Integer NO_CORRECTRESULT = -1;
	/**
	 * 修改 个人/企业异议情况管理控制信息 根据传参不同，控制表对应字段置空 type = 1 标注置空
	 */
	public static final String MGR_TYPE_MARK = "1";
	/**
	 * 修改 个人/企业异议情况管理控制信息 根据传参不同，控制表对应字段置空 type = 2 核查回复置空
	 */
	public static final String MGR_TYPE_APPLICATION = "2";
	/**
	 * 修改 个人/企业异议情况管理控制信息 根据传参不同，控制表对应字段置空 type = 3 异议更正结果置空
	 */
	public static final String MGR_TYPE_CORRECTRESULT = "3";
}
