package com.zrd.wh.front.web.front.auth.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.Resource;
import com.zrd.wh.core.front.entity.auth.ZTree;
import com.zrd.wh.core.front.service.auth.IResourceService;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

@Service("resourceService")
public class ResourceServiceImpl implements IResourceService {
	
	@Reference(interfaceClass = IResourceService.class)
	private IResourceService iresourceService;
	
	@Override
	public int insert(Resource record) throws DBException, SysException {
		 
		return iresourceService.insert(record);
	}

	@Override
	public int deleteResource(Resource record,String resId) throws DBException, SysException  {
		 
		return iresourceService.deleteResource(record,resId);
	}

	@Override
	public int updateResource(Resource record) throws DBException, SysException  {
		 
		return iresourceService.updateResource(record) ;
	}

	@Override
	public Resource selectOneResource(String ResourceId) throws DBException, SysException  {
		 
		return iresourceService.selectOneResource(ResourceId);
	}

	@Override
	public List<Resource> selectAllResource() throws DBException, SysException  {
		 return iresourceService.selectAllResource();
	}

	@Override
	public PageInfo<Resource> selectPageResource(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException  {
		 
		return iresourceService.selectPageResource(pageNum, pageSize, param);
	}

	@Override
	public List<Resource> selectAllResourceBypResId(String pResId) throws DBException, SysException  {
		 return iresourceService.selectAllResourceBypResId(pResId);
	}

	@Override
	public int delAllResourceRoles(String resId) throws DBException, SysException  {
	 
		return iresourceService.delAllResourceRoles(resId);
	}

	@Override
	public List<ZTree> getResourcesTree(Map<String, Object> params) throws DBException, SysException  {
		return iresourceService.getResourcesTree(params);
	}

	@Override
	public List<Resource> selectAllResourceByUserIdAndpResId(Map<String, String> param) throws DBException, SysException  {
		 
		return iresourceService.selectAllResourceByUserIdAndpResId(param);
	}

	 

}
