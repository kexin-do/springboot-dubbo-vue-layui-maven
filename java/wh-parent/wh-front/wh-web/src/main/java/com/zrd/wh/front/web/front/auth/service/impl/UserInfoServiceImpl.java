package com.zrd.wh.front.web.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.LoginError;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IUserInfoService;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

	@Reference(interfaceClass = IUserInfoService.class)
	private IUserInfoService iuserInfoService;
	
	@Override
	public int addRoles(List<Map<String, String>> userRole) throws DBException, SysException {
		 
		return iuserInfoService.addRoles(userRole);
	}

	@Override
	public int delAllRoles(String userId) throws DBException, SysException {
		 
		return iuserInfoService.delAllRoles(userId);
	}

	@Override
	public int updateRoles(String userId, List<Map<String, String>> userRole) throws DBException, SysException {
		 
		return iuserInfoService.updateRoles(userId, userRole);
	}

	@Override
	public int insert(User record) throws DBException, SysException {
		
		return iuserInfoService.insert(record);
	}

	@Override
	public int deleteUser(User record,String userId) throws DBException, SysException {
		
		return iuserInfoService.deleteUser(record,userId);
	}

	@Override
	public int updateUser(User record) throws DBException, SysException {
		 
		return iuserInfoService.updateUser(record);
	}

	@Override
	public User selectOneUser(String userId) throws DBException, SysException {
		 
		return iuserInfoService.selectOneUser(userId);
	}

	@Override
	public List<User> selectUser(Map<String, String> param) throws DBException, SysException {
		 
		return iuserInfoService.selectUser(param);
	}

	@Override
	public PageInfo<User> selectUserAndOrgInfo(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
		 
		return iuserInfoService.selectUserAndOrgInfo(pageNum, pageSize, param);
	}

	@Override
	public User selectLoginUser(String userNo) throws DBException, SysException {
		 
		return iuserInfoService.selectLoginUser(userNo);
	}

	@Override
	public LoginError selectLoginErrorInfo(String userId) throws DBException, SysException {
		return iuserInfoService.selectLoginErrorInfo(userId);
	}

	@Override
	public int insertOrUpdateLoginError(LoginError loginError) throws DBException, SysException {
		return iuserInfoService.insertOrUpdateLoginError(loginError);
	}


	@Override
	public int delUserById(String userId) throws DBException, SysException {
		 
		return iuserInfoService.delUserById(userId);
	}

	@Override
	public PageInfo<User> selectUserAndManager(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
		 
		return iuserInfoService.selectUserAndManager(pageNum, pageSize, param);
	}

	@Override
	public List<User> selectNormalUserByOrgIds(String orgIds) throws DBException, SysException {
		 
		return iuserInfoService.selectNormalUserByOrgIds(orgIds);
	}

	@Override
	public String selectUserOrgCodeByUserId(String sysUserId) throws DBException, SysException {
		return iuserInfoService.selectUserOrgCodeByUserId(sysUserId);
	}

	@Override
	public int delLoginError(String userId) throws DBException, SysException {
		 
		return iuserInfoService.delLoginError(userId);
	}

}
