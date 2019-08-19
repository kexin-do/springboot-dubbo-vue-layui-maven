package com.zrd.wh.front.web.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.Role;
import com.zrd.wh.core.front.entity.auth.RoleAndResource;
import com.zrd.wh.core.front.entity.auth.RoleAndUser;
import com.zrd.wh.core.front.service.auth.IRoleService;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Reference(interfaceClass = IRoleService.class)
	private IRoleService roleService;
	
	@Override
	public int insert(Role record) throws DBException, SysException {
		 
		return roleService.insert(record);
	}

	@Override
	public int deleteRole(String roleId) throws DBException, SysException {
		 
		return roleService.deleteRole(roleId);
	}

	@Override
	public int updateRole(Role record) throws DBException, SysException {
		 
		return roleService.updateRole(record);
	}

	@Override
	public Role selectOneRole(String RoleId) throws DBException, SysException {
		 
		return roleService.selectOneRole(RoleId);
	}

	@Override
	public List<Role> selectRole() throws DBException, SysException {
		 
		return roleService.selectRole();
	}

	@Override
	public PageInfo<Role> selectRoleByPage(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
	 
		return roleService.selectRoleByPage(pageNum, pageSize, param);
	}

	@Override
	public List<RoleAndUser> selectRoleAndUser(String userId) throws DBException, SysException {
	 
		return roleService.selectRoleAndUser(userId);
	}

	@Override
	public int insertRoleAndUser(RoleAndUser record) throws DBException, SysException {
		 
		return roleService.insertRoleAndUser(record);
	}

	@Override
	public int deleteRoleAndUser(RoleAndUser record) throws DBException, SysException {
		 
		return roleService.deleteRoleAndUser(record);
	}

	@Override
	public List<RoleAndResource> selectRoleAndResource(String roleId) throws DBException, SysException {
	 
		return roleService.selectRoleAndResource(roleId);
	}

	@Override
	public int insertRoleAndResource(RoleAndResource record) throws DBException, SysException {
		 
		return roleService.insertRoleAndResource(record);
	}

	@Override
	public int deleteRoleAndResource(RoleAndResource record) throws DBException, SysException {
		 
		return roleService.deleteRoleAndResource(record);
	}

	@Override
	public PageInfo<Role> selectRoleByUserId(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
		 
		return roleService.selectRoleByUserId(pageNum, pageSize, param);
	}

	@Override
	public int delAllUsers(String roleId) throws DBException, SysException {
	 
		return roleService.delAllUsers(roleId);
	}

	@Override
	public int delAllResource(String roleId) throws DBException, SysException {
		 
		return roleService.delAllResource(roleId);
	}

	@Override
	public int checkUserResource(Map<String, Object> params) throws DBException, SysException {
		return roleService.checkUserResource(params);
	}

}
