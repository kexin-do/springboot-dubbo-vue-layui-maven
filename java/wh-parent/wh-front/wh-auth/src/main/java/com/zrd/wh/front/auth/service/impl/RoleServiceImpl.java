package com.zrd.wh.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.service.BaseService;
import com.zrd.wh.core.front.entity.auth.Role;
import com.zrd.wh.core.front.entity.auth.RoleAndResource;
import com.zrd.wh.core.front.entity.auth.RoleAndUser;
import com.zrd.wh.core.front.service.auth.IRoleService;
import com.zrd.wh.front.auth.mapper.IRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;

@Service(interfaceClass = IRoleService.class,
parameters = {"insert.timeout","5000",
		"deleteRole.timeout","5000",
		"updateRole.timeout","5000",
		"selectOneRole.timeout","5000",
		"selectRole.timeout","50000",
		"selectRoleByPage.timeout","50000",
		"selectRoleAndUser.timeout","50000",
		"insertRoleAndUser.timeout","5000",
		"deleteRoleAndUser.timeout","5000",
		"selectRoleAndResource.timeout","50000",
		"insertRoleAndResource.timeout","5000",
		"deleteRoleAndResource.timeout","5000",
		"selectRoleByUserId.timeout","50000",
		"delAllUsers.timeout","5000",
		"delAllResource.timeout","5000",
		"checkUserResource", "5000"})
@Component
public class RoleServiceImpl extends BaseService implements IRoleService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4540360160059666480L;
	@Autowired
	private IRoleMapper roleMapper;

	@Override
	public int insert(Role record) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.insert(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	@Transactional
	public int deleteRole(String roleId) throws DBException, SysException {
		//删除角色用户关联关系
		roleMapper.delAllUsers(roleId);
		//删除角色资源关联关系
		roleMapper.delAllResource(roleId);
		//删除角色
		
		int count = 0;
		try {
			count = roleMapper.deleteRole(roleId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int updateRole(Role record) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.updateRole(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public Role selectOneRole(String RoleId) throws DBException, SysException {
		Role o = null;
		try {
			o = roleMapper.selectOneRole(RoleId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return o;
	}

	@Override
	public List<Role> selectRole() throws DBException, SysException {
		List<Role> os = null;
		try {
			os = roleMapper.selectRole();
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

	@Override
	public PageInfo<Role> selectRoleByPage(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
		List<Role> list = null;
		PageInfo<Role> pageInfo = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = roleMapper.selectRoleByPage(param);
			pageInfo = new PageInfo<>(list);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return pageInfo;
	}

	@Override
	public List<RoleAndUser> selectRoleAndUser(String userId) throws DBException, SysException {
		List<RoleAndUser> os = null;
		try {
			os = roleMapper.selectRoleAndUser(userId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

	@Override
	public int insertRoleAndUser(RoleAndUser record) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.insertRoleAndUser(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int deleteRoleAndUser(RoleAndUser record) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.deleteRoleAndUser(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public List<RoleAndResource> selectRoleAndResource(String roleId) throws DBException, SysException {
		List<RoleAndResource> os = null;
		try {
			os = roleMapper.selectRoleAndResource(roleId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

	@Override
	public int insertRoleAndResource(RoleAndResource record) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.insertRoleAndResource(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int deleteRoleAndResource(RoleAndResource record) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.deleteRoleAndResource(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public PageInfo<Role> selectRoleByUserId(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
		List<Role> list = null;
		PageInfo<Role> pageInfo = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = roleMapper.selectRoleByUserId(param);
			pageInfo = new PageInfo<>(list);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return pageInfo;
	}

	@Override
	public int delAllUsers(String roleId) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.delAllUsers(roleId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int delAllResource(String roleId) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.delAllResource(roleId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int checkUserResource(Map<String, Object> params) throws DBException, SysException {
		int count = 0;
		try {
			count = roleMapper.checkUserResource(params);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

}
