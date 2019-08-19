package com.zrd.wh.core.front.service.auth;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.LoginError;
import com.zrd.wh.core.front.entity.auth.User;

/**
 * 用户
 */
public interface IUserInfoService {
	
	/**
	 * 赋予用户角色
	 * @param userRole
	 * @return
	 */
	public int addRoles(List<Map<String, String>> userRole) throws DBException, SysException;
	
	/**
	 * 删除用户所属的所有角色
	 * @param userId
	 * @return
	 */
	public int delAllRoles(String userId) throws DBException, SysException;
	
	/**
	 * 修改用户所属角色
	 * @param userId
	 * @return
	 */
	public int updateRoles(String userId, List<Map<String, String>> userRole) throws DBException, SysException;
	
	/**
	 * 新增用户信息
	 * @param record
	 * @return
	 */
	public int insert(User record) throws DBException, SysException;
	
	/**
	 * 删除用户信息
	 * @param userId
	 * @return
	 */
	public int deleteUser(User record, String userId) throws DBException, SysException;
	
	/**
	 * 修改用户信息
	 * @param record
	 * @return
	 */
	public int updateUser(User record) throws DBException, SysException;
	
	/**
	 * 查询一条用户记录
	 * @param userId
	 * @return
	 */
	public User selectOneUser(String userId) throws DBException, SysException;
	
	/**
	 * 查询所有用户记录
	 * @param param - 查询条件（用户代码精确条件，用户名称模糊条件，用户状态精确条件）
	 * @return
	 */
	public List<User> selectUser(Map<String, String> param) throws DBException, SysException;

	/**
	 * 联合查询所有用户和机构记录
	 * @param param
	 * @return
	 */
	public PageInfo<User> selectUserAndOrgInfo(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;

	/**
	 * 根据用户代码查询用户
	 * @param userNo
	 * @return
	 */
	public User selectLoginUser(String userNo) throws DBException, SysException;
	
	/**
	 * 根据用户id查询用户
	 * @param userId
	 * @return
	 */
	public LoginError selectLoginErrorInfo(String userId) throws DBException, SysException;

	/**
	 * 	添加或者更新用户登录错误表
	 * @param loginError
	 * @return
	 */
	public int insertOrUpdateLoginError(LoginError loginError) throws DBException, SysException;
	/**
	 * 通过用户的机构id查找到所有普通用户
	 * @param orgIds 通过','拼接好的所有机构号
	 * @return
	 */
	public List<User> selectNormalUserByOrgIds(String orgIds) throws DBException, SysException;
	
	/**
	 * 物理删除用户，同时删除角色关联关系
	 * @param userId
	 * @return
	 */
	public int delUserById(String userId) throws DBException, SysException;
	 
	/**
	 *联合查询本级普通用户和下一级管理员 
	 * @param pageNum
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public PageInfo<User> selectUserAndManager(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;

	
	/**
	 * 通过用户id查询用户的机构号
	 * @param sysUserId
	 * @return
	 */
	public String selectUserOrgCodeByUserId(String sysUserId) throws DBException, SysException;
	/**
	 * 删除登录错误日志表
	 * @param userId
	 * @return
	 */
	public int delLoginError(String userId) throws DBException, SysException;
}
