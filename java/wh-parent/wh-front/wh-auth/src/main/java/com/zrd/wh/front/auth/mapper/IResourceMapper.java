package com.zrd.wh.front.auth.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.zrd.wh.core.front.entity.auth.Resource;
import com.zrd.wh.core.front.entity.auth.ZTree;

/**
 * 资源信息
 */
public interface IResourceMapper {
	
	/**
	 * 新增资源信息
	 * @param record
	 * @return
	 */
	int insert(Resource record);
 
	/**
	 * 修改资源信息
	 * @param record
	 * @return
	 */
	int updateResource(Resource record);
	
	/**
	 * 查询一条资源信息
	 * @param userId
	 * @return
	 */
	Resource selectOneResource(String userId);
	
	/**
	 * 查询所有资源信息
	 * @return
	 */
	List<Resource> selectAllResource();

	/**
	 * 查询资源信息 - 分页
	 * @param param
	 * @return
	 */
	Page<Resource> selectPageResource(Map<String, String> param);

	/**
	 * 根据上级菜单ID查询所有资源记录
	 * @return
	 */
	List<Resource> selectAllResourceBypResId(String pResId);
	
	/**
	 * 删除拥有该系统资源的所有角色
	 * @param resId
	 * @return
	 */
	int delAllResourceRoles(String resId);

	/**
	 * 查询系统权限树
	 * @param params 数据状态
	 * @return
	 */
	List<ZTree> getResourcesTree(Map<String, Object> params);
	
	/**
	 * 根据userId和pResId查询菜单资源
	 * @param param
	 * @return
	 */
	List<Resource> selectAllResourceByUserIdAndpResId(Map<String, String> param);

}
