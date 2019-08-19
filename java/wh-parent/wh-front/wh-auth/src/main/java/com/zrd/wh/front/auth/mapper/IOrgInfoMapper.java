package com.zrd.wh.front.auth.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
/**
 * 机构信息
 */
public interface IOrgInfoMapper {
	
	/**
	 * 新增机构信息
	 * @param record
	 * @return
	 */
	int insert(OrgInfo record);

	/**
	 * 修改机构信息
	 * @param record
	 * @return
	 */
	int updateOrgInfo(OrgInfo record);
	
	/**
	 * 查询一条机构记录
	 * @param orgId
	 * @return
	 */
	OrgInfo selectOneOrgInfo(String orgId);
  
	
	/**
	 * 查询子机构记录
	 * @param orgId
	 * @return
	 */
	List<OrgInfo> selectSubOrgInfo(String orgId);
	
	/**
	 * 删除用户表中机构为该机构的记录
	 * @param orgId
	 * @return
	 */
	int updateUserOrg(String orgId);
	 
	/**
	 * 分页查询所有子机构记录
	 * @return
	 */
	Page<OrgInfo> selectSubOrgInfoAll(Map<String, String> param);
 
	/**
	 * 通过条件查询机构记录
	 * @param param
	 * @return
	 */
	List<OrgInfo> selectOrgInfoByMap(Map<String, String> param);
	/**
	 * 分页查询总行所有子机构记录
	 * @return
	 */
	Page<OrgInfo> selectHeadSubOrgInfo(Map<String, String> param);
}
