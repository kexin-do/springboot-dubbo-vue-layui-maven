package com.zrd.wh.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.service.BaseService;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
import com.zrd.wh.core.front.service.auth.IOrgInfoService;
import com.zrd.wh.front.auth.mapper.IOrgInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;

@Service(interfaceClass = IOrgInfoService.class,
parameters = {"insert.timeout","5000",
		"updateOrgInfo.timeout","5000",
		"selectOneOrgInfo.timeout","5000",
		"selectSubOrgInfo.timeout","50000",
		"updateUserOrg.timeout","5000",
		"selectSubOrgInfoAll.timeout","5000",
		"selectOrgInfoByMap.timeout","50000"
		})
@Component
public class OrgInfoServiceImpl extends BaseService implements IOrgInfoService {
	
	  
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 5379444298912669281L;
	@Autowired
	private IOrgInfoMapper orgInfoMapper;

	@Override
	public int insert(OrgInfo record) throws DBException, SysException {
		int count = 0;
		try {
			count = orgInfoMapper.insert(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}


	@Override
	public int updateOrgInfo(OrgInfo record) throws DBException, SysException {
		int count = 0;
		try {
			count = orgInfoMapper.updateOrgInfo(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public OrgInfo selectOneOrgInfo(String orgId) throws DBException, SysException {
		OrgInfo o = null;
		try {
			o = orgInfoMapper.selectOneOrgInfo(orgId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return o;
	}
  

	@Override
	public List<OrgInfo> selectSubOrgInfo(String orgId) throws DBException, SysException {
		List<OrgInfo> os = null;
		try {
			os = orgInfoMapper.selectSubOrgInfo(orgId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

	@Override
	public int updateUserOrg(String orgId) throws DBException, SysException {
		int count = 0;
		try {
			count = orgInfoMapper.updateUserOrg(orgId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}
 
	@Override
	public PageInfo<OrgInfo> selectSubOrgInfoAll(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
		List<OrgInfo> list = null;
		PageInfo<OrgInfo> pageInfo = null;
		try {
			Page<OrgInfo> orgInfoPage = PageHelper.startPage(pageNum, pageSize);
			orgInfoMapper.selectSubOrgInfoAll(param);
	        pageInfo = new PageInfo<>(orgInfoPage.getResult());
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return pageInfo;
	}
 
	@Override
	public List<OrgInfo> selectOrgInfoByMap(Map<String, String> param) throws DBException, SysException {
		List<OrgInfo> os = null;
		try {
			os = orgInfoMapper.selectOrgInfoByMap(param);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}


	@Override
	public PageInfo<OrgInfo> selectHeadSubOrgInfo(int pageNum, int pageSize, Map<String, String> param)
			throws DBException, SysException {
		List<OrgInfo> list = null;
		PageInfo<OrgInfo> pageInfo = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = orgInfoMapper.selectHeadSubOrgInfo(param);
	        pageInfo = new PageInfo<>(list);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return pageInfo;
	}

}
