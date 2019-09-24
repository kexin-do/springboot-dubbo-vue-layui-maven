package com.zrd.wh.core.front.entity.auth;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zrd.wh.core.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 组织机构信息表
 * RBAC_ORG
 *
 */
public class OrgInfo extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 系统主键
	 */
	private String orgId;

	/**
	 * 机构编码，此系统为14位金融机构编码
	 */
	private String orgCode;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 机构全称
	 */
	private String orgFName;

	/**
	 * 机构英文名称
	 */
	private String orgEName;

	/**
	 * 上级机构代码，空标示顶级机构
	 */
	private String pOrgId;

	/**
	 * 机构快速检索号
	 */
	private String sOrgId;

	/**
	 * 机构地址
	 */
	private String orgAddress;

	/**
	 * 地区代码
	 */
	private String areaCode;

	/**
	 * 机构状态，
	 * 1=正常，
	 * 2=停用，
	 * 3=注销
	 */
	private String orgSts;

	/**
	 * 最后变更日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lstTime;

	/**
	 * 最后变更人
	 */
	private String lstUser;
	
	/**
	 * 是否允许另存报告
	 */
	private String reportSaveRule;
	
	/**
	 * 是否允许打印报告
	 */
	private String reportPrintRule;
	
	/**
	 * 查询时间段
	 */
	private String timeLimit;
	
	/**
	 * 详细地址
	 */
	private String address;
	
	/**
	 * 邮政编码
	 */
	private String zip;
	
	/**
	 * 联系人
	 */
	private String linkMan;
	
	/**
	 * 联系方式
	 */
	private String linkMode;
	
	/**
	 * 其他联系方式
	 */
	private String otherLinkMode;
	
	/**
	 * 所属机构名称
	 */
	private String pOrgName;   
	
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgFName() {
		return orgFName;
	}

	public void setOrgFName(String orgFName) {
		this.orgFName = orgFName;
	}

	public String getOrgEName() {
		return orgEName;
	}

	public void setOrgEName(String orgEName) {
		this.orgEName = orgEName;
	}

	public String getpOrgId() {
		return pOrgId;
	}

	public void setpOrgId(String pOrgId) {
		this.pOrgId = pOrgId;
	}

	public String getsOrgId() {
		return sOrgId;
	}

	public void setsOrgId(String sOrgId) {
		this.sOrgId = sOrgId;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOrgSts() {
		return orgSts;
	}

	public void setOrgSts(String orgSts) {
		this.orgSts = orgSts;
	}

	public Date getLstTime() {
		return lstTime;
	}

	public void setLstTime(Date lstTime) {
		this.lstTime = lstTime;
	}

	public String getLstUser() {
		return lstUser;
	}

	public void setLstUser(String lstUser) {
		this.lstUser = lstUser;
	}

	public String getReportSaveRule() {
		return reportSaveRule;
	}

	public void setReportSaveRule(String reportSaveRule) {
		this.reportSaveRule = reportSaveRule;
	}

	public String getReportPrintRule() {
		return reportPrintRule;
	}

	public void setReportPrintRule(String reportPrintRule) {
		this.reportPrintRule = reportPrintRule;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkMode() {
		return linkMode;
	}

	public void setLinkMode(String linkMode) {
		this.linkMode = linkMode;
	}

	public String getOtherLinkMode() {
		return otherLinkMode;
	}

	public void setOtherLinkMode(String otherLinkMode) {
		this.otherLinkMode = otherLinkMode;
	}

	public String getpOrgName() {
		return pOrgName;
	}

	public void setpOrgName(String pOrgName) {
		this.pOrgName = pOrgName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OrgInfo [orgId=" + orgId + ", orgCode=" + orgCode + ", orgName=" + orgName + ", orgFName=" + orgFName
				+ ", orgEName=" + orgEName + ", pOrgId=" + pOrgId + ", sOrgId=" + sOrgId + ", orgAddress=" + orgAddress
				+ ", areaCode=" + areaCode + ", orgSts=" + orgSts + ", lstTime=" + lstTime + ", lstUser=" + lstUser
				+ ", reportSaveRule=" + reportSaveRule + ", reportPrintRule=" + reportPrintRule + ", timeLimit="
				+ timeLimit + ", address=" + address + ", zip=" + zip + ", linkMan=" + linkMan + ", linkMode="
				+ linkMode + ", otherLinkMode=" + otherLinkMode + ", pOrgName=" + pOrgName + ", createUser="
				+ createUser + ", createTime=" + createTime + "]";
	}

	 
	
}
