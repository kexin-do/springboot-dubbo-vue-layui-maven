package com.zrd.wh.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.service.BaseService;
import com.zrd.wh.core.front.entity.auth.LoginError;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IUserInfoService;
import com.zrd.wh.front.auth.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;

@Service(interfaceClass = IUserInfoService.class,
parameters = {"addRoles.timeout","5000",
		"delAllRoles.timeout","5000",
		"updateRoles.timeout","5000",
		"insert.timeout","5000",
		"deleteUser.timeout","5000",
		"updateUser.timeout","5000",
		"selectOneUser.timeout","5000",
		"selectUser.timeout","50000",
		"selectUserAndOrgInfo.timeout","50000",
		"selectLoginUser.timeout","5000",
		"selectLoginErrorInfo.timeout","5000",
		"insertOrUpdateLoginError.timeout","5000",
		"selectNormalUserByOrgIds.timeout","5000",
		"selectUserAndManager.timeout","50000"
		})
@Component
public class UserInfoServiceImpl extends BaseService implements IUserInfoService {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4263352006044197164L;
	@Autowired
	private IUserMapper userMapper;
	
	@Override
	public int addRoles(List<Map<String, String>> userRole) throws DBException, SysException {
		int count = 0;
		try {
			count = userMapper.addRoles(userRole);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}
	
	@Override
	public int delAllRoles(String userId) throws DBException, SysException {
		int count = 0;
		try {
			count = userMapper.delAllRoles(userId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}
	
	@Override
	@Transactional
	public int updateRoles(String userId, List<Map<String, String>> userRole) throws DBException, SysException {
		userMapper.delAllRoles(userId);
		int count = 0;
		try {
			count = userMapper.addRoles(userRole);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int insert(User record) throws DBException, SysException  {
		int count = 0;
		try {
			count = userMapper.insert(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	@Transactional
	public int deleteUser(User record,String userId) throws DBException, SysException  {
		//删除用户所属的所有角色
		userMapper.delAllRoles(userId); 
		//将用户状态设置为3：注销
		int count = 0;
		try {
			count = userMapper.updateUser(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	@Transactional
	public int updateUser(User record) throws DBException, SysException  {
		int count = 0;
		try {
			count = userMapper.updateUser(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public User selectOneUser(String userId) throws DBException, SysException  {
		User o = null;
		try {
			o = userMapper.selectOneUser(userId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return o;
	}

	@Override
	public List<User> selectUser(Map<String, String> param) throws DBException, SysException  {
		
		List<User> os = null;
		try {
			os = userMapper.selectUser(param);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

	@Override
	public PageInfo<User> selectUserAndOrgInfo(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException  {
		PageHelper.startPage(pageNum, pageSize);
		List<User> list = userMapper.selectUserAndOrgInfo(param);
		PageInfo<User> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public User selectLoginUser(String userNo) throws DBException, SysException  {
		 
		User o = null;
		
		try {
			o = userMapper.selectLoginUser(userNo);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return o;
	}

	@Override
	public LoginError selectLoginErrorInfo(String userId) throws DBException, SysException  {
		LoginError o = null;
		try {
			o =  userMapper.selectLoginErrorInfo(userId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return o;
	}

	@Override
	public int insertOrUpdateLoginError(LoginError loginError) throws DBException, SysException  {
		
		int count = 0;
		try {
			count = userMapper.updateLoginError(loginError);
			if(count == 0) {
				count = userMapper.insertLoginError(loginError);
			}
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public List<User> selectNormalUserByOrgIds(String orgIds) throws DBException, SysException  {
		List<User> os = null;
		try {
			os = userMapper.selectNormalUserByOrgIds(orgIds);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}
	@Override
	@Transactional
	public int delUserById(String userId) throws DBException, SysException  {
		userMapper.delAllRoles(userId);
		int count = 0;
		try {
			count = userMapper.delUserById(userId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public PageInfo<User> selectUserAndManager(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException  {
		List<User> list = null;
		PageInfo<User> pageInfo = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = userMapper.selectUserAndManager(param);
			pageInfo = new PageInfo<>(list);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return pageInfo;
	}

	@Override
	public String selectUserOrgCodeByUserId(String sysUserId) throws DBException, SysException  {
		String count = "";
		try {
			count = userMapper.selectUserOrgCodeByUserId(sysUserId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int delLoginError(String userId) throws DBException, SysException  {
		 
		int count = 0;
		try {
			count = userMapper.delLoginError(userId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}
}
