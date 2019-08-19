package com.zrd.wh.core.front.entity.auth;

import java.util.Date;

import com.zrd.wh.core.base.entity.BaseEntity;

/**
 * 系统菜单资源信息表
 * RBAC_RESOURCE
 */
public class Resource extends BaseEntity {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 569679425831353571L;
	
	/**
	 * 系统主键
	 */
	private String resId;
	
	/**
	 * 菜单唯一编码，用于LOG记录跟踪
	 */
	private String resNo;
	
	/**
	 * 菜单名称
	 */
	private String resName;
	
	/**
	 * 上级菜单ID
	 */
	private String pResId;
	
	/**
	 * 菜单URL
	 */
	private String resUrl;
	
	/**
	 * 是否末节点，Y=是，N=否
	 */
	private String resLeaf;
	
	/**
	 * 菜单显示顺序
	 */
	private Integer resOrder;
	
	/**
	 * 菜单状态，
	 * 1=正常，
	 * 2=停用，
	 * 3=注销
	 */
	private String resSts;
	
	/**
	 * 最后变更日期
	 */
	private Date lstTime;
	
	/**
	 * 最后变更人
	 */
	private String lstUser;

	/**
	 * 角色资源关联状态（角色页面展示）
	 */
	private String roleResourceSts;
	
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getpResId() {
		return pResId;
	}

	public void setpResId(String pResId) {
		this.pResId = pResId;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getResLeaf() {
		return resLeaf;
	}

	public void setResLeaf(String resLeaf) {
		this.resLeaf = resLeaf;
	}

	public Integer getResOrder() {
		return resOrder;
	}

	public void setResOrder(Integer resOrder) {
		this.resOrder = resOrder;
	}

	public String getResSts() {
		return resSts;
	}

	public void setResSts(String resSts) {
		this.resSts = resSts;
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

	public String getRoleResourceSts() {
		return roleResourceSts;
	}

	public void setRoleResourceSts(String roleResourceSts) {
		this.roleResourceSts = roleResourceSts;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Resource [resId=" + resId + ", resNo=" + resNo + ", resName=" + resName + ", pResId=" + pResId
				+ ", resUrl=" + resUrl + ", resLeaf=" + resLeaf + ", resOrder=" + resOrder + ", resSts=" + resSts
				+ ", lstTime=" + lstTime + ", lstUser=" + lstUser + ", roleResourceSts=" + roleResourceSts + "]";
	}
	
	

}
