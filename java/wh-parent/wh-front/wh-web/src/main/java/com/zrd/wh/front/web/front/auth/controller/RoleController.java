package com.zrd.wh.front.web.front.auth.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.tool.StringUtil;
import com.zrd.wh.core.base.tool.SysLogger;
import com.zrd.wh.core.base.tool.UUIDGenerator;
import com.zrd.wh.core.front.entity.auth.Resource;
import com.zrd.wh.core.front.entity.auth.Role;
import com.zrd.wh.core.front.entity.auth.RoleAndResource;
import com.zrd.wh.core.front.entity.auth.ZTree;
import com.zrd.wh.core.front.service.auth.IResourceService;
import com.zrd.wh.core.front.service.auth.IRoleService;
import com.zrd.wh.front.web.config.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@Scope("prototype")
@RequestMapping("/role")
public class RoleController extends BaseController {
	 
	private static final long serialVersionUID = -5078927180757757293L;

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IResourceService resourceService;

	/**
	 * 跳转角色管理页面
	 * @return
	 */
	@RequestMapping(value = "/roleManage", method = RequestMethod.GET)
	public String roleManage(ModelMap model){
		return "/system/auth/roleManage/roleManage";
	}
	
	/**
	 * 角色信息列表查询
	 * @param rows
	 * @param page
	 * @param roleName
	 * @param roleSts
	 * @return
	 */
	@RequestMapping(value = "/roleSelect", method = RequestMethod.POST)
	@ResponseBody
	public Object roleSelect(MessageModel model,
			 @RequestParam(required = false, defaultValue = "10") String rows,
			 @RequestParam(required = false, defaultValue = "1") String page,
			 @RequestParam(required = false, value = "roleName") String roleName,
			 @RequestParam(required = false, value = "roleSts") String roleSts) {
		
		Map<String, Object> result = new HashMap<>();
		try {
			SysLogger.info(super.getLogUserAndIpAddr()+"RoleController->roleSelect:", "roleName:"+roleName+"  roleSts:"+roleSts);
			Map<String, String> roleMap = new HashMap<String, String>();
			if (!StringUtil.isEmpty(roleName)) {
				roleMap.put("roleName", roleName.trim());
			}
			if (!StringUtil.isEmpty(roleSts)) {
				roleMap.put("roleSts", roleSts.trim());
			}
			roleMap.put("orgId", super.getUser().getOrgId());
			//分页查询
			PageInfo<Role> roleInfoList = roleService.selectRoleByPage(Integer.parseInt(page),
					Integer.parseInt(rows),  roleMap);
			
			result.put("rows", roleInfoList.getList());
			result.put("total", roleInfoList.getTotal());
			SysLogger.info(super.getLogUserAndIpAddr()+"RoleController->roleSelect:", 
					"rows:"+roleInfoList.getList().toString()+
					",  total:"+roleInfoList.getTotal());
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->roleSelect:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("查询角色列表错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->roleSelect:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("查询角色列表错误");
			e2.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 增加角色信息
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel addRole(MessageModel model, Role role) {
		model = super.getAddFailureNotice(model);
		
		try {
			// 添加角色
			String ids = UUIDGenerator.generate();
			//设置角色id
			role.setRoleId(ids);
			//设置创建人
			role.setLstUser(super.getUser().getUserNo());
			role.setOrgId(super.getUser().getOrgId());
			int result = roleService.insert(role);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("增加角色信息",
					"/role/addRole", Constant.LOG_TYPE_ADD, "addRole",
					"角色ID:"+role.getRoleId()+",用户名称:"+role.getRoleName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"RoleController->addRole:", "role:"+role.toString());
			
			model = super.getAddSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->addRole:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("新增角色错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->addRole:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("新增角色错误");
			e2.printStackTrace();
		}
		
		return model;
	}
	
	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel updateRole(MessageModel model, Role role) {
		
		model = super.getUpdateFailureNotice(model);
		
		try {
			// 修改角色
			//修改创建人
			role.setLstUser(this.getUser().getUserNo());
			int result = roleService.updateRole(role);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("更新角色信息",
					"/role/updateRole", Constant.LOG_TYPE_UPDATE, "updateRole", 
					"角色ID:"+role.getRoleId()+",用户名称:"+role.getRoleName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"RoleController->updateRole:", "role:"+role.toString());
			
			model = super.getUpdateSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->updateRole:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("更新角色错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->updateRole:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("更新角色错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel deleteRole(MessageModel model, 
			@RequestParam(required = false, value = "roleId") String roleId) {
		
		model = super.getDeleteFailureNotice(model);
		
		try {
			//删除角色用户关联关系
			//删除角色资源关联关系
			//删除角色
			int result = roleService.deleteRole(roleId);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("删除角色信息",
					"/role/deleteRole", Constant.LOG_TYPE_DELETE, "deleteRole", "角色ID:"+roleId);
			*/
			SysLogger.info(super.getLogUserAndIpAddr()+"RoleController->deleteRole:", "roleId:"+roleId);
			
			model = super.getDeleteSuccessNotice(model);
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->deleteRole:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("删除角色错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->deleteRole:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("删除角色错误");
			e2.printStackTrace();
		}

		return model;
	}
	
	/**
	 * 角色分配权限查询
	 * @param rows
	 * @param page
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/roleResourceSelect")
	@ResponseBody
	public Object roleResourceSelect(MessageModel model, 
			@RequestParam(required = false, defaultValue = "10") String rows,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, value = "roleId") String roleId) {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			Map<String, String> resourceMap = new HashMap<String, String>();
			//查询资源列表 
			PageInfo<Resource> resourceInfoList = resourceService.selectPageResource(Integer.parseInt(page),
					Integer.parseInt(rows), resourceMap);
			List<Resource> resourceList = resourceInfoList.getList();
			//查询角色资源关系
			List<RoleAndResource> roleAndResourceList = roleService.selectRoleAndResource(roleId);
			//增加角色资源关系状态，用于页面显示
			if(roleAndResourceList!=null&&roleAndResourceList.size()>0&&resourceList!=null&&resourceList.size()>0){
				for(RoleAndResource roleAndResource : roleAndResourceList){
					for(Resource resource : resourceList){
						if(roleAndResource.getResId().equals(resource.getResId())){
							resource.setRoleResourceSts("1");
							break;
						}
					}
				}
			}
			result.put("rows", resourceList);
			result.put("total", resourceInfoList.getTotal());
			SysLogger.info(super.getLogUserAndIpAddr()+"RoleController->roleResourceSelect:", 
					"rows:"+resourceList.toString()+
					",  total:"+resourceInfoList.getTotal());
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->roleResourceSelect:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("查询角色列表错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->roleResourceSelect:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("查询角色列表错误");
			e2.printStackTrace();
		}
		
		return result;
	}
	

	/**
	 * 给角色分配权限
	 * @param roleId
	 * @param resourcesIds
	 * @return
	 */
	@RequestMapping(value = "/updateRoleResources", method = RequestMethod.POST)
	@ResponseBody
	public Object updateRoleResources(MessageModel model, 
			@RequestParam(required = false, value = "roleId") String roleId,
			@RequestParam(required = false, value = "resourcesIds") String resourcesIds) {
		model = super.getUpdateFailureNotice(model);
		
		try {
			//将需要更新的权限拿出来
			String[] resources = resourcesIds.split(",");
			//存储需要新增的权限
			Set<String> resourcesReadyToUpdateAll = new HashSet<>();
			//存储需要删除的权限
			Set<String> resourcesReadyToDeleteAll = new HashSet<>();
			//存储不需要变更的权限
			Set<String> resourcesNotChangeAll = new HashSet<>();
			
			for (String resourceId : resources) {
				resourcesReadyToUpdateAll.add(resourceId);
				resourcesNotChangeAll.add(resourceId);
			}
			
			//查询该角色所有权限
			List<RoleAndResource> roleAndResources = 
					roleService.selectRoleAndResource(roleId);
			for (RoleAndResource roleAndResource : roleAndResources) {
				resourcesReadyToDeleteAll.add(roleAndResource.getResId());
			}
			//求当前用户权限不变的权限
			resourcesNotChangeAll.retainAll(resourcesReadyToDeleteAll);
			//求得当前用户需要新增的权限
			resourcesReadyToUpdateAll.removeAll(resourcesNotChangeAll);
			//求得当前用户需要删除的权限
			resourcesReadyToDeleteAll.removeAll(resourcesNotChangeAll);
			
			//移除权限
			RoleAndResource opRoleAndResource = new RoleAndResource();
			opRoleAndResource.setRoleId(roleId);
			opRoleAndResource.setpType("E");
			for (String deleteResId : resourcesReadyToDeleteAll) {
				opRoleAndResource.setResId(deleteResId);
				roleService.deleteRoleAndResource(opRoleAndResource);
			}
			
			//新增权限
			for (String updateResId : resourcesReadyToUpdateAll) {
				opRoleAndResource.setResId(updateResId);
				roleService.insertRoleAndResource(opRoleAndResource);
			}
			model = super.getUpdateSuccessNotice(model);
			
			String opText = "新增权限id："+resourcesReadyToUpdateAll.toString()+"删除权限："+resourcesReadyToDeleteAll.toString();
			//增加核心日志,操作日志
			/*super.insertCorelogsAndSyslogs("给角色分配权限",
					 "/role/updateRoleResources", Constant.LOG_TYPE_UPDATE, "updateRoleResources", 
					 this.getUser().getUserName()+"为角色"+roleId+"赋予权限");*/
			SysLogger.info(super.getLogUserAndIpAddr()+"RoleController->updateRoleResources:", opText);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->updateRoleResources:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("更新角色资源错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->updateRoleResources:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("更新角色资源错误");
			e2.printStackTrace();
		}
		
		return model;
	}
	

	
	/**
	 * 跳转到新增角色页面
	 * @return
	 */
	@RequestMapping(value = "/goAdd", method = RequestMethod.GET)
	public String goAdd(MessageModel model) {
		return "/system/auth/roleManage/roleManageAdd";
	}
	
	/**
	 * 跳转到新增修改页面
	 * @return
	 */
	@RequestMapping(value = "/goUpdate", method = RequestMethod.GET)
	public String goUpdate(MessageModel model, 
			ModelMap modelMap,@RequestParam(required = false, value = "roleId") String roleId) {
		try {
			Role role = roleService.selectOneRole(roleId);
			modelMap.put("role", role);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->goUpdate:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("跳转到角色修改页面错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->goUpdate:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("跳转到角色修改页面错误");
			e2.printStackTrace();
		}
		return "/system/auth/roleManage/roleManageUpdate";
	}
	
	
	@RequestMapping(value = "/resourcesZTreeToRole", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel resourcesZTreeToRole(MessageModel model, @RequestParam(required = false, value = "roleId") String roleId) {
		model = super.getQueryFailureNotice(model);
		
		try {
			//查询系统资源中状态正常的权限数据
			Map<String, Object> params = new HashMap<>();
			params.put("resSts", "hasValue");
			List<ZTree> resourcesTree = resourceService.getResourcesTree(params);
			
			//查询对应角色下的权限
			List<RoleAndResource> roleAndResources = 
					roleService.selectRoleAndResource(roleId);
			//说明当前这个角色并没有权限,直接返回ztree树
			if(roleAndResources == null || roleAndResources.isEmpty()) {
				model = super.getQuerySuccessNotice(model);
				model.getData().put("resourcesTree", resourcesTree);
				return model;
			}
			
			//将权限组装到map中在遍历中可以单次循环解决
			Map<String, Object> roleResourcesMap = new HashMap<>();
			for (RoleAndResource roleAndResource : roleAndResources) {
				String resId = roleAndResource.getResId();
				roleResourcesMap.put(resId, resId);
			}
			//标记zTree树那些节点再当前权限中已经存在
			for (ZTree zTree : resourcesTree) {
				zTree.setChecked(false);
				String id = zTree.getId();
				if(roleResourcesMap.get(id)!=null) {
					zTree.setChecked(true);
					roleResourcesMap.remove(id);
				}
			}
			model = super.getQuerySuccessNotice(model);
			model.getData().put("resourcesTree", resourcesTree);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->resourcesZTreeToRole:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("查询角色资源错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->resourcesZTreeToRole:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("查询角色资源错误");
			e2.printStackTrace();
		}
		
		return model;
	}
	/**
	 * 改变角色状态
	 * @param model
	 * @param o
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel changeStatus(MessageModel model, Role o) {
		
		model = super.getUpdateFailureNotice(model);
		try {
			String roleSts = o.getRoleSts();
			if(StringUtil.isEmpty(roleSts)) {
				model.setStatusInfo("该角色的状态异常");
				return model;
			}
			String roleStsStr = "";
			if("1".equals(o.getRoleSts())){
				roleStsStr = "启用";
			}else if("2".equals(o.getRoleSts())) {
				roleStsStr = "停用";
			}else {
				roleStsStr = "注销";
			}
			
			int count = 0;
			//不论是注销还是停用都会将用户角色表中有对应关系的角色删除
			if(!"1".equals(o.getRoleSts().trim())) {
				roleService.delAllUsers(o.getRoleId());
				//在注销状态下，需要将该角色的资源信息删除
				if("3".equals(o.getRoleSts().trim())) {
					count = roleService.delAllResource(o.getRoleId());
				}
			}
			o.setLstUser(super.getUser().getUserNo());
			//新状态
			count = roleService.updateRole(o);
			if(count == 0) {
				return model;
			}
			model = super.getUpdateSuccessNotice(model);
			/*super.insertCorelogsAndSyslogs(roleStsStr+"角色",
					 "/role/changeStatus", Constant.LOG_TYPE_UPDATE, "changeStatus", 
					 this.getUser().getUserName()+"修改角色"+"状态为："+roleStsStr);*/
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->changeStatus:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("修改角色状态错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"RoleController->changeStatus:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("修改角色状态错误");
			e2.printStackTrace();
		}
		
		return model;
	}

}
