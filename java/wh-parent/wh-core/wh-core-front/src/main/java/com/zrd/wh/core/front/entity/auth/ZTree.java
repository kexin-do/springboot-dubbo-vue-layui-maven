package com.zrd.wh.core.front.entity.auth;

import com.zrd.wh.core.base.entity.BaseEntity;

/**
 * ZTree数实体类
 */
public class ZTree extends BaseEntity {

    /**
	 * 序列化
	 */
	private static final long serialVersionUID = 4900393198209403233L;
	//zTree 当前id
    private String id;
    //zTree 父级id
    private String pid;
    //zTree 节点名称
    private String name;
    //zTree 节点打开
    private boolean open;

    private boolean checked;
    public String url;
    public String icon;
    public String title;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
