package com.zrd.wh.core.front.service.auth;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.Role;
import com.zrd.wh.core.front.entity.auth.RoleAndResource;
import com.zrd.wh.core.front.entity.auth.RoleAndUser;

/**
 * 角色
 */
public interface IRoleService {
	
	/**
	 * 新增角色信息
	 * @param record
	 * @return
	 */
	int insert(Role record) throws DBException, SysException;
	
	/**
	 * 删除角色信息
	 * @param roleId
	 * @return
	 */
	int deleteRole(String roleId) throws DBException, SysException;
	
	/**
	 * 修改角色信息
	 * @param record
	 * @return
	 */
	int updateRole(Role record) throws DBException, SysException;
	
	/**
	 * 查询一条角色记录
	 * @param RoleId
	 * @return
	 */
	Role selectOneRole(String RoleId) throws DBException, SysException;
	
	/**
	 * 查询所有角色记录
	 * @return
	 */
	List<Role> selectRole() throws DBException, SysException;
	
	/**
	 * 分页查询角色
	 * @param param
	 * @return
	 */
	PageInfo<Role> selectRoleByPage(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;
	
	/**
	 * 根据用户id查询角色是否关联
	 * @param userId
	 * @return
	 */
	List<RoleAndUser> selectRoleAndUser(String userId) throws DBException, SysException;
	
	/**
	 * 增加角色和用户关联关系
	 * @param record
	 * @return
	 */
	int insertRoleAndUser(RoleAndUser record) throws DBException, SysException;
	
	/**
	 * 删除角色和用户关联关系
	 * @param record
	 * @return
	 */
	int deleteRoleAndUser(RoleAndUser record) throws DBException, SysException;
	
	/**
	 * 根据角色id查询角色资源是否关联
	 * @param roleId
	 * @return
	 */
	List<RoleAndResource> selectRoleAndResource(String roleId) throws DBException, SysException;

	/**
	 * 增加角色和资源关联关系
	 * @param record
	 * @return
	 */
	int insertRoleAndResource(RoleAndResource record) throws DBException, SysException;
	
	/**
	 * 删除角色和资源关联关系
	 * @param record
	 * @return
	 */
	int deleteRoleAndResource(RoleAndResource record) throws DBException, SysException;
	
	/**
	 * 根据用户id查询对应角色列表
	 * @param param
	 * @return
	 */
	PageInfo<Role> selectRoleByUserId(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;

	
	/**
	 * 删除角色所拥有的所有用户
	 * @param roleId
	 * @return
	 */
	int delAllUsers(String roleId) throws DBException, SysException;
	
	/**
	 * 删除角色所拥有的所有系统资源
	 * @param roleId
	 * @return
	 */
	int delAllResource(String roleId) throws DBException, SysException;
	
	/**
	 * 通过用户ID和资源url信息去查询用户有没有对应权限
	 * @param params
	 * @return
	 */
	int checkUserResource(Map<String, Object> params) throws DBException, SysException;
}
