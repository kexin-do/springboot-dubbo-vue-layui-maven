package com.zrd.wh.core.front.entity.auth;

import java.util.Date;

import com.zrd.wh.core.base.entity.BaseEntity;

/**
 * 系统角色信息表
 * RBAC_ROLE
 */
public class Role extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3772615744678564789L;
	
	/**
	 * 系统主键
	 */
	private String roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleDesc;

	/**
	 * 角色状态
	 */
	private String roleSts;

	/**
	 * 最后变更日期
	 */
	private Date lstTime;

	/**
	 * 最后变更人
	 */
	private String lstUser;
	
	/**
	 * 用户角色关联状态（用户页面展示）
	 */
	private String roleUserSts;
	
	/**
	 * 创建机构号
	 */
	private String orgId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleSts() {
		return roleSts;
	}

	public void setRoleSts(String roleSts) {
		this.roleSts = roleSts;
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

	public String getRoleUserSts() {
		return roleUserSts;
	}

	public void setRoleUserSts(String roleUserSts) {
		this.roleUserSts = roleUserSts;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", roleSts=" + roleSts
				+ ", lstTime=" + lstTime + ", lstUser=" + lstUser + ", roleUserSts=" + roleUserSts + "]";
	}
	
	 

}
