package com.zrd.wh.front.web.front.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.tool.StringUtil;
import com.zrd.wh.core.base.tool.SysLogger;
import com.zrd.wh.core.base.tool.UUIDGenerator;
import com.zrd.wh.core.front.entity.auth.OrgInfo;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IOrgInfoService;
import com.zrd.wh.front.web.config.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

@RestController
@Scope("prototype")
@RequestMapping("/org")
public class OrgInfoController extends BaseController {
  
	private static final long serialVersionUID = -7864624250581177092L;

	@Autowired
	private IOrgInfoService orgInfoService;
	
	/**
	 * 跳转机构管理页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public MessageModel orgManage(MessageModel model){
		return model;
	}

	/**
	 * 查询分页机构管理数据
	 * @param rows
	 * @param page
	 * @param orgName
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public MessageModel list(MessageModel model,
			@RequestParam(required = false,defaultValue="10") String limit,
            @RequestParam(required = false,defaultValue="1") String page,
            @RequestParam(required = false,value="orgName") String orgName,
            @RequestParam(required = false,value="orgCode") String orgCode){
		SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->list:", "orgName:"+orgName+"  orgCode:"+orgCode);
		User user = super.getUser();
		try {
			//验证查询字段是否为空
			Map<String, String> orgMap = new HashMap<>();
			if (!StringUtil.isEmpty(orgName)){
				orgMap.put("orgName", orgName.trim());
			}
			if (!StringUtil.isEmpty(orgCode)){
				orgMap.put("orgCode",  orgCode.trim());
			}
			orgMap.put("orgId",  user.getOrgId());
			//分页查询子机构管理
			PageInfo<OrgInfo> orgInfoList = orgInfoService.
					selectSubOrgInfoAll(Integer.parseInt(page), Integer.parseInt(limit), orgMap);

			/*if("A".equals(user.getUserLevel())){
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
			}*/
			model = super.getQuerySuccessNotice(model);
			model.setRows(orgInfoList.getList());
			model.setTotal(orgInfoList.getTotal());
			SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->orgInfoSelect:", model);
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
		return model;
	}
	
	/**
	 * 添加机构管理
	 * @param orgInfo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public MessageModel add(MessageModel model, OrgInfo orgInfo) {
		User user = this.getUser();
		model = super.getAddFailureNotice(model);
		try {
			Map<String, String> orgMap = new HashMap<String, String>();
			orgMap.put("orgCode",  orgInfo.getOrgCode());
			List<OrgInfo> orgInfoList = orgInfoService.selectOrgInfoByMap(orgMap);
			if(orgInfoList != null && orgInfoList.size()>0){
				model.setStatusInfo("已经存在此机构,机构代码重复");
				return model;
			}
			// 添加机构管理
			String ids = UUIDGenerator.generate();
			//设置机构id
			orgInfo.setOrgId(ids);
			String sOrgId = "";
			//机构管理模式 1：管理下级 2：总行管理
			String orgManageModel = super.getSystemParamCache("orgManageModel").toString();
			if("1".equals(orgManageModel)){
				//管理模式为下级管理,则建立下级机构
				//设置父机构
				orgInfo.setpOrgId(user.getOrgId());
				//设置机构快速检索号
				sOrgId = orgInfoService.selectOneOrgInfo(user.getOrgId()).getsOrgId();
			}
			if("2".equals(orgManageModel)){
				//管理模式为总行统一管理，设置所选机构
				//设置机构快速检索号
				sOrgId = orgInfoService.selectOneOrgInfo(orgInfo.getpOrgId()).getsOrgId();
			}
		 
			orgInfo.setsOrgId(sOrgId+","+ids);
			//设置创建人
			orgInfo.setCreateUser(user.getUserNo());
			//设置变更人
			orgInfo.setLstUser(user.getUserNo());
			int result = orgInfoService.insert(orgInfo);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("添加机构管理",
					"/org/addOrgInfo", Constant.LOG_TYPE_ADD, "addOrgInfo",
					user.getUserNo()+"新增机构,机构代码:"+orgInfos.getOrgCode()+",机构名称:"+orgInfos.getOrgName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->addOrgInfo:", "orgInfos:"+orgInfo.toString());
			
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
	 * @param orgInfo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public MessageModel updateOrg(MessageModel model, OrgInfo orgInfo) {
		
		model = super.getUpdateFailureNotice(model);
		try {
			//修改机构管理
			//更改创建人
			String userNo = this.getUser().getUserNo();
			orgInfo.setLstUser(userNo);
			int result = orgInfoService.updateOrgInfo(orgInfo);
			if(result == 0) {
				return model;
			}
			//增加操作日志
			/*super.insertSyslogs("修改机构管理",
					"/org/updateOrg", Constant.LOG_TYPE_UPDATE, "updateOrg", 
					userNo+"修改机构,机构代码:"+orgInfos.getOrgCode()+",机构名称:"+orgInfos.getOrgName());*/
			SysLogger.info(super.getLogUserAndIpAddr()+"OrgInfoController->updateOrg:", "orgInfos:"+orgInfo.toString());
			
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
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public MessageModel start(MessageModel model,
			@RequestParam(value = "orgId") String orgId) {
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
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public MessageModel stop(MessageModel model,
			@RequestParam(value = "orgId") String orgId) {
		
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

	@RequestMapping(value = "/selectOne", method = RequestMethod.POST)
	public MessageModel selectOne(MessageModel model,
								  @RequestParam(value = "orgId") String orgId){

		model = super.getQueryFailureNotice(model);
		try {
			OrgInfo orgInfo = orgInfoService.selectOneOrgInfo(orgId);
			if (orgInfo == null){
				model.setStatusInfo("未找到当前机构");
				return model;
			}

			model = super.getQuerySuccessNotice(model);
			model.getData().put("orgInfo", orgInfo);
		} catch (DBException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->doEnd:", e2);
			e2.printStackTrace();
			model = super.getDataBaseErrorNotice(model);
			model.setStatusInfo("查询单个机构错误");
		} catch (SysException e2) {
			SysLogger.error(super.getLogUserAndIpAddr()+"OrgInfoController->doEnd:", e2);
			model = super.getSystemErrorNotice(model);
			model.setStatusInfo("查询单个机构错误");
			e2.printStackTrace();
		}
		return model;
	}

}
