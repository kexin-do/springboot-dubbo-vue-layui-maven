package com.zrd.wh.front.web.front.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.tool.DateHelper;
import com.zrd.wh.core.base.tool.StringUtil;
import com.zrd.wh.core.base.tool.SysLogger;
import com.zrd.wh.core.base.tool.UUIDGenerator;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
import com.zrd.wh.core.front.entity.auth.Role;
import com.zrd.wh.core.front.entity.auth.RoleAndUser;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IOrgInfoService;
import com.zrd.wh.core.front.service.auth.IRoleService;
import com.zrd.wh.core.front.service.auth.IUserInfoService;
import com.zrd.wh.front.web.config.controller.BaseController;
import com.zrd.wh.front.web.front.login.entity.UserLoginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@Scope("prototype")
@RequestMapping("/user")
public class UserController extends BaseController {
 
	private static final long serialVersionUID = 5008631348840035238L;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IOrgInfoService orgInfoService;
	@Autowired
	private IRoleService roleService;

	/**
	 * 跳转用户管理页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public MessageModel userManage(MessageModel model, ModelMap modelMap) {
		User user = this.getUser();
		String orgId = user.getOrgId();
		String userLevel = user.getUserLevel();
		List<OrgInfo> orgInfoList = new ArrayList<>();
		try {
			OrgInfo orgInfo = orgInfoService.selectOneOrgInfo(orgId);
			if("S".equals(userLevel)){
				//如果当前登录用户为超级管理员
				//则显示本级机构
				orgInfoList.add(orgInfo);
				 
			}else if("A".equals(userLevel)){
				//如果当前登录用户为管理员
				Map<String, String> orgMap = new HashMap<String, String>();
				orgMap.put("orgId", orgId);
				//机构管理模式 1：管理下级 2：总行管理
				String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
				if("1".equals(orgManageModel)){
					//管理模式为下级管理
					//则显示所有下一级机构及本级机构
					orgInfoList = orgInfoService.selectSubOrgInfoAll(1, 1000, orgMap).getList();
					orgInfoList.add(orgInfo);
					modelMap.put("flag", "1");//机构管理模式 1：管理下级
				}
				if("2".equals(orgManageModel)){
					//管理模式为总行统一管理并且为总行管理员，则查询总行下所有机构
					modelMap.put("flag", "2");//机构管理模式  2：总行管理
					if(super.getUser().getpOrgId()==null||"".equals(super.getUser().getpOrgId())){
						orgInfoList = orgInfoService.selectHeadSubOrgInfo(1, 1000, orgMap).getList();
					}else{
						modelMap.put("flag", "3");//没有查询权限
					}
				}
				
			}
			modelMap.put("orgInfoList", orgInfoList);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->userManage:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("跳转到用户管理页面错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->userManage:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("跳转到用户管理页面错误");
			e2.printStackTrace();
		}
		
		return model;
	}

	/**
	 * 用户信息查询列表
	 * @param rows
	 * @param page
	 * @param orgId
	 * @param userNo
	 * @param userName
	 * @param userSts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public MessageModel userSelect(MessageModel model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") String rows,
			@RequestParam(required = false, value = "pageNum", defaultValue = "1") String page,
			@RequestParam(required = false, value = "orgId") String orgId,
			@RequestParam(required = false, value = "userNo") String userNo,
			@RequestParam(required = false, value = "userName") String userName,
			@RequestParam(required = false, value = "userSts") String userSts) {
		Map<String, Object> result = model.getData();
		PageInfo<User> userAndOrgInfoList = new PageInfo<User>();
		User user = this.getUser();
		String userOrgId = user.getOrgId();
		String userUserLevel = user.getUserLevel();
		try {
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->userSelect:", "orgId:"+orgId+
					"  userNo:"+userNo+"  userName:"+userName+"  userSts:"+userSts);
			Map<String, String> userMap = new HashMap<String, String>();
			if (!StringUtil.isEmpty(userNo)) {
				userMap.put("userNo", userNo.trim());
			}
			if (!StringUtil.isEmpty(userName)) {
				userMap.put("userName", userName.trim());
			}
			if (!StringUtil.isEmpty(userSts)) {
				userMap.put("userSts", userSts.trim());
			}
			if("S".equals(userUserLevel)){
				//如果为超级管理员，则默认为本级机构
				userMap.put("orgId", userOrgId);
				userMap.put("userLevel", "A");
				userAndOrgInfoList = userInfoService.selectUserAndOrgInfo(Integer.parseInt(page),
						Integer.parseInt(rows), userMap);
			}else if("A".equals(userUserLevel)){
				//如果为管理员
				String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
				if("1".equals(orgManageModel)){
					//管理模式为下级管理 
					if (!StringUtil.isEmpty(orgId)) {
						userMap.put("orgId", orgId.trim());
						if(orgId.trim().equals(userOrgId)){
							//如果用户选择本级机构条件，则查询本级普通用户
							userMap.put("userLevel", "B");
							userAndOrgInfoList = userInfoService.selectUserAndOrgInfo(Integer.parseInt(page),
									Integer.parseInt(rows), userMap);
						}else{
							//如果用户选择下级机构条件，则查询下级管理员
							userMap.put("userLevel", "A");
							userAndOrgInfoList = userInfoService.selectUserAndOrgInfo(Integer.parseInt(page),
									Integer.parseInt(rows), userMap);
						}
						
					}else{
						//如果用户没有选择机构，则查询所有本级用户及下一级管理员
						userMap.put("orgId", userOrgId);
						userAndOrgInfoList = userInfoService.selectUserAndManager(Integer.parseInt(page),
								Integer.parseInt(rows), userMap);
					}
				}
				if("2".equals(orgManageModel)){
					//管理模式为总行统一管理并且为总行管理员
					if(StringUtil.isEmpty(user.getpOrgId())){
							userMap.put("orgId", orgId.trim());
							//如果用户选择机构，则查询本级普通用户和管理员
							userAndOrgInfoList = userInfoService.selectUserAndOrgInfo(Integer.parseInt(page),
									Integer.parseInt(rows), userMap);
							
					}
				}
				
			}
		 
			//获取用户列表信息
			result.put("rows", userAndOrgInfoList.getList());
			//获取总条数
			result.put("total", userAndOrgInfoList.getTotal());
			
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->userSelect:","result:"+result);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->userSelect:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("查询用户列表错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->userSelect:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("查询用户列表错误");
			e2.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/selectOne", method = RequestMethod.POST)
	public MessageModel selectOne(MessageModel model,
			@RequestParam(value = "userId", required = true) String userId) {
		model = super.getQueryFailureNotice(model);
		try {
			User user = userInfoService.selectOneUser(userId);
			if(user == null){
				return model;
			}
			user.setUserPwd(super.instance.decryptToString(user.getUserPwd()));
			model.getData().put("userInfo", user);
			model = super.getQuerySuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->userSelect:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
			model.setStatusInfo("查询用户信息错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->userSelect:", e2);
			model = super.getSystemErrorNotice(model);
			model.setStatusInfo("查询用户信息错误");
			e2.printStackTrace();
		} catch (Exception e) {
			model = super.getSystemErrorNotice(model);
			model.setStatusInfo("查询用户信息错误");
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * 增加用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public MessageModel addUser(MessageModel model, User user) {
		// 添加用户
		model = super.getAddFailureNotice(model);
		User loginUser = this.getUser();
		String loginOrgId = loginUser.getOrgId();
		String loginUserNo = loginUser.getUserNo();
		String loginUserLevel = loginUser.getUserLevel();
		try {
			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("userNo", user.getUserNo());
			List<User> userNos = userInfoService.selectUser(userMap);
			if(userNos!=null&&userNos.size()>0){
				model.setStatusInfo("该用户已存在,用户代码重复");
				return model;
			}
			String newpwd = instance.encryptToString(user.getUserPwd());
			//设置密码
			user.setUserPwd(newpwd);
			//设置上次修改密码日期
			user.setUserPwdDay(new Date());
			String ids = UUIDGenerator.generate();
			//设置用户id
			user.setUserId(ids);
			//设置创建人
			user.setCreateUser(loginUserNo);
			//设置锁定变更人
			user.setLstUser(loginUserNo);
			//设置启停变更人
			user.setStartUser(loginUserNo);
			if("S".equals(loginUserLevel)){
				//如果为超级管理员，则允许创建本级管理员
				//设置机构id为本级机构
				user.setOrgId(loginOrgId);
				//设置用户级别为管理员
				user.setUserLevel("A");
			}
			int result = userInfoService.insert(user);
			if(result == 0) {
				return model;
			}
			//增加操作日志
		/*	super.insertSyslogs("增加用户信息",
					"/user/addUser", Constant.LOG_TYPE_ADD, "addUser",
					loginUserNo+"新增用户,用户代码:"+user.getUserNo()+",用户名称:"+user.getUserName());
			*/
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->addUser:", "user:"+user.toString());
			model = super.getAddSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->addUser:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("新增用户错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->addUser:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("新增用户错误");
			e2.printStackTrace();
		} catch (Exception e) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->addUser:", e);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("新增用户时加密错误");
			e.printStackTrace();
		}
		
		return model;
	}

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public MessageModel updateUser(MessageModel model, User user){
		
		model = super.getUpdateFailureNotice(model);
		try {
			//更新用户表
			user.setUserPwd(super.instance.encryptToString(user.getUserPwd()));
			int result = userInfoService.updateUser(user);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("更新用户信息",
					"/user/updateUser", Constant.LOG_TYPE_UPDATE, "updateUser", 
					this.getUser().getUserNo()+"修改用户,用户代码:"+user.getUserNo()+",用户名称:"+user.getUserName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->updateUser:", "user:"+user.toString());
			
			model = super.getUpdateSuccessNotice(model);
			 
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->updateUser:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("更新用户信息错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->updateUser:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("更新用户信息错误");
			e2.printStackTrace();
		} catch (Exception e) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->updateUser:", e);
			model = super.getSystemErrorNotice(model);
			model.setStatusInfo("更新用户信息错误");
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 重置密码
	 * @param model
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	public MessageModel resetPwd(MessageModel model, @RequestParam("userId") String userId,
			@RequestParam("newPwd") String newPwd){
		
		model = super.getUpdateFailureNotice(model);
		try {
			//查询旧密码
			User user = userInfoService.selectOneUser(userId);
			if(user.getUserPwd().equals(instance.encryptToString(newPwd))){
				model.setStatusInfo("新旧密码不能相同");
				return model;
			}
			user.setUserPwdDay(DateHelper.getCurrentDate(DateHelper.DatePattern.StandardDatePattern));
			user.setUserPwd(instance.encryptToString(newPwd));//更新密码
			user.setPwdInvalid("0");//将密码状态设置为过期，让用户本人再次去修改密码
			int result = userInfoService.updateUser(user);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("管理员重置密码",
					"/user/resetPwd", Constant.LOG_TYPE_UPDATE, "resetPwd", 
					this.getUser().getUserNo()+"给用户"+user.getUserNo()+",重置密码");*/
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->resetPwd:", "user:"+user.toString());
			
			model = super.getUpdateSuccessNotice(model);
			 
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->resetPwd:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("重置密码错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->resetPwd:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("重置密码错误");
			e2.printStackTrace();
		} catch (Exception e) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->resetPwd:", e);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("重置密码时加密错误");
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * 删除用户信息
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public MessageModel deleteUser(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {
		
		model = super.getDeleteFailureNotice(model);
		try {
			//物理删除用户及删除用户所属的所有角色对应关系
			int result = userInfoService.delUserById(userId);
			if(result == 0) {
				return model;
			}
			
			//增加操作日志
			/*super.insertSyslogs("删除用户信息",
					"/user/deleteUser", Constant.LOG_TYPE_DELETE, "deleteUser", 
					"用户ID："+userId);*/
			
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->deleteUser:", "用户ID："+userId);
			
			model = super.getDeleteSuccessNotice(model);
		}  catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->deleteUser:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("删除用户信息错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->deleteUser:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("删除用户信息错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 用户分配角色查询
	 * @param rows
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping("/roleUserSelect")
	public MessageModel roleUserSelect(MessageModel model,
			@RequestParam(required = false, defaultValue = "10") String rows,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, value = "userId") String userId) {
		
		Map<String, Object> result = model.getData();
		try {
			Map<String, String> roleMap = new HashMap<String, String>();
			roleMap.put("roleSts", "1");
			roleMap.put("orgId", super.getUser().getOrgId());
			//查询角色列表 
			PageInfo<Role> roleInfoList = roleService.selectRoleByPage(Integer.parseInt(page),
					Integer.parseInt(rows), roleMap);
			List<Role> roleList = roleInfoList.getList();
			//查询用户角色关系
			List<RoleAndUser> roleAndUserList = roleService.selectRoleAndUser(userId);
			//增加用户角色关系状态，用于页面显示
			if(roleAndUserList!=null&&roleAndUserList.size()>0&&roleList!=null&&roleList.size()>0){
				for(RoleAndUser roleAndUser : roleAndUserList){
					for(Role role : roleList){
						if(roleAndUser.getRoleId().equals(role.getRoleId())){
							role.setRoleUserSts("1");
							break;
						}
					}
				}
			}
			
			result.put("rows", roleList);
			result.put("total", roleInfoList.getTotal());
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->roleUserSelect:",
					"roleList:"+roleList.toString()+", roleInfoList.getTotal():"+roleInfoList.getTotal());
		}catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->roleUserSelect:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("用户分配角色查询错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->roleUserSelect:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("用户分配角色查询错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 用户角色管理
	 * @param model
	 * @param ids
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public MessageModel saveRole(MessageModel model,
			@RequestParam(required = false, value = "ids") String ids,
			@RequestParam(required = false, value = "userId") String userId,
			@RequestParam(required = false, value = "userNo") String userNo) {
		
		model = super.getUpdateFailureNotice(model);
		try {
			StringBuffer roleNames = new StringBuffer();
			int result = 0;
			 
			String[] idsArr = ids.split(",");
			result = userInfoService.delAllRoles(userId);
			RoleAndUser roleAndUser = new RoleAndUser();
			roleAndUser.setUserId(userId);
			for (String roleId : idsArr) {
				roleAndUser.setRoleId(roleId);
				result = roleService.insertRoleAndUser(roleAndUser);
				String roleName = roleService.selectOneRole(roleId).getRoleName();
				roleNames.append(roleName).append(",");
			}
			if(result==0){
				return model;
			}
			//增加核心日志,操作日志
			/*super.insertCorelogsAndSyslogs("给用户分配角色",
					"/user/saveRole", Constant.LOG_TYPE_ADD, "saveRole", 
					super.getUser().getUserNo()+"给"+userNo+"分配了"+roleNames.deleteCharAt(roleNames.length()-1).toString()+"角色");*/
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->saveRole:", super.getUser().getUserNo()+"给"+userId+"分配角色，ids:"+ids);
			model =super.getUpdateSuccessNotice(model);
			
			//锁定用户时清空该用户的loginMap信息
			UserLoginStatus.changeUserLoginStatus(userId,Constant.USER_STATUS_LOCK);

		}catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->saveRole:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("给用户分配角色错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->saveRole:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("给用户分配角色错误");
			e2.printStackTrace();
		}
		
		return model;
	}
	@RequestMapping(value = "/ifgoAdd", method = RequestMethod.POST)
	public MessageModel ifgoAdd(MessageModel model) {
		model = super.getUpdateFailureNotice(model);
		String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
		if("2".equals(orgManageModel)){
			if(super.getUser().getpOrgId()!=null&&!"".equals(super.getUser().getpOrgId())){
				model = super.getUpdateSuccessNotice(model);
			}
		}
		
		return model;
	}
	/**
	 * 跳转到新增用户页面
	 * @return
	 */
	@RequestMapping(value = "/goAdd", method = RequestMethod.GET)
	public MessageModel goAdd(MessageModel model) {
		model = super.getQueryFailureNotice(model);
		List<OrgInfo> orgInfoList = new ArrayList<OrgInfo>();
		if("S".equals(this.getUser().getUserLevel())){
			//如果为超级管理员，则跳转到超级管理员新增页面
			model = super.getQuerySuccessNotice(model);
			return model;
		}
		Map<String, Object> data = model.getData();
		String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
		if("1".equals(orgManageModel)){
			//管理模式为下级管理 
			data.put("flag", "1");
		}
		if("2".equals(orgManageModel)){
			//管理模式为总行统一管理
			data.put("flag", "2");
			Map<String, String> orgMap = new HashMap<>();
			if(!StringUtil.isEmpty(super.getUser().getpOrgId())){
				orgMap.put("orgId", super.getUser().getOrgId());
				try {
					orgInfoList = orgInfoService.selectHeadSubOrgInfo(1, 1000, orgMap).getList();
					data.put("orgInfoList", orgInfoList);
					model = super.getQuerySuccessNotice(model);
				} catch (DBException e) {
					e.printStackTrace();
				} catch (SysException e) {
					e.printStackTrace();
				}
			}
		}
		return model;
	}
	
	/**
	 * 跳转到更新用户页面
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/goUpdate", method = RequestMethod.GET)
	public MessageModel goUpdate(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {
		model = super.getQueryFailureNotice(model);
		Map<String, Object> data = model.getData();
		User loginUser = this.getUser();
		String loginOrgId = loginUser.getOrgId();
		String loginUserLevel = loginUser.getUserLevel();
		model = super.getQueryFailureNotice(model);
		try {
			User user = userInfoService.selectOneUser(userId);
			data.put("user", user);
			if("S".equals(loginUserLevel)){
				//如果为超级管理员，则跳转到超级管理员修改页面
				model = super.getQuerySuccessNotice(model);
				return model;
			}
			List<OrgInfo> orgInfoList = new ArrayList<OrgInfo>();
			String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
			if("1".equals(orgManageModel)){
				//管理模式为下级管理 
				data.put("flag", "1");
				if("A".equals(user.getUserLevel())){
					//如果为管理员,则获取所有下级机构
					Map<String, String> orgMap = new HashMap<String, String>();
					orgMap.put("orgId",  loginOrgId);
					orgMap.put("orgSts",  "1");
					orgInfoList = orgInfoService.selectSubOrgInfoAll(1, 100, orgMap).getList();
				}else if("B".equals(user.getUserLevel())){
					//如果为用户,则获取本级机构
					OrgInfo orgInfo = orgInfoService.selectOneOrgInfo(loginOrgId);
					orgInfoList.add(orgInfo);
				}
			}
			if("2".equals(orgManageModel)){
				//管理模式为总行统一管理
				data.put("flag", "2");
				Map<String, String> orgMap = new HashMap<String, String>();
				if(super.getUser().getpOrgId()==null||"".equals(super.getUser().getpOrgId())){
					orgMap.put("orgId", super.getUser().getOrgId());
					orgInfoList = orgInfoService.selectHeadSubOrgInfo(1, 1000, orgMap).getList();
				}
			}
			data.put("orgInfoList", orgInfoList);
			model = super.getQuerySuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->goUpdate:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("跳转到用户更新页面错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->goUpdate:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("跳转到用户更新页面错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 跳转到重置密码页面
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/goResetPwd", method = RequestMethod.GET)
	public MessageModel goResetPwd(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {
		Map<String, Object> data = model.getData();
		data.put("userId", userId);
		return model;
	}
	
	/**
	 * 跳转到详细用户页面
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/doDetail", method = RequestMethod.GET)
	public MessageModel doDetail(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {

		model = super.getQueryFailureNotice(model);
		Map<String, Object> data = model.getData();
		try {
			User user = userInfoService.selectOneUser(userId);
			if(user == null) {
				model.setStatusInfo("查询用户出错");
				return model;
			}
			data.put("user", user);
			OrgInfo orgInfo = orgInfoService.selectOneOrgInfo(user.getOrgId());
			if (orgInfo == null) {
				model.setStatusInfo("查询用户机构出错");
				return model;
			}
			data.put("orgInfo", orgInfo);
			model = super.getQuerySuccessNotice(model);
		}catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doDetail:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("跳转到用户相信信息页面错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doDetail:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("跳转到用户详细信息页面错误");
			e2.printStackTrace();
		}
		return model;
	}

	/**
	 * 改变用户级别获取机构
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changeUserLevel", method = RequestMethod.POST)
	public MessageModel changeUserLevel(MessageModel model,
			@RequestParam(required = false, value = "userLevel") String userLevel){

		String loginOrgId = this.getUser().getOrgId();
		List<OrgInfo> orgInfoList = new ArrayList<OrgInfo>();
		model = super.getQueryFailureNotice(model);
		Map<String, Object> data = model.getData();
		try {
			if("A".equals(userLevel)){
				//如果为管理员,则获取所有下级机构
				Map<String, String> orgMap = new HashMap<String, String>();
				orgMap.put("orgId",  loginOrgId);
				orgMap.put("orgSts",  "1");
				orgInfoList = orgInfoService.selectSubOrgInfoAll(1, 100, orgMap).getList();
			}else if("B".equals(userLevel)){
				//如果为用户,则获取本级机构
				OrgInfo orgInfo = orgInfoService.selectOneOrgInfo(loginOrgId);
				orgInfoList.add(orgInfo);
			}
			data.put("orgInfoList", orgInfoList);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->changeUserLevel:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("修改用户级别错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->changeUserLevel:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("修改用户级别错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 用户启用
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public MessageModel start(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {
		
		model = super.getDeleteFailureNotice(model);
		try {
			//将用户启停状态设置为1：正常
			User users = userInfoService.selectOneUser(userId);
			User user = new User();
			user.setUserSts("1");
			user.setUserId(userId);  
			//设置启停变更人
			user.setStartUser(this.getUser().getUserNo());
			//设置启停变更时间
			user.setStartTime(new Date());

			int result = userInfoService.updateUser(user);
			if(result == 0) {
				return model;
			}
			//启用用户时，将用户的信息还原
			UserLoginStatus.changeUserLoginStatus(userId,Constant.USER_STATUS_NORMAL);
			//增加操作日志
			/*super.insertSyslogs("启用用户",
					"/user/doStart", Constant.LOG_TYPE_UPDATE, "doStart", 
					"用户ID："+userId);*/
			
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->doStart:", "用户ID："+userId);
			
			model = super.getDeleteSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doStart:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("启用用户错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doStart:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("启用用户错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 停用用户
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public MessageModel stop(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {
		
		model = super.getDeleteFailureNotice(model);
		try {
			//将用户启停状态设置为2：停用
			User user = new User();
			user.setUserSts("2");
			user.setUserId(userId);  
			//设置启停变更人
			user.setStartUser(this.getUser().getUserNo());
			//设置启停变更时间
			user.setStartTime(new Date());
			int result = userInfoService.updateUser(user);
			if(result == 0) {
				return model;
			}
			
			//停用用户时清空该用户的loginMap信息
			UserLoginStatus.changeUserLoginStatus(userId,Constant.USER_STATUS_STOP);
			//增加操作日志
			/*super.insertSyslogs("停用用户",
					"/user/doEnd", Constant.LOG_TYPE_UPDATE, "doEnd", 
					"用户ID："+userId);*/
			
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->doEnd:", "用户ID："+userId);
			
			model = super.getDeleteSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doEnd:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("停用用户错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doEnd:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("停用用户错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 解锁用户
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/unLock", method = RequestMethod.POST)
	public MessageModel unLock(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {
		
		model = super.getUpdateFailureNotice(model);
		try {
			//将用户锁定状态设置为0：正常
			User user = new User();
			user.setUserLockSts("0");
			user.setUserId(userId);  
			//设置锁定变更人
			user.setLstUser(this.getUser().getUserNo());
			//设置锁定变更时间
			user.setLstTime(new Date());
			int result = userInfoService.updateUser(user);
			//删除登录错误日志表记录
			userInfoService.delLoginError(userId);
			if(result == 0) {
				return model;
			}
			//解锁用户时，将用户loginMap的信息还原
			UserLoginStatus.changeUserLoginStatus(userId,Constant.USER_STATUS_NORMAL);
			//增加操作日志
			/*super.insertSyslogs("解锁用户",
					"/user/doUnLock", Constant.LOG_TYPE_UPDATE, "doUnLock", 
					"用户ID："+userId);*/
			
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->doUnLock:", "用户ID："+userId);
			
			model = super.getUpdateSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doUnLock:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("解锁用户错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doUnLock:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("解锁用户错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 锁定用户
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	public MessageModel lock(MessageModel model,
			@RequestParam(required = false, value = "userId") String userId) {
		
		model = super.getUpdateFailureNotice(model);
		try {
			//将用户锁定状态设置为1：锁定
			User user = new User();
			user.setUserLockSts("1");
			user.setUserId(userId);  
			//设置锁定变更人
			user.setLstUser(this.getUser().getUserNo());
			//设置锁定变更时间
			user.setLstTime(new Date());
			int result = userInfoService.updateUser(user);
			if(result == 0) {
				return model;
			}
			
			//锁定用户时清空该用户的loginMap信息
			UserLoginStatus.changeUserLoginStatus(userId,Constant.USER_STATUS_LOCK);
			
			//增加操作日志
			/*super.insertSyslogs("锁定用户",
					"/user/doLock", Constant.LOG_TYPE_UPDATE, "doLock", 
					"用户ID："+userId);*/
			
			SysLogger.info(super.getLogUserAndIpAddr()+"UserController->doLock:", "用户ID："+userId);
			
			model = super.getUpdateFailureNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doLock:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("锁定用户错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"UserController->doLock:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("锁定用户错误");
			e2.printStackTrace();
		}
		return model;
	}
}
