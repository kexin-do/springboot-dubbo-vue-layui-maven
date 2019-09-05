package com.zrd.wh.front.web.config.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.core.base.constant.SysConstant;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.global.GlobalVariable;
import com.zrd.wh.core.base.tool.Encrypt;
import com.zrd.wh.core.base.tool.PropertiesUtil;
import com.zrd.wh.core.base.tool.SysLogger;
import com.zrd.wh.core.base.tool.UUIDGenerator;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IOrgInfoService;
import com.zrd.wh.core.front.service.auth.IUserInfoService;
import com.zrd.wh.front.web.front.login.entity.UserLoginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class BaseController implements Serializable {
	private static final long serialVersionUID = -3121305631423557474L;

	@Autowired
	private IOrgInfoService orgInfoService;
	@Autowired
	private IUserInfoService userInfoService;
	/*@Autowired
	private SysLogsService sysLogsServie;
	@Autowired
	private CoreLogsService coreLogsServie;*/
	@Autowired
	private HttpServletRequest baseRequest;

	private User user;

	public final Encrypt instance = Encrypt.getInstance();

	@ModelAttribute
	public void prepare(HttpServletRequest baseRequest, HttpServletResponse response) throws DBException, SysException {
		HttpSession session = baseRequest.getSession();
		System.out.println(session.getId());
		user = (User) session.getAttribute(Constant.LOGIN_USER);
		if (user != null) {
			// 只要用户进行操作就设置超时时间，当用户在设置时间后未进行任何操作那么session超时
			session.setMaxInactiveInterval(this.getSystemParamIntValue(SysConstant.LOGINTIMEOUT) * 60);
			SysLogger.debug("用户代码：" + user.getUserNo());
		}
	}

	public String getLogUserAndIpAddr() {
		return "用户代码：【" + getUser().getUserNo() + "】用户Ip:【" + getClientIpAddress() + "】----->";
	}


	public Integer getSystemParamIntValue(String sysConstantValue) throws DBException, SysException {
		Integer oSysConstant = null;
		try {
			oSysConstant = Integer.valueOf(PropertiesUtil.getValueByKey(sysConstantValue));
		} catch (NumberFormatException e) {
			oSysConstant = null;
			String opText = "获取系统配置参数[" + sysConstantValue + "]失败，已使用默认值，请检查config.properties配置文件是否配置正确！！";
			SysLogger.info("用户代码：【" + getUser().getUserNo() + "】用户Ip:【" + getClientIpAddress() + "】" + opText);
			/*this.insertCorelogsAndSyslogs("系统登录获取参数[" + sysConstantValue + "]失败", "/csmsp/login,/csmsp/updatePwd",
					Constant.LOG_TYPE_QUERY, "userVerification", opText);*/
		}
		return oSysConstant;
	}

	public void setAjaxResponseHeader(HttpServletResponse response) {
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
	}

	public String getExplorerType(HttpServletRequest baseRequest) {
		String agent = baseRequest.getHeader("USER-AGENT");
		if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
			return "firefox";
		} else if (agent != null && agent.toLowerCase().indexOf("msie") > 0) {
			return "ie";
		} else if (agent != null && agent.toLowerCase().indexOf("chrome") > 0) {
			return "chrome";
		} else if (agent != null && agent.toLowerCase().indexOf("opera") > 0) {
			return "opera";
		} else if (agent != null && agent.toLowerCase().indexOf("safari") > 0) {
			return "safari";
		}
		return "others";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 获取用户真实IP地址，不使用baseRequest.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 * 
	 * @return ip
	 */
	public String getClientIpAddress() {
		String ip = baseRequest.getHeader("x-forwarded-for");
		System.out.println("x-forwarded-for ip: " + ip);
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = baseRequest.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = baseRequest.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = baseRequest.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = baseRequest.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = baseRequest.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = baseRequest.getRemoteAddr();
		}
		System.out.println("获取客户端ip: " + ip);
		return ip;
	}

	/**
	 * 锁定当前用户
	 * 
	 * @return
	 */
	public int lockOrStopUser(String userStatus) throws DBException, SysException {
		int count = 0;

		User currentUser = getUser();
		if (Constant.USER_STATUS_LOCK.equals(userStatus)) {
			currentUser.setUserLockSts("1");
			// 设置锁定变更人
			currentUser.setLstUser(currentUser.getUserNo());
			// 设置锁定变更时间
			currentUser.setLstTime(new Date());
			// 将全局参数中用户状态调整为锁定
			baseRequest.getSession().setAttribute(Constant.USER_STATUS, Constant.USER_STATUS_LOCK);
			UserLoginStatus uls = new UserLoginStatus(Constant.USER_STATUS_LOCK, baseRequest.getSession());
			GlobalVariable.loginMap.put(getUser().getUserId(), uls);
		} else if (Constant.USER_STATUS_STOP.equals(userStatus)) {
			currentUser.setUserSts("2");
			// 设置启停变更人
			currentUser.setStartUser(currentUser.getUserNo());
			// 设置启停变更时间
			currentUser.setStartTime(new Date());
			// 将全局参数中用户状态调整为停用
			baseRequest.getSession().setAttribute(Constant.USER_STATUS, Constant.USER_STATUS_STOP);
			UserLoginStatus uls = new UserLoginStatus(Constant.USER_STATUS_STOP, baseRequest.getSession());
			GlobalVariable.loginMap.put(getUser().getUserId(), uls);
		}
		count = userInfoService.updateUser(currentUser);

		// 更新表中用户信息
		return count;
	}


	/**
	 * 添加核心日志到数据库
	 * 
	 * @param opDesc
	 * @param opUrl
	 * @param opType
	 * @param opFun
	 * @param opText
	 * @return
	 *//*
	public int insertCorelogs(String opDesc, String opUrl, String opType, String opFun, String opText)
			throws DBException, SysException {
		return coreLogsServie.insert(this.getCoreLogs(opDesc, opUrl, opType, opFun, getLogUserAndIpAddr() + opText));
	}

	*//**
	 * 添加系统日志到数据库
	 * 
	 * @param opDesc
	 * @param opUrl
	 * @param opType
	 * @param opFun
	 * @param opText
	 * @return
	 *//*
	public int insertSyslogs(String opDesc, String opUrl, String opType, String opFun, String opText)
			throws DBException, SysException {
		return sysLogsServie.insert(this.getSysLogs(opDesc, opUrl, opType, opFun, getLogUserAndIpAddr() + opText));
	}

	*//**
	 * 添加系统日志与核心日志到数据库
	 * 
	 * @param opDesc
	 * @param opUrl
	 * @param opType
	 * @param opFun
	 * @param opText
	 * @return 返回存入数据库的状态 corelogsReturn是存储核心日志的状态，syslogsReturn是存储系统日志的状态
	 *//*
	public Map<String, Integer> insertCorelogsAndSyslogs(String opDesc, String opUrl, String opType, String opFun,
			String opText) throws DBException, SysException {
		Map<String, Integer> result = new HashMap<>();
		result.put("corelogsReturn", this.insertCorelogs(opDesc, opUrl, opType, opFun, opText));
		result.put("syslogsReturn", this.insertSyslogs(opDesc, opUrl, opType, opFun, opText));
		return result;
	}

	public OrgInfo getCurrentUserOrgInfo() throws DBException, SysException {
		OrgInfo o = null;
		o = orgInfoService.selectOneOrgInfo(user.getOrgId());
		return o;
	}

	private SysLogs getSysLogs(String opDesc, String opUrl, String opType, String opFun, String opText)
			throws DBException, SysException {
		// 增加操作日志
		// 查询机构代码
		OrgInfo orgInfo = getCurrentUserOrgInfo();
		String orgCode = "";
		if (orgInfo != null) {
			orgCode = orgInfo.getOrgCode();
		}
		// 防止描述过长
		opText = opText.length() > 500 ? opText.substring(0, 499) : opText;

		// 封装日志
		SysLogs sysLogs = new SysLogs();

		String id = UUIDGenerator.generate();
		sysLogs.setLogId(id);
		sysLogs.setOrgCode(orgCode);
		sysLogs.setUserNo(user.getUserNo());
		sysLogs.setUserName(user.getUserName());
		sysLogs.setIpAddr(user.getBindAddressNum());
		sysLogs.setBusCode("");
		sysLogs.setIpAddr(getClientIpAddress());
		sysLogs.setOpDesc(opDesc);
		sysLogs.setOpUrl(opUrl);
		sysLogs.setOpType(opType);
		sysLogs.setOpFun(opFun);
		sysLogs.setOpText(opText);
		return sysLogs;
	}

	private CoreLogs getCoreLogs(String opDesc, String opUrl, String opType, String opFun, String opText)
			throws DBException, SysException {
		// 增加操作日志
		// 查询机构代码
		OrgInfo orgInfo = getCurrentUserOrgInfo();
		String orgCode = "";
		if (orgInfo != null) {
			orgCode = orgInfo.getOrgCode();
		}
		// 防止描述过长
		opText = opText.length() > 500 ? opText.substring(0, 499) : opText;

		// 封装日志
		CoreLogs coreLogs = new CoreLogs();
		String id = UUIDGenerator.generate();
		coreLogs.setLogId(id);
		coreLogs.setOrgCode(orgCode);
		coreLogs.setUserNo(user.getUserNo());
		coreLogs.setUserName(user.getUserName());
		coreLogs.setIpAddr(user.getBindAddressNum());
		coreLogs.setBusCode("");
		coreLogs.setIpAddr(getClientIpAddress());
		coreLogs.setOpDesc(opDesc);
		coreLogs.setOpUrl(opUrl);
		coreLogs.setOpType(opType);
		coreLogs.setOpFun(opFun);
		coreLogs.setOpText(opText);
		return coreLogs;
	}*/

	public MessageModel getLoginSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.LOGIN_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.LOGIN_SUCCESS_MSG);
		return model;
	}

	public MessageModel getLogoutSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.LOGOUT_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.LOGOUT_SUCCESS_MSG);
		return model;
	}

	public MessageModel getSignUpSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.SIGNUP_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.SIGNUP_SUCCESS_MSG);
		return model;
	}

	public MessageModel getAuthorizationSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.AUTHORIZATION_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.AUTHORIZATION_SUCCESS_MSG);
		return model;
	}

	public MessageModel getAuthenticationSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.AUTHENTICATION_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.AUTHENTICATION_SUCCESS_MSG);
		return model;
	}

	public MessageModel getQuerySuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.QUERY_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.QUERY_SUCCESS_MSG);
		return model;
	}

	public MessageModel getDeleteSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.DELETE_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.DELETE_SUCCESS_MSG);
		return model;
	}

	public MessageModel getUpdateSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.UPDATE_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.UPDATE_SUCCESS_MSG);
		return model;
	}

	public MessageModel getAddSuccessNotice(MessageModel model) {
		model.setStatusCode(MessageModel.ADD_SUCCESS_CODE);
		model.setStatusInfo(MessageModel.ADD_SUCCESS_MSG);
		return model;
	}

	public MessageModel getLoginFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.LOGIN_FAILURE_CODE);
		model.setStatusInfo(MessageModel.LOGIN_FAILURE_MSG);
		return model;
	}

	public MessageModel getSignUpFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.SIGNUP_FAILURE_CODE);
		model.setStatusInfo(MessageModel.SIGNUP_FAILURE_MSG);
		return model;
	}

	public MessageModel getAuthorizationFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.AUTHORIZATION_FAILURE_CODE);
		model.setStatusInfo(MessageModel.AUTHORIZATION_FAILURE_MSG);
		return model;
	}

	public MessageModel getAuthenticationFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.AUTHENTICATION_FAILURE_CODE);
		model.setStatusInfo(MessageModel.AUTHENTICATION_FAILURE_MSG);
		return model;
	}

	public MessageModel getQueryFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.QUERY_FAILURE_CODE);
		model.setStatusInfo(MessageModel.QUERY_FAILURE_MSG);
		return model;
	}

	public MessageModel getDeleteFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.DELETE_FAILURE_CODE);
		model.setStatusInfo(MessageModel.DELETE_FAILURE_MSG);
		return model;
	}

	public MessageModel getUpdateFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.UPDATE_FAILURE_CODE);
		model.setStatusInfo(MessageModel.UPDATE_FAILURE_MSG);
		return model;
	}

	public MessageModel getAddFailureNotice(MessageModel model) {
		model.setStatusCode(MessageModel.ADD_FAILURE_CODE);
		model.setStatusInfo(MessageModel.ADD_FAILURE_MSG);
		return model;
	}

	public MessageModel getSystemErrorNotice(MessageModel model) {
		model.setStatusCode(MessageModel.SYSTEM_ERROR_CODE);
		model.setStatusInfo(MessageModel.SYSTEM_ERROR_MSG);
		return model;
	}

	public MessageModel getDataBaseErrorNotice(MessageModel model) {
		model.setStatusCode(MessageModel.DATABASE_ERROR_CODE);
		model.setStatusInfo(MessageModel.DATABASE_ERROR_MSG);
		return model;
	}

	protected Object getSystemParamCache(String orgManageModel) {
		return "123123123";
	}
}
