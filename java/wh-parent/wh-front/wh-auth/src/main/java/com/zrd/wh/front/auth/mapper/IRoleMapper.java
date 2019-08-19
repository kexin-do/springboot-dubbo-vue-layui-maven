package com.zrd.wh.front.auth.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.zrd.wh.core.front.entity.auth.Role;
import com.zrd.wh.core.front.entity.auth.RoleAndResource;
import com.zrd.wh.core.front.entity.auth.RoleAndUser;

/**
 * 角色
 */
public interface IRoleMapper {
	
	/**
	 * 新增角色信息
	 * @param record
	 * @return
	 */
	int insert(Role record);

	/**
	 * 修改角色信息
	 * @param userId
	 * @return
	 */
	int updateRole(Role record);
	
	/**
	 * 查询一条角色信息
	 * @param userId
	 * @return
	 */
	Role selectOneRole(String userId);

	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<Role> selectRole();
	
	/**
	 * 分页查询角色
	 * @param param
	 * @return
	 */
	Page<Role> selectRoleByPage(Map<String, String> param);
	
	/**
	 * 根据用户id查询角色是否关联
	 * @param userId
	 * @return
	 */
	List<RoleAndUser> selectRoleAndUser(String userId);

	/**
	 * 增加角色和用户关联关系
	 * @param record
	 * @return
	 */
	int insertRoleAndUser(RoleAndUser record);
	
	/**
	 * 删除角色和用户关联关系
	 * @param record
	 * @return
	 */
	int deleteRoleAndUser(RoleAndUser record);
	
	/**
	 * 根据角色id查询角色资源是否关联
	 * @param roleId
	 * @return
	 */
	List<RoleAndResource> selectRoleAndResource(String roleId);
	
	/**
	 * 增加角色和资源关联关系
	 * @param record
	 * @return
	 */
	int insertRoleAndResource(RoleAndResource record);
	
	/**
	 * 删除角色和资源关联关系
	 * @param record
	 * @return
	 */
	int deleteRoleAndResource(RoleAndResource record);
	
	/**
	 * 根据用户id查询对应角色列表
	 * @param param
	 * @return
	 */
	Page<Role> selectRoleByUserId(Map<String, String> param);
	
	/**
	 * 删除角色所拥有的所有用户
	 * @param roleId
	 * @return
	 */
	int delAllUsers(String roleId);
	
	/**
	 * 删除角色所拥有的所有系统资源
	 * @param roleId
	 * @return
	 */
	int delAllResource(String roleId);

	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	int deleteRole(String roleId);
	
	/**
	 * 通过用户ID和资源url信息去查询用户有没有对应权限
	 * @param params
	 * @return
	 */
	int checkUserResource(Map<String, Object> params);
}
