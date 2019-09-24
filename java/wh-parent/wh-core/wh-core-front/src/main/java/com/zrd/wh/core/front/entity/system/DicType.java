package com.zrd.wh.core.front.entity.system;

import com.zrd.wh.core.base.entity.BaseEntity;

/**
 * @ClassName DicType
 * @Description 数字字典类型
 * @Author yangjixuan
 * @Date 2019/9/18 10:05
 * @Version 1.0
 **/
public class DicType extends BaseEntity {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -6996240936729872530L;

    /**
     * 数据字典分类代码
     */
    private String dicCode;

    /**
     * 数据字典分类名称
     */
    private String dicName;

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getDicName() {
        return dicName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"dicCode\":\"")
                .append(dicCode).append('\"');
        sb.append(",\"dicName\":\"")
                .append(dicName).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
