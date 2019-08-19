package com.zrd.wh.core.front.service.auth;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.auth.Resource;
import com.zrd.wh.core.front.entity.auth.ZTree;

import java.util.List;
import java.util.Map;

/**
 * 资源
 */
public interface IResourceService {
	
	/**
	 * 新增资源信息
	 * @param record
	 * @return
	 */
	public int insert(Resource record) throws DBException, SysException;
	
	/**
	 * 删除资源信息
	 * @param resId
	 * @return
	 */
	public int deleteResource(Resource record, String resId) throws DBException, SysException;
	
	/**
	 * 修改资源信息
	 * @param record
	 * @return
	 */
	public int updateResource(Resource record) throws DBException, SysException;
	
	/**
	 * 查询一条资源记录
	 * @param ResourceId
	 * @return
	 */
	public Resource selectOneResource(String ResourceId) throws DBException, SysException;
	
	/**
	 * 查询所有资源记录
	 * @return
	 */
	public List<Resource> selectAllResource() throws DBException, SysException;
	
	/**
	 * 查询资源记录 - 分页
	 * @param pageNum - 页码
	 * @param pageSize - 每页记录数
	 * @param param - 查询条件（资源代码精确条件，资源名称模糊条件，资源状态精确条件）
	 * @return
	 */
	public PageInfo<Resource> selectPageResource(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;
	
	/**
	 * 根据上级菜单ID查询所有资源记录
	 * @return
	 */
	public List<Resource> selectAllResourceBypResId(String pResId) throws DBException, SysException;
	
	/**
	 * 删除拥有该系统资源的所有角色
	 * @param resId
	 * @return
	 */
	public int delAllResourceRoles(String resId) throws DBException, SysException;
	
	/**
	 * 查询系统权限树
	 * @return
	 */
	public List<ZTree> getResourcesTree(Map<String, Object> params) throws DBException, SysException;
	
	/**
	 * 根据userId和pResId查询菜单资源
	 * @param param
	 * @return
	 */
	public List<Resource> selectAllResourceByUserIdAndpResId(Map<String, String> param) throws DBException, SysException;

}
