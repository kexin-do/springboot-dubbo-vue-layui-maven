package com.zrd.wh.core.front.service.auth;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
/**
 * 机构
 */
public interface IOrgInfoService {
	
	/**
	 * 新增机构信息
	 * @param record
	 * @return
	 */
	int insert(OrgInfo record) throws DBException, SysException;

	/**
	 * 修改机构信息
	 * @param record
	 * @return
	 */
	int updateOrgInfo(OrgInfo record) throws DBException, SysException;
	
	/**
	 * 查询一条机构记录
	 * @param orgId
	 * @return
	 */
	OrgInfo selectOneOrgInfo(String orgId) throws DBException, SysException;
  
	
	/**
	 * 查询子机构记录
	 * @param orgId
	 * @return
	 */
	List<OrgInfo> selectSubOrgInfo(String orgId) throws DBException, SysException;
	
	/**
	 * 删除用户表中机构为该机构的记录
	 * @param orgId
	 * @return
	 */
	int updateUserOrg(String orgId) throws DBException, SysException;
 
	/**
	 * 查询所有子机构记录
	 * @return
	 */
	PageInfo<OrgInfo> selectSubOrgInfoAll(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;
	 
	
	/**
	 * 通过条件查询机构记录
	 * @param param
	 * @return
	 */
	List<OrgInfo> selectOrgInfoByMap(Map<String, String> param) throws DBException, SysException;
	/**
	 * 查询总行所有子机构记录
	 * @return
	 */
	PageInfo<OrgInfo> selectHeadSubOrgInfo(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;
}
