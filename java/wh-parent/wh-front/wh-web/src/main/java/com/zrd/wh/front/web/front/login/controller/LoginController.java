package com.zrd.wh.front.web.front.login.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.core.base.constant.SysConstant;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.global.GlobalVariable;
import com.zrd.wh.core.base.tool.DateHelper;
import com.zrd.wh.core.base.tool.StringUtil;
import com.zrd.wh.core.base.tool.SysLogger;
import com.zrd.wh.core.front.entity.auth.LoginError;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
import com.zrd.wh.core.front.entity.auth.Resource;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IOrgInfoService;
import com.zrd.wh.core.front.service.auth.IResourceService;
import com.zrd.wh.core.front.service.auth.IUserInfoService;
import com.zrd.wh.front.web.config.annotation.NotCheckedMethod;
import com.zrd.wh.front.web.config.controller.BaseController;
import com.zrd.wh.front.web.front.login.entity.UserLoginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.rpc.RpcException;

@Controller
@Scope("prototype")
@RequestMapping("/csmsp")
@CrossOrigin
public class LoginController extends BaseController {

	private static final long serialVersionUID = -4364851594600291021L;

	@Autowired
	private IResourceService resourceService;

	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IOrgInfoService orgInfoService;
	
	private final String indexUrl = Constant.TO_INDEX_URL;
	private final String updatePwdUrl = Constant.TO_UPDATE_PWD_URL;
	
