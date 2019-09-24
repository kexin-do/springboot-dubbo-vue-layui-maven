package com.zrd.wh.core.front.entity.auth;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zrd.wh.core.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统用户信息表
 * RBAC_USER
 */
public class User extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -8866335382795531278L;
	
	/**
	 * 系统主键
	 */
	private String userId;

	/**
	 * 用户代码或工号
	 */
	private String userNo;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	private String userPwd;

	/**
	 * 上次修改密码日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date userPwdDay;

	/**
	 * 密码过期标识，
	 * 1=正常，
	 * 2=过期
	 */
	private String pwdInvalid;

	/**
	 * 权限校验码，权限集合的MD5值
	 */
	private String pmd5;

	/**
	 * 用户状态：
	 * 1=正常，
	 * 2=停用，
	 */
	private String userSts;

	/**
	 * 锁定变更日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lstTime;

	/**
	 * 锁定变更人
	 */
	private String lstUser;
	
	/**
	 * 机构主键
	 */
	private String orgId;
	
	//2018/11/02 kexin 用于登录缓存
	/**
	 * 机构编码
	 */
	private String orgCode;
	
	/**
	 * 上级机构   用于登录缓存
	 */
	private String pOrgId;
	//增加机构名称
	/**
	 * 机构名称
	 */
	private String orgName;
	
	/**
	 * 用户在所属机构的部门
	 */
	private String deptName;
	
	/**
	 * 是否允许保存信用报告
	 */
	private String reportSaveRule;
	
	/**
	 * 是否允许打印信用报告
	 */
	private String reportPrintRule;
	
	/**
	 * 单日是否限制最大查询量(0：是；1：否)
	 */
	private String isAmountLimit;
	
	/**
	 * 单日最大查询量
	 */
	private Integer amountLimit;
	
	/**
	 * 查询时间是否限制(0：是；1：否)
	 */
	private String isTimeLimit;
	
	/**
	 * 查询时间，例如：08:20-15:32
	 */
	private String timeLimit;
	
	/**
	 * 是否设置最大查询频率(0:是,1:否)
	 */
	private String isFrequencyLimit;
	
	/**
	 * 每分钟查询最大频率
	 */
	private String frequencyLimit;
	
	/**
	 * 绑定地址
	 */
	private String bindAddressNum;
	
	/**
	 * 用户级别，S-超级管理员；A-管理员；B-业务员
	 */
	private String userLevel;
	
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
	
	/**
	 * 启停变更人
	 */
	private String startUser;
	
	/**
	 * 启停变更时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;
	
	/**
	 * 用户锁定状态(0-正常；1-锁定)
	 */
	private String userLockSts;
	
	private String idNo;
	/**
	 * 最后登录时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lstLoginDate;
	
	public Date getLstLoginDate() {
		return lstLoginDate;
	}

	public void setLstLoginDate(Date lstLoginDate) {
		this.lstLoginDate = lstLoginDate;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Date getUserPwdDay() {
		return userPwdDay;
	}

	public void setUserPwdDay(Date userPwdDay) {
		this.userPwdDay = userPwdDay;
	}

	public String getPwdInvalid() {
		return pwdInvalid;
	}

	public void setPwdInvalid(String pwdInvalid) {
		this.pwdInvalid = pwdInvalid;
	}

	public String getPmd5() {
		return pmd5;
	}

	public void setPmd5(String pmd5) {
		this.pmd5 = pmd5;
	}

	public String getUserSts() {
		return userSts;
	}

	public void setUserSts(String userSts) {
		this.userSts = userSts;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getIsAmountLimit() {
		return isAmountLimit;
	}

	public void setIsAmountLimit(String isAmountLimit) {
		this.isAmountLimit = isAmountLimit;
	}

	public Integer getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(Integer amountLimit) {
		this.amountLimit = amountLimit;
	}

	public String getIsTimeLimit() {
		return isTimeLimit;
	}

	public void setIsTimeLimit(String isTimeLimit) {
		this.isTimeLimit = isTimeLimit;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getIsFrequencyLimit() {
		return isFrequencyLimit;
	}

	public void setIsFrequencyLimit(String isFrequencyLimit) {
		this.isFrequencyLimit = isFrequencyLimit;
	}

	public String getFrequencyLimit() {
		return frequencyLimit;
	}

	public void setFrequencyLimit(String frequencyLimit) {
		this.frequencyLimit = frequencyLimit;
	}

	public String getBindAddressNum() {
		return bindAddressNum;
	}

	public void setBindAddressNum(String bindAddressNum) {
		this.bindAddressNum = bindAddressNum;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
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

	public String getStartUser() {
		return startUser;
	}

	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getUserLockSts() {
		return userLockSts;
	}

	public void setUserLockSts(String userLockSts) {
		this.userLockSts = userLockSts;
	}

	public String getpOrgId() {
		return pOrgId;
	}

	public void setpOrgId(String pOrgId) {
		this.pOrgId = pOrgId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userNo=" + userNo + ", userName=" + userName + ", userPwd=" + userPwd
				+ ", userPwdDay=" + userPwdDay + ", pwdInvalid=" + pwdInvalid + ", pmd5=" + pmd5 + ", userSts="
				+ userSts + ", lstTime=" + lstTime + ", lstUser=" + lstUser + ", orgId=" + orgId + ", orgCode="
				+ orgCode + ", pOrgId=" + pOrgId + ", orgName=" + orgName + ", deptName=" + deptName
				+ ", reportSaveRule=" + reportSaveRule + ", reportPrintRule=" + reportPrintRule + ", isAmountLimit="
				+ isAmountLimit + ", amountLimit=" + amountLimit + ", isTimeLimit=" + isTimeLimit + ", timeLimit="
				+ timeLimit + ", isFrequencyLimit=" + isFrequencyLimit + ", frequencyLimit=" + frequencyLimit
				+ ", bindAddressNum=" + bindAddressNum + ", userLevel=" + userLevel + ", createUser=" + createUser
				+ ", createTime=" + createTime + ", startUser=" + startUser + ", startTime=" + startTime
				+ ", userLockSts=" + userLockSts + ", idNo=" + idNo + "]";
	}

 

	 

}
