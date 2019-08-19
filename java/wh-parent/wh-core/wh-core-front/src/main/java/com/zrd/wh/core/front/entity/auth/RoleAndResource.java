package com.zrd.wh.core.front.entity.auth;

import com.zrd.wh.core.base.entity.BaseEntity;

/**
 * 系统角色资源信息表
 * RBAC_ROLE_RESOURCE
 */
public class RoleAndResource extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -1704370005494220972L;

	/**
	 * 资源id
	 */
	private String resId;
	
	/**
	 * 角色id
	 */
	private String roleId;
	
	/**
	 * 权限类型
	 */
	private String pType;

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	@Override
	public String toString() {
		return "RoleAndResource [resId=" + resId + ", roleId=" + roleId + ", pType=" + pType + "]";
	}
	
	
}
