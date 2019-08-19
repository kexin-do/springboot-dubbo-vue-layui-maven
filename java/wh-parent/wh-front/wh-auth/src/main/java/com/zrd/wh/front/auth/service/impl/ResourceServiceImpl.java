package com.zrd.wh.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.service.BaseService;
import com.zrd.wh.core.front.entity.auth.Resource;
import com.zrd.wh.core.front.entity.auth.ZTree;
import com.zrd.wh.core.front.service.auth.IResourceService;
import com.zrd.wh.front.auth.mapper.IResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;

@Service(interfaceClass = IResourceService.class,
	parameters = {"selectAllResourceBypResId.timeout", "50000",
			"insert.timeout", "5000",
			"deleteResource.timeout", "5000",
			"updateResource.timeout", "5000",
			"selectOneResource.timeout", "5000",
			"selectAllResource.timeout", "5000",
			"selectPageResource.timeout", "50000",
			"delAllResourceRoles.timeout", "5000",
			"getResourcesTree.timeout", "50000",
			"selectAllResourceByUserIdAndpResId.timeout", "50000"
			})
@Component
public class ResourceServiceImpl extends BaseService implements IResourceService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7464028811820944100L;
	@Autowired
	private IResourceMapper resourceMapper;

	@Override
	public int insert(Resource record) throws DBException, SysException {
		int count = 0;
		try {
			count = resourceMapper.insert(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	@Transactional
	public int deleteResource(Resource record,String resId) throws DBException, SysException  {
		//删除拥有该系统资源的所有角色
		resourceMapper.delAllResourceRoles(resId);
		//修改权限状态为3：注销
		int count = 0;
		try {
			count = resourceMapper.updateResource(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public int updateResource(Resource record) throws DBException, SysException  {
		int count = 0;
		try {
			count = resourceMapper.updateResource(record);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public Resource selectOneResource(String ResourceId) throws DBException, SysException  {
		Resource o = null;
		try {
			o = resourceMapper.selectOneResource(ResourceId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return o;
	}

	@Override
	public List<Resource> selectAllResource() throws DBException, SysException  {
		List<Resource> os = null;
		try {
			os = resourceMapper.selectAllResource();
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}
	
	@Override
	public PageInfo<Resource> selectPageResource(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException  {
		List<Resource> list = null;
		PageInfo<Resource> pageInfo = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = resourceMapper.selectPageResource(param);
			pageInfo = new PageInfo<>(list);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return pageInfo;
	}

	@Override
	public List<Resource> selectAllResourceBypResId(String pResId) throws DBException, SysException  {
		List<Resource> os = null;
		try {
			os = resourceMapper.selectAllResourceBypResId(pResId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

	@Override
	public int delAllResourceRoles(String resId) throws DBException, SysException  {
		 
		int count = 0;
		try {
			count = resourceMapper.delAllResourceRoles(resId);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return count;
	}

	@Override
	public List<ZTree> getResourcesTree(Map<String, Object> params) throws DBException, SysException  {
		List<ZTree> os = null;
		try {
			os = resourceMapper.getResourcesTree(params);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

	@Override
	public List<Resource> selectAllResourceByUserIdAndpResId(Map<String, String> param) throws DBException, SysException  {
		 
		List<Resource> os = null;
		try {
			os = resourceMapper.selectAllResourceByUserIdAndpResId(param);
		} catch (DataAccessException ex) {
			throw new DBException(ex);
		} catch (Exception ex) {
			throw new SysException(ex);
		}
		return os;
	}

}
