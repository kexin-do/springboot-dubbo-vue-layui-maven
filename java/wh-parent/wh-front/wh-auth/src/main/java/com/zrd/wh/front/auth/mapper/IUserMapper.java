package com.zrd.wh.front.auth.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.zrd.wh.core.front.entity.auth.LoginError;
import com.zrd.wh.core.front.entity.auth.User;

/**
 * 用户信息
 */
public interface IUserMapper {
	
	/**
	 * 赋予用户角色
	 * @param userRole
	 * @return
	 */
	public int addRoles(List<Map<String, String>> userRole);
	
	/**
	 * 删除用户所属的所有角色
	 * @param userId
	 * @return
	 */
	public int delAllRoles(String userId);
	
	/**
	 * 新增用户信息
	 * @param record
	 * @return
	 */
	public int insert(User record);

  
	/**
	 * 修改用户信息
	 * @param userId
	 * @return
	 */
	public int updateUser(User record);
	
	/**
	 * 查询一条用户记录
	 * @param userId
	 * @return
	 */
	public User selectOneUser(String userId);

	/**
	 * 查询所有用户记录
	 * @param param
	 * @return
	 */
	public List<User> selectUser(Map<String, String> param);

	/**
	 * 联合查询所有用户和机构记录
	 * @param param
	 * @return
	 */
	public Page<User> selectUserAndOrgInfo(Map<String, String> param);
	
	/**
	 * 根据用户代码查询用户
	 * @param userNo
	 * @return
	 */
	public User selectLoginUser(String userNo);

	/**
	 * 查询用户登录错误信息
	 * @param userId
	 * @return
	 */
	public LoginError selectLoginErrorInfo(String userId);

	/**
	 * 更新用户登录错误信息
	 * @param loginError
	 * @return
	 */
	public int updateLoginError(LoginError loginError);

	/**
	 * 新增用户登录错误信息
	 * @param loginError
	 * @return
	 */
	public int insertLoginError(LoginError loginError);
	/**
	 * 通过用户的机构id查找到所有普通用户
	 * @param orgIds 通过','拼接好的所有机构号
	 * @return
	 */
	public List<User> selectNormalUserByOrgIds(String orgIds);
	/**
	 * 物理删除用户
	 * @param userId
	 * @return
	 */
	public int delUserById(String userId);
	
	/**
	 * 联合查询本级普通用户和下一级管理员
	 * @param param
	 * @return
	 */
	public Page<User> selectUserAndManager(Map<String, String> param);

	/**
	 * 通过用户id查询用户的机构号
	 * @param sysUserId
	 * @return
	 */
	public String selectUserOrgCodeByUserId(String sysUserId);
	
	/**
	 * 删除登录错误日志表
	 * @param userId
	 * @return
	 */
	public int delLoginError(String userId);
}
