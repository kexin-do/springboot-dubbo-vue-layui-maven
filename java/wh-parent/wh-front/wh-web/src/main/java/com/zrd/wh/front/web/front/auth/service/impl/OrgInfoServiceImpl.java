package com.zrd.wh.front.web.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
import com.zrd.wh.core.front.service.auth.IOrgInfoService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

@Service("orgInfoService")
public class OrgInfoServiceImpl implements IOrgInfoService {
	
	@Reference(interfaceClass = IOrgInfoService.class)
	private IOrgInfoService dubboOrgInfoService;

	@Override
	public int insert(OrgInfo record) throws DBException, SysException {
		return dubboOrgInfoService.insert(record);
	}

	@Override
	public int updateOrgInfo(OrgInfo record) throws DBException, SysException  {
		return dubboOrgInfoService.updateOrgInfo(record);
	}

	@Override
	public OrgInfo selectOneOrgInfo(String orgId) throws DBException, SysException  {
		
		return dubboOrgInfoService.selectOneOrgInfo(orgId);
	}
  
	@Override
	public List<OrgInfo> selectSubOrgInfo(String orgId) throws DBException, SysException  {
		 
		return dubboOrgInfoService.selectSubOrgInfo(orgId);
	}

	@Override
	public int updateUserOrg(String orgId) throws DBException, SysException  {
		 
		return dubboOrgInfoService.updateUserOrg(orgId);
	}

 
	@Override
	public PageInfo<OrgInfo> selectSubOrgInfoAll(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException  {
		return dubboOrgInfoService.selectSubOrgInfoAll(pageNum, pageSize, param);
	}
 
	@Override
	public List<OrgInfo> selectOrgInfoByMap(Map<String, String> param) throws DBException, SysException  {
		 
		return dubboOrgInfoService.selectOrgInfoByMap(param);
	}

	@Override
	public PageInfo<OrgInfo> selectHeadSubOrgInfo(int pageNum, int pageSize, Map<String, String> param)
			throws DBException, SysException {
		return dubboOrgInfoService.selectHeadSubOrgInfo(pageNum, pageSize, param);
	}

}
