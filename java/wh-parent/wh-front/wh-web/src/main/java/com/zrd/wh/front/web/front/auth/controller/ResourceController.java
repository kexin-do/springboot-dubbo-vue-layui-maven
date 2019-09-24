package com.zrd.wh.front.web.front.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.tool.SysLogger;
import com.zrd.wh.core.base.tool.UUIDGenerator;
import com.zrd.wh.core.front.entity.auth.Resource;
import com.zrd.wh.core.front.entity.auth.ZTree;
import com.zrd.wh.core.front.service.auth.IResourceService;
import com.zrd.wh.front.web.config.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@Scope("prototype")
@RequestMapping("/resource")
public class ResourceController extends BaseController {
 
	private static final long serialVersionUID = -6172922463009405994L;
	@Autowired
	private IResourceService resourceService;
	/**
	 * 跳转权限管理页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String selectPermission(MessageModel model){
		return "/system/auth/permissionManage/permissionManage";
	}
	
	/**
	 * 查询系统资源树封装到ztree中
	 * @return
	 */
	@RequestMapping(value = "/resourcesZTree", method = RequestMethod.POST)
	public MessageModel resourcesZTree(MessageModel model){
		model = super.getQueryFailureNotice(model);
		try {
			Map<String, Object> params = new HashMap<>();
            List<ZTree> resourcesTree =
            		resourceService.getResourcesTree(params);
            
            if(resourcesTree == null || resourcesTree.size()==0) {
            	return model;
            }
            
            model = super.getQuerySuccessNotice(model);
            model.getData().put("resourcesTree", resourcesTree);
        } catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->resourcesZTree:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("查找资源树信息出错");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->resourcesZTree:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("查找资源树信息出错");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 增加权限信息
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public MessageModel addResource(MessageModel model, Resource resource) {
		model = super.getAddFailureNotice(model);
		try {
			// 添加权限信息
			String ids = UUIDGenerator.generate();
			//设置权限id
			resource.setResId(ids);
			//设置创建人
			resource.setLstUser(this.getUser().getUserNo());
			//设置上级菜单
			//如果上级菜单为"",则设置为"0",默认最高级
			if("".equals(resource.getpResId())){
				resource.setpResId("0");
			}
			int result = resourceService.insert(resource);
			if(result == 0) {
				return model;
			}
			//增加核心日志与增加操作日志
			/*super.insertCorelogsAndSyslogs("增加权限信息",
					"/resource/addResource", Constant.LOG_TYPE_ADD, "addResource",
					"权限ID:"+resource.getResId()+",权限代码:"+resource.getResNo()+",权限名称:"+resource.getResName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"ResourceController->addResource:", "resource:"+resource.toString());
			
			model = super.getAddSuccessNotice(model);
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->addResource:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("新增权限错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->addResource:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("新增权限错误");
			e2.printStackTrace();
		}
		return model;
		
	}
	
	/**
	 * 修改权限信息
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public MessageModel updateResource(MessageModel model, Resource resource) {
		model = super.getUpdateFailureNotice(model);
		try {
			//修改创建人
			resource.setLstUser(this.getUser().getUserNo());
			int result = resourceService.updateResource(resource);
			if(result == 0) {
				return model;
			}
			//增加核心日志与操作日志
			/*super.insertCorelogsAndSyslogs("修改权限信息",
					"/resource/updateResource", Constant.LOG_TYPE_UPDATE, "updateResource",
					"权限ID:"+resource.getResId()+",权限代码:"+resource.getResNo()+",权限名称:"+resource.getResName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"ResourceController->updateResource:", "resource:"+resource.toString());
			
			model = super.getUpdateSuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->updateResource:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("修改权限错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->updateResource:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("修改权限错误");
			e2.printStackTrace();
		}
		return model;
	}
	

	/**
	 * 删除权限信息
	 * @param resId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public MessageModel deleteResource(MessageModel model,
			@RequestParam(required = false, value = "resId") String resId) {
		model = super.getDeleteFailureNotice(model);
		try {
			//判断该菜单是否是末节点
			Resource targetResource = resourceService.selectOneResource(resId);
			if("Y".equals(targetResource.getResLeaf())){
				//是末节点
				//修改权限状态为3：注销
				//删除拥有该系统资源的所有角色
				Resource resource = new Resource();
				resource.setResSts("3"); 
				resource.setResId(resId);
				int result = resourceService.deleteResource(resource,resId);
				if(result == 0) {
					return model;
				}
				//增加操作日志,核心日志
				/*super.insertCorelogsAndSyslogs("删除权限信息",
						"/resource/deleteResource", Constant.LOG_TYPE_DELETE, "deleteResource", 
						"权限ID:"+resource.getResId());*/
				SysLogger.info(super.getLogUserAndIpAddr()+"ResourceController->deleteResource:", "resource:"+resource.toString());
				
				model = super.getDeleteSuccessNotice(model);
			}else{
				model.setStatusCode("error");
				model.setStatusInfo("该资源不是末节点...");
			} 
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->deleteResource:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("删除权限错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->deleteResource:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("删除权限错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 跳转到新增权限信息页面
	 * @param type 添加的类型：1：同级，2：下级
	 * @return
	 */
	@RequestMapping(value = "/goAdd", method = RequestMethod.GET)
	public MessageModel goAdd(MessageModel model,
			@RequestParam(required = false, value = "resId") String resId,
			@RequestParam(required = false, value = "type") String type) {
		model = super.getQueryFailureNotice(model);
		try {
			Map<String, Object> data = model.getData();
			Resource resource = resourceService.selectOneResource(resId);
			if("2".equals(type)) {
				data.put("resId", resource.getResId());
				data.put("resName", resource.getResName());
			}else {
				Resource pResource = resourceService.selectOneResource(resource.getpResId());
				if(pResource != null) {
					data.put("resId", pResource.getResId());
					data.put("resName", pResource.getResName());
				}
			}

			model = super.getQuerySuccessNotice(model);
		}catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->goAdd:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("跳转到新增资源页面错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->goAdd:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("跳转到新增资源页面错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/goUpdate", method = RequestMethod.GET)
	public MessageModel goUpdate(MessageModel model,
			@RequestParam(required = false, value = "resId") String resId) {
		model = super.getQueryFailureNotice(model);
		try {
			Resource resource = resourceService.selectOneResource(resId);
			model.getData().put("resource", resource);
			model = super.getQuerySuccessNotice(model);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->goUpdate:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("跳转页面错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"ResourceController->goUpdate:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("跳转页面错误");
			e2.printStackTrace();
		}
		return model;
	}
	
}
