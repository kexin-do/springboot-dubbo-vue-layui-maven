package com.zrd.wh.front.web.front.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.tool.SysLogger;
import com.zrd.wh.core.base.tool.UUIDGenerator;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IOrgInfoService;
import com.zrd.wh.front.web.config.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/org")
public class OrgInfoController extends BaseController {
  
	private static final long serialVersionUID = -7864624250581177092L;
	@Autowired
	private IOrgInfoService orgInfoService;
	
	/**
	 * 跳转机构管理页面
	 * @return
	 */
	@RequestMapping(value = "/orgManage", method = RequestMethod.GET)
	public String orgManage(MessageModel model, ModelMap modelMap){
		String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
		if("2".equals(orgManageModel)){
			//管理模式为总行统一管理
			if(super.getUser().getpOrgId()!=null&&!"".equals(super.getUser().getpOrgId())){
				modelMap.put("flag", "3");//没有查询权限
			}
		}
		return "/system/auth/orgManage/orgManage";
	}

	/**
	 * 查询分页机构管理数据
	 * @param rows
	 * @param page
	 * @param orgName
	 * @return
	 */
	@RequestMapping(value = "/orgInfoSelect", method = RequestMethod.POST)
	@ResponseBody
	public Object orgInfoSelect(MessageModel model,
			@RequestParam(required = false,defaultValue="10") String rows,
            @RequestParam(required = false,defaultValue="1") String page,
            @RequestParam(required = false,value="orgName") String orgName,
            @RequestParam(required = false,value="orgCode") String orgCode){
		SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->orgInfoSelect:", "orgName:"+orgName+"  orgCode:"+orgCode);
		Map<String, Object> result = new HashMap<>();
		User user = super.getUser();
		try {
			//验证查询字段是否为空
			Map<String, String> orgMap = new HashMap<String, String>();
			if (orgName.trim() != null && !"".equals(orgName.trim())){
				orgMap.put("orgName", orgName.trim());
			}
			if (orgCode.trim() != null && !"".equals(orgCode.trim())){
				orgMap.put("orgCode",  orgCode.trim());
			}
			orgMap.put("orgId",  user.getOrgId());
			//分页查询子机构管理
			PageInfo<OrgInfo> orgInfoList = new PageInfo<OrgInfo>();
			if("A".equals(user.getUserLevel())){
				//如果当前登录用户为管理员
				//机构管理模式 1：管理下级 2：总行管理
				String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
				if("1".equals(orgManageModel)){
					//管理模式为下级管理,则允许查询下级机构
					orgInfoList = orgInfoService.selectSubOrgInfoAll(Integer.parseInt(page), Integer.parseInt(rows), orgMap);
				}
				if("2".equals(orgManageModel)){
					//管理模式为总行统一管理并且为总行管理员，则查询总行下所有机构
					if(user.getpOrgId()==null||"".equals(user.getpOrgId())){
						orgInfoList = orgInfoService.selectHeadSubOrgInfo(Integer.parseInt(page), Integer.parseInt(rows), orgMap);
					}
				}
				
			}
			//获取用户列表信息
			result.put("rows", orgInfoList.getList());
			//获取总条数
			result.put("total", orgInfoList.getTotal());
			
			SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->orgInfoSelect:", "result:"+result);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->orgInfoSelect:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("查询机构列表错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->orgInfoSelect:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("查询机构列表错误");
			e2.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加机构管理
	 * @param orgInfos
	 * @return
	 */
	@RequestMapping(value = "/addOrgInfo", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel addOrgInfo(MessageModel model, OrgInfo orgInfos) {
		User user = this.getUser();
		model = super.getAddFailureNotice(model);
		try {
			Map<String, String> orgMap = new HashMap<String, String>();
			orgMap.put("orgCode",  orgInfos.getOrgCode());
			List<OrgInfo> orgInfoList = orgInfoService.selectOrgInfoByMap(orgMap);
			if(orgInfoList != null && orgInfoList.size()>0){
				model.setStatusInfo("已经存在此机构,机构代码重复");
				return model;
			}
			// 添加机构管理
			String ids = UUIDGenerator.generate();
			//设置机构id
			orgInfos.setOrgId(ids);
			String sOrgId = "";
			//机构管理模式 1：管理下级 2：总行管理
			String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
			if("1".equals(orgManageModel)){
				//管理模式为下级管理,则建立下级机构
				//设置父机构
				orgInfos.setpOrgId(user.getOrgId());
				//设置机构快速检索号
				sOrgId = orgInfoService.selectOneOrgInfo(user.getOrgId()).getsOrgId();
			}
			if("2".equals(orgManageModel)){
				//管理模式为总行统一管理，设置所选机构
				//设置机构快速检索号
				sOrgId = orgInfoService.selectOneOrgInfo(orgInfos.getpOrgId()).getsOrgId();
			}
		 
			orgInfos.setsOrgId(sOrgId+","+ids);
			//设置创建人
			orgInfos.setCreateUser(user.getUserNo());
			//设置变更人
			orgInfos.setLstUser(user.getUserNo());
			int result = orgInfoService.insert(orgInfos);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("添加机构管理",
					"/org/addOrgInfo", Constant.LOG_TYPE_ADD, "addOrgInfo",
					user.getUserNo()+"新增机构,机构代码:"+orgInfos.getOrgCode()+",机构名称:"+orgInfos.getOrgName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->addOrgInfo:", "orgInfos:"+orgInfos.toString());
			
			model = super.getAddSuccessNotice(model);
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->addOrgInfo:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("新增机构错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->addOrgInfo:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("新增机构错误");
			e2.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 修改机构管理
	 * @param orgInfos
	 * @return
	 */
	@RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel updateOrg(MessageModel model, OrgInfo orgInfos) {
		
		model = super.getUpdateFailureNotice(model);
		try {
			//修改机构管理
			//更改创建人
			String userNo = this.getUser().getUserNo();
			orgInfos.setLstUser(userNo);
			int result = orgInfoService.updateOrgInfo(orgInfos);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("修改机构管理",
					"/org/updateOrg", Constant.LOG_TYPE_UPDATE, "updateOrg", 
					userNo+"修改机构,机构代码:"+orgInfos.getOrgCode()+",机构名称:"+orgInfos.getOrgName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->updateOrg:", "orgInfos:"+orgInfos.toString());
			
			model = super.getUpdateSuccessNotice(model);
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->updateOrg:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("修改机构错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->updateOrg:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("修改机构错误");
			e2.printStackTrace();
		}
		
		return model;
	}
	
	/**
	 * 机构启用
	 * @param model
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "/doStart", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel doStart(MessageModel model,
			@RequestParam(required = false, value = "orgId") String orgId) {
		model = super.getUpdateFailureNotice(model);
		try {
			String userNo = this.getUser().getUserNo();
			OrgInfo orgInfos = new OrgInfo();
			orgInfos.setOrgId(orgId);
			orgInfos.setOrgSts("1");
			orgInfos.setLstUser(userNo);
			int result = orgInfoService.updateOrgInfo(orgInfos);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("机构启用",
					"/org/doStart", Constant.LOG_TYPE_UPDATE, "doStart", 
					userNo+"启用机构"+orgInfos.getOrgId());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->doStart:", "orgInfos:"+orgInfos.toString());
			
			model = super.getUpdateSuccessNotice(model);
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->doStart:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("启用机构错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->doStart:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("启用机构错误");
			e2.printStackTrace();
		}
		
		return model;
	}
	
	/**
	 * 机构停用
	 * @param model
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "/doEnd", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel doEnd(MessageModel model,
			@RequestParam(required = false, value = "orgId") String orgId) {
		
		model = super.getUpdateFailureNotice(model);
		try {
			String userNo = this.getUser().getUserNo(); 
			int result = 0;
			int count = 0;
			//停用该机构下所有子机构
			//查询所有子机构
			Map<String, String> orgMap = new HashMap<String, String>();
			orgMap.put("sOrgId", orgId);
			orgMap.put("orgSts", "1");
			List<OrgInfo> orgInfoList = orgInfoService.selectOrgInfoByMap(orgMap);
			if(orgInfoList!=null && orgInfoList.size()>0){
				for(OrgInfo orgInfo : orgInfoList){
					orgInfo.setOrgSts("2");
					orgInfo.setLstUser(userNo);
					result = orgInfoService.updateOrgInfo(orgInfo);
					count = count + result;
				} 
				//增加操作日志
				/*super.insertSyslogs("机构停用",
						"/org/doEnd", Constant.LOG_TYPE_UPDATE, "doEnd", 
						userNo+"停用机构"+orgId+"下的所有机构");*/
				SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->doEnd:", "停用机构ID:"+orgId+"下的所有机构");
				if(count != orgInfoList.size()) {
					return model;
				}
				model = super.getUpdateSuccessNotice(model);
			}
				
			
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->doEnd:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
        	model.setStatusInfo("停用机构错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->doEnd:", e2);
			model = super.getSystemErrorNotice(model);
        	model.setStatusInfo("停用机构错误");
			e2.printStackTrace();
		}
		
		return model;
	}

	@RequestMapping(value = "/ifgoAdd", method = RequestMethod.POST)
	@ResponseBody
	public MessageModel ifgoAdd(MessageModel model) {

		model = super.getUpdateFailureNotice(model);
		String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
		if("2".equals(orgManageModel)){
			if(super.getUser().getpOrgId()!=null&&!"".equals(super.getUser().getpOrgId())){
				model = super.getUpdateSuccessNotice(model);
			}
		}

		return model;
	}

}
