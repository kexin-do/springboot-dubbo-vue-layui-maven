package com.zrd.wh.core.front.entity.auth;

import com.zrd.wh.core.base.entity.BaseEntity;
/**
 * 系统用户角色关系表
 * RBAC_ROLE_USER
 */
public class RoleAndUser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -369219076284827352L;
	
	/**
	 * 角色id
	 */
	private String roleId;
	
	/**
	 * 用户id
	 */
	private String userId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RoleAndUser [roleId=" + roleId + ", userId=" + userId + "]";
	}
	
	

}