	/**
	 * 正常登陆页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toLogin")
	@NotCheckedMethod//自定义注解，使用后拦截器不再拦截该方法
	public String toLogin(ModelMap model, HttpSession session) {
		return "login";
	}
	
	/**
	 * 用户跳转登录页面
	 * 		1.正常跳转，说明用户在登录时填写信息有误跳转
	 * 		2.session超时跳转，说明用户在规定的时间内没有任何操作
	 * 		3.用户由于误操作被锁定，或者被管理员停用，若用户仍然在登录状态，
	 * 			此时用户登录的session还存在
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/toErrorLogin")
	@NotCheckedMethod//自定义注解，使用后拦截器不再拦截该方法
	public String toErrorLogin(ModelMap model, HttpSession session) {
		User loginUser = (User)session.getAttribute(Constant.LOGIN_USER);
		String userStatus = "";
		if(loginUser != null) {//当用户在存在时就设置session失效
			UserLoginStatus o = (UserLoginStatus) GlobalVariable.loginMap.get(loginUser.getUserId());
			userStatus = o.getStatus();
			Object currentStatus = session.getAttribute(Constant.USER_STATUS);
			if(currentStatus != null){
				userStatus = currentStatus.toString();
			}
		}
		if(Constant.USER_STATUS_LOCK.equals(userStatus)) {
			model.put("message", Constant.USER_STATUS_LOCK_MSG);
		}
		if(Constant.USER_STATUS_STOP.equals(userStatus)) {
			model.put("message", Constant.USER_STATUS_STOP_MSG);
		}
		if(Constant.USER_STATUS_RPLS.equals(userStatus)) {
			model.put("message", Constant.USER_STATUS_RPLS_MSG);
		}
		session.invalidate();
		return "login";
	}
	
	@RequestMapping("/index")
	//@NotCheckedMethod//自定义注解，使用后拦截器不再拦截该方法
	public String login(ModelMap modelMap, HttpSession session) {
		//判断用户是否已登录系统
		User currentUser = (User)session.getAttribute(Constant.LOGIN_USER);
		if(currentUser!=null) {
			modelMap.put("username", currentUser.getUserName());
			modelMap.put("menuInfo", session.getAttribute("menuInfo"));
			return "main/index";
		}else {
			return "login";
		}
	}
	
	@RequestMapping(value="/toOnlyUpdatePassword", method = RequestMethod.GET)
	public String toOnlyUpdatePassword() {
		return "/main/updatePwd";
	}
	
	@RequestMapping(value="/onlyUpdatePassword", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel onlyUpdatePassword(MessageModel model, HttpServletRequest request,
                                           @RequestParam("oldPassWord") String oldPassWord,
                                           @RequestParam("passWord") String passWord) {
		
		model = super.getUpdateFailureNotice(model);
		User currentUser = super.getUser();
		String userCode = currentUser.getUserNo();
		
		try {
			String newPwd = instance.encryptToString(passWord);
			String oldPwd = instance.encryptToString(oldPassWord);
			User loginUser = userInfoService.selectLoginUser(userCode);
			
			if(loginUser==null) {
				return model;
			}
			
			if(!loginUser.getUserPwd().equals(oldPwd)) {
				model.setStatusInfo("密码错误");
				model.setStatusCode("error");
				return model;
			}
			//修改密码时间
			loginUser.setUserPwdDay(DateHelper.getNewDate(DateHelper.getCurrentStrDate()));
			loginUser.setUserPwd(newPwd);//更新密码
			int result = userInfoService.updateUser(loginUser);
			if(result == 0) {
				return model;
			}
			request.getSession().setAttribute(Constant.LOGIN_USER, loginUser);
			//系统日志，核心日志
			/*super.insertCorelogsAndSyslogs("用户修改密码",
						"/user/onlyUpdatePassword", Constant.LOG_TYPE_UPDATE, "onlyUpdatePassword", "用户代码:"+loginUser.getUserNo()+",用户名称:"+loginUser.getUserName());*/
			model = super.getUpdateSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->onlyUpdatePassword:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("修改密码错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->onlyUpdatePassword:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("修改密码错误");
			e2.printStackTrace();
		} catch (Exception e) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->onlyUpdatePassword:", e);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("修改密码加密出错");
			e.printStackTrace();
		}
		
		return model;
	}
	
	@RequestMapping(value = "/toUpdatePwdPage")
	@NotCheckedMethod//自定义注解，使用后拦截器不再拦截该方法
	public String toUpdatePwdPage(ModelMap modelMap, HttpSession session) {
		String userCode = session.getAttribute("userCode").toString();
		if(StringUtil.isEmpty(userCode)) {
			return "login";
		}else {
			modelMap.put("userCode", userCode);
			modelMap.put("message", session.getAttribute("message"));
		}
		return "updatePwd";
	}
	
	/**
	 * 用户修改密码
	 * @param model
	 * @param request 
	 * @param userCode 用户编号
	 * @param oldPassWord 旧密码
	 * @param passWord 新密码
	 * @return
	 */
	@RequestMapping(value="/updatePwd", method=RequestMethod.POST)
	@ResponseBody
	@NotCheckedMethod//自定义注解，使用后拦截器不再拦截该方法
	public MessageModel updatePwd(MessageModel model, HttpServletRequest request,
			@RequestParam("userCode") String userCode, 
			@RequestParam("oldPassWord") String oldPassWord,
			@RequestParam("passWord") String passWord) {
		model = super.getLoginFailureNotice(model);
		HttpSession session = request.getSession();
		Map<String, Object> data = model.getData();
		
		Map<String, Object> userVerJson = null;
		String currentIp = super.getClientIpAddress();
		try {
			String newPwd = instance.encryptToString(passWord);
			String oldPwd = instance.encryptToString(oldPassWord);
			User loginUser = userInfoService.selectLoginUser(userCode);
			
			userVerJson = userVerification(loginUser, oldPwd, currentIp);
			//登陆用户校验
			if("0".equals(String.valueOf(userVerJson.get("msgType")))) {//校验通过
				//密码修改密码时间设置为当日
				loginUser.setUserPwdDay(DateHelper.getNewDate(DateHelper.getCurrentStrDate()));
				loginUser.setUserPwd(newPwd);//更新密码
				loginUser.setPwdInvalid(Constant.PASSWORD_INVLID_YES);//下次登陆不需要再去修改密码
                loginUser.setLstLoginDate(new Date());
				userInfoService.updateUser(loginUser);
				
				model = super.getLoginSuccessNotice(model);
				//加载当前用户自己的资源树
				List<Resource> loginUserResources = getLoginUserResources(loginUser.getUserId(), "");
				final String menuInfo = getMenuInfo(loginUserResources, session);
				//存session
				session.setAttribute(Constant.LOGIN_USER, loginUser);
				//设置用户超时时间此处以秒为单位
				session.setMaxInactiveInterval(
						this.getSystemParamIntValue(SysConstant.LOGINTIMEOUT)*60);
				session.setAttribute("menuInfo", menuInfo);
				
				//存放到全局的loginMap中
				UserLoginStatus uls = new UserLoginStatus(Constant.USER_STATUS_NORMAL, session);
				GlobalVariable.loginMap.put(loginUser.getUserId(),  uls);

				SysLogger.info("menuInfo：" + menuInfo + ": " +loginUser.getUserName());
				data.put("toPage", indexUrl);
				return model;
			}else {//校验未通过,返回密码修改页面
				data.put("userCode", userCode);
				data.put("message", userVerJson.get("message"));
				return model;
			}
		}catch (DBException e) {
			SysLogger.error("LoginController->updatePwd:", e);
			e.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("更新密码失败");
		} catch (SysException e) {
			SysLogger.error("LoginController->updatePwd:", e);
			e.printStackTrace();
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("更新密码失败");
		}catch (Exception e){
			SysLogger.error("LoginController->updatePwd:", e);
        	e.printStackTrace();
        	model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("更新密码失败");
        }
		
		return model;
	}
	
	/**
	 * 用户登陆系统请求
	 * 
	 * @param userCode - 用户名
	 * @param passWord - 用户密码
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	@NotCheckedMethod//自定义注解，使用后拦截器不再拦截该方法
	public MessageModel login(MessageModel model, HttpServletRequest request,
			@RequestParam("userCode") String userCode, 
			@RequestParam("passWord") String passWord) {
		HttpSession session = request.getSession();
		Map<String, Object> userVerJson = null;
		Map<String, Object> data = model.getData();
		//获取客户端ip地址
		String currentIp = super.getClientIpAddress();
		try {
			// 对登陆用户密码进行加密处理
			String iPwd = instance.encryptToString(passWord);
			
			User loginUser = userInfoService.selectLoginUser(userCode);
			userVerJson = userVerification(loginUser,iPwd,currentIp);
			//登陆用户校验
			if("0".equals(String.valueOf(userVerJson.get("msgType")))) {//校验通过
				//查看当前用户登录状态如果为在线，那么顶替掉
				UserLoginStatus usl = (UserLoginStatus)GlobalVariable.loginMap.get(loginUser.getUserId());
				if(usl != null && Constant.USER_STATUS_NORMAL.equals(usl.getStatus())){
					HttpSession oldSession = usl.getSession();
					//设置之前用于已被顶替的标识，该用户下次操作直接退出系统
					try {
						oldSession.setAttribute(Constant.USER_STATUS,Constant.USER_STATUS_RPLS);
					} catch (Exception e) {
					}
				}
				int pdType = Integer.parseInt(userVerJson.get("pdType").toString());
				//密码即将过期，让用户取修改密码
				if(Constant.PASSWORD_TYPE_WILL_PAST.equals(pdType)) {
					data.put("message", userVerJson.get("message"));
					data.put("toPage", updatePwdUrl);
					session.setAttribute("message", userVerJson.get("message").toString());
					session.setAttribute("userCode", userCode);
					model.setStatusCode("300");
					model.setStatusInfo(userVerJson.get("message").toString());
					return model;
				}else if(Constant.PASSWORD_TYPE_REAL_PAST.equals(pdType)) {
					data.put("message", userVerJson.get("message"));
					data.put("userCode", userCode);
					model.setStatusCode("400");
					model.setStatusInfo(userVerJson.get("message").toString());
					return model;
				}

				//首次登陆或者重置密码时，跳转到修改密码页面
				if(Constant.PASSWORD_INVLID_NO.equals(loginUser.getPwdInvalid())) {
					data.put("message", userVerJson.get("message"));
					data.put("toPage", updatePwdUrl);
					session.setAttribute("userCode", userCode);
					session.setAttribute("message", "重置密码或首次登陆需要修改密码");
					model.setStatusCode("300");
					model.setStatusInfo("重置密码或首次登陆需要修改密码");
					return model;
				}

				
				model = super.getLoginSuccessNotice(model);
                //更新用户最后登录时间
                loginUser.setLstLoginDate(new Date());
                userInfoService.updateUser(loginUser);

				//根据用户取权限取
				List<Resource> loginUserResources = getLoginUserResources(loginUser.getUserId(), "");
				
				session.setAttribute(Constant.LOGIN_USER, loginUser);
				//设置用户超时时间此处以秒为单位
				session.setMaxInactiveInterval(
						this.getSystemParamIntValue(SysConstant.LOGINTIMEOUT)*60);
				final String menuInfo = getMenuInfo(loginUserResources, session);
				session.setAttribute("menuInfo", menuInfo);
				
				//存放到全局的loginMap中
				UserLoginStatus uls = new UserLoginStatus(Constant.USER_STATUS_NORMAL, session);
				GlobalVariable.loginMap.put(loginUser.getUserId(),  uls);
				
				SysLogger.info("menuInfo：" + menuInfo);
				data.put("toPage", indexUrl);
				data.put("menu", menuInfo);
				return model;
			}else {//校验未通过,返回登陆页面
				data.put("message", userVerJson.get("message"));
				data.put("userCode", userCode);
				//当用户存在时，就记录登录错误信息
				if(null != loginUser) {
					LoginError loginError = new LoginError();
					loginError.setUserId(loginUser.getUserId());
					loginError.setLstIp(currentIp);
					userInfoService.insertOrUpdateLoginError(loginError);
				}
				model.setStatusCode("400");
				model.setStatusInfo(userVerJson.get("message").toString());
				return model;
			}
		} catch (DBException e2) {
			SysLogger.error("LoginController->login:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("数据库查询异常");
		} catch (SysException e2) {
			SysLogger.error("LoginController->login:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("系统运行异常");
			e2.printStackTrace();
		} catch (Exception e) {
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("登录异常");
			if(e instanceof RpcException) {
				model.setStatusInfo("登录超时");
			}
			SysLogger.error("LoginController->login:", e);
			e.printStackTrace();
		}
		
		return model;
		
	}
	
	@RequestMapping(value="/loginout", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel loginout(MessageModel model, HttpServletRequest request) {
		model = super.getLogoutSuccessNotice(model);
		User loginUser = super.getUser();
		if(loginUser!=null) {
			request.getSession().invalidate();
		}
		return model;
	}
	
	
	
	/**
	 * 用户想要离开页面时存储该值，配合lazyLogout一起判断用户是刷新页面还是关闭页面
	 * @param session
	 * @param closeFlag
	 */
	@RequestMapping(value="/isLeavePage", method = RequestMethod.POST)
	@ResponseBody
	public void isLeavePage(HttpSession session,
			@RequestParam("closeFlag") String closeFlag) {
		session.setAttribute("closeFlag", closeFlag);
	}
	
	/**
	 * 在用户离开页面时，延迟十秒让用户操作，之后判断用户是留在页面还是关闭页面
	 * @param session
	 */
	@RequestMapping(value="/lazyLogout", method = RequestMethod.POST)
	@ResponseBody
	public void lazyLogout(HttpSession session, 
			@RequestParam("closeFlag") String closeFlag) {
		
		if(!StringUtil.isEmpty(closeFlag)) {
			session.setAttribute("closeFlag", closeFlag);
		}
		
		Thread lazyLogoutThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logoutByCloseFlag(session);
			}
			
		});
		lazyLogoutThread.start();
	}
	
	/**
	 * 等待用户关闭浏览器之后执行清除session
	 * @param session
	 */
	public void logoutByCloseFlag(HttpSession session) {
		String closeFlag = session.getAttribute("closeFlag").toString();
		if("1".equals(closeFlag)) {
			session.invalidate();
		}
	}
	/**
	 * 获取用户资源树
	 * @param userId 用户id
	 * @param pResId 父节点  可不传
	 * @return
	 * @throws SysException 
	 * @throws DBException 
	 */
	private List<Resource> getLoginUserResources(String userId, String pResId) throws DBException, SysException{
		Map<String, String> param = new HashMap<String, String>();
		if(userId != null && !"".equals(userId)) {
			param.put("userId", userId);
		}
		if(pResId != null && !"".equals(pResId)) {
			param.put("pResId", pResId);
		}
		
		return resourceService.selectAllResourceByUserIdAndpResId(param);
	}
	
	/**
	 * 
	 * @param loginUser - 登陆用户信息
	 * @param inPwd - 加密后的密码
	 * @param currentIp 
	 * @return json
	 * @jsonContent 
	 *   
	 */
	private Map<String, Object> userVerification(User loginUser, String inPwd, String currentIp)  throws DBException, SysException {
        Map<String, Object> json = new HashMap<>();
		//登录配置默认值，避免获取不到时依然可用
		int timeInterval = 30, loginErrorLimit = 5;
		
		if (null != loginUser) {
			
			//查看当前用户登录的ip是否在允许ip范围内，如果没有限制填写那么不做限制
			String bindAddressNums = loginUser.getBindAddressNum();
			if(!StringUtil.isEmpty(bindAddressNums)){
				//若不在当前绑定ip中，那么跳出，提示用户
				if(!bindAddressNums.contains(currentIp)) {
					json.put("msgType", Constant.USER_LOGIN_IP_ERROR);
					json.put("message", Constant.USER_LOGIN_IP_ERROR_MSG);
					return json;
				}
			}
			//管理员直接pass
			if("admin".equals(loginUser.getUserNo().trim())){
				if(loginUser.getUserPwd().trim().equals(inPwd)){
                    OrgInfo currentOrg = orgInfoService.selectOneOrgInfo(loginUser.getOrgId());
                    loginUser.setOrgCode(currentOrg.getOrgCode());
                    loginUser.setpOrgId(currentOrg.getpOrgId());
					json.put("msgType", Constant.USER_LOGIN_SUCCESS);
					json.put("pdType", Constant.PASSWORD_TYPE_NORMAL);
					json.put("message", "欢迎您！" + loginUser.getUserName());
					return json;
				}else{
					json.put("msgType", Constant.USER_LOGIN_PWD_ERROR);
					json.put("message", Constant.USER_LOGIN_PWD_ERROR_MSG);
					return json;
				}
			}
			//获取系统中配置用户登录次数
			Integer pLoginErrorLimit = this.getSystemParamIntValue(SysConstant.LOGINERRORLIMIT);
			if(pLoginErrorLimit != null) {
				loginErrorLimit = pLoginErrorLimit.intValue();
			}
			
			LoginError loginError = userInfoService.selectLoginErrorInfo(loginUser.getUserId());
			//判断登录次数，超过五次那个就锁定该用户
			if(loginError != null && loginError.getUserErrNum()>loginErrorLimit) {
				json.put("msgType", Constant.USER_LOGIN_ERR_MORE);
				json.put("message", new String(Constant.USER_LOGIN_ERR_MORE_MSG).replace("LIMIT", String.valueOf(loginErrorLimit)));
				//锁定用户
				String userLockSts = loginUser.getUserLockSts();
				if(!Constant.USER_LOCK_STS_LOCK.equals(userLockSts)) {//用户已被锁定则不管他
					loginUser.setUserLockSts(Constant.USER_LOCK_STS_LOCK);
					userInfoService.updateUser(loginUser);
				}
				return json;
			}
			//用户当前的状态不为1(正常)
			if(Constant.USER_STOP_STS_BY_SYSTEM.equals(loginUser.getUserSts())) {
				json.put("msgType", Constant.USER_LOGIN_USER_STS_ERROR);
				json.put("message", Constant.USER_LOGIN_USER_STS_ERROR_MSG);
				return json;
			} else if(Constant.USER_STOP_STS_BY_NO_LOGIN.equals(loginUser.getUserSts())) {
				json.put("msgType", Constant.USER_LOGIN_USER_STS_ERROR);
				json.put("message", Constant.USER_LOGIN_USER_STS_ERROR_MSG_NO_LOGIN);
				return json;
			} else if(Constant.USER_STOP_STS_BY_NO_UPD_PWD.equals(loginUser.getUserSts())) {
				json.put("msgType", Constant.USER_LOGIN_USER_STS_ERROR);
				json.put("message", Constant.USER_LOGIN_USER_STS_ERROR_MSG_NO_UPD_PWD);
				return json;
			} else if(!Constant.USER_LOCK_STS_NORMAL.equals(loginUser.getUserLockSts())) {//用户当前的锁定状态不为0(正常)
				json.put("msgType", Constant.USER_LOGIN_LOCK_STS_ERROR);
				json.put("message", Constant.USER_LOGIN_LOCK_STS_ERROR_MSG);
				return json;
			} else if (loginUser.getUserPwd().trim().equals(inPwd)) {
				//查看用户机构状态是否正常
				OrgInfo currentOrg = orgInfoService.selectOneOrgInfo(loginUser.getOrgId());
				if(!"1".equals(currentOrg.getOrgSts())) {
					json.put("msgType", Constant.USER_LOGIN_ORG_ERROR);
					json.put("message", Constant.USER_LOGIN_ORG_ERROR_MSG);
					return json;
				}
				loginUser.setOrgCode(currentOrg.getOrgCode());
				loginUser.setpOrgId(currentOrg.getpOrgId());
				Integer pTimeInterval = this.getSystemParamIntValue(SysConstant.TIMEINTERVAL);
				// 读取登陆用户密码过期时间间隔
				if (pTimeInterval != null) {
					timeInterval = pTimeInterval.intValue();
				}
				int currentTimeInterval = DateHelper.getTimeDistance(loginUser.getUserPwdDay(), new Date());
				
				if (currentTimeInterval < timeInterval) {
					// 登陆用户密码在正常日期范围内
					json.put("msgType", Constant.USER_LOGIN_SUCCESS);
					json.put("pdType", Constant.PASSWORD_TYPE_NORMAL);
					json.put("message", "欢迎您！" + loginUser.getUserName());
				}else if (currentTimeInterval == timeInterval) {
					// 登陆用户密码即将过期
					json.put("msgType", Constant.USER_LOGIN_SUCCESS);
					json.put("pdType", Constant.PASSWORD_TYPE_WILL_PAST);
					json.put("message", Constant.USER_LOGIN_PWD_WILL_PAST);
					return json;
				} else {// 登陆用户密码已过期
					json.put("msgType", Constant.USER_LOGIN_SUCCESS);
					json.put("pdType", Constant.PASSWORD_TYPE_REAL_PAST);
					json.put("message", Constant.USER_LOGIN_PWD_REAL_PAST);
/*					//停用用户
					loginUser.setUserSts(Constant.USER_STS_STOP);
					userInfoService.updateUser(loginUser);
*/					return json;
				}
			} else {
				// 用户密码错误
				json.put("msgType", Constant.USER_LOGIN_PWD_ERROR);
				json.put("message", Constant.USER_LOGIN_PWD_ERROR_MSG);
				return json;
			}
		} else {
			// 用户不存在
			json.put("msgType", Constant.USER_LOGIN_USR_ERROR);
			json.put("message", Constant.USER_LOGIN_USR_ERROR_MSG);
			return json;
		}
		return json;
	}
	
	
	/**
	 * 登陆用户有权操作的菜单-StringBuffer版
	 * @param session 
	 * @param list - 登陆用户有权操作的菜单列表
	 * @return
	 */
	private String getMenuInfo(List<Resource> list, HttpSession session) {
		StringBuffer menu = new StringBuffer();
		StringBuffer menuSubLevel1 = new StringBuffer();
		StringBuffer menuSubLevel2 = new StringBuffer();
		if(list != null && list.size() > 0) {
			for(Resource res : list) {
				if("0".equals(res.getpResId())) {
					menu.append(",{\"id\":\"").append(res.getResId()).append("\",\"name\":\"").append(res.getResName()).
					append("\",\"url\":\"").append((res.getResUrl() == null? "#":res.getResUrl())).append("\",\"ico\":\"\"");
					for (Resource resLevel2 : list) {
						if (resLevel2.getpResId().equals(res.getResId())) {
							menuSubLevel1
							.append(",{\"id\":\"").append(resLevel2.getResId())
							.append("\",\"name\":\"").append(resLevel2.getResName())
							.append("\",\"url\":\"").append((resLevel2.getResUrl() == null? "#":resLevel2.getResUrl()))
							.append("\"");
							for (Resource resLevel3 : list) {
								if (resLevel3.getpResId().equals(resLevel2.getResId())) {
									menuSubLevel2
									.append(",{\"id\":\"").append(resLevel3.getResId())
									.append("\",\"name\":\"").append(resLevel3.getResName())
									.append("\",\"url\":\"").append((resLevel3.getResUrl() == null? "#":resLevel3.getResUrl()))
									.append("\"}");
								}
							}
							int level2Len = menuSubLevel2.length();
							if(level2Len > 0) {
								menuSubLevel1.append(",\"submenu\":[").append(menuSubLevel2.deleteCharAt(0)).append("]}");
							} else {
								menuSubLevel1.append("}");
							}
							menuSubLevel2.delete(0, level2Len);
						}
					}
					int level1Len = menuSubLevel1.length();
					if(level1Len > 0) {
						menu.append(",\"submenu\":[").append(menuSubLevel1.deleteCharAt(0)).append("]}");
					} else {
						menu.append("}");
					}
					menuSubLevel1.delete(0, level1Len);
				}
			}
			menu.insert(0, "[").deleteCharAt(1).append("]");
		} else {
			return null;
		}
		return menu.toString();
	}
}
