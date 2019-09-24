package com.zrd.wh.core.front.service.system;


import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.front.entity.system.DicType;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IDicTypeService
 * @Description 数据字典类型接口
 * @Author yangjixuan
 * @Date 2019/9/18 10:03
 * @Version 1.0
 **/
public interface IDicTypeService {
    /**
     * 新增数据字典类型
     * @param dicType
     * @return
     * @throws DBException
     * @throws SysException
     */
    public int insert(DicType dicType) throws DBException, SysException;

    /**
     * 删除数据字典类型
     * @param dicCode
     * @return
     * @throws DBException
     * @throws SysException
     */
    public void delete(String dicCode) throws DBException, SysException;

    /**
     * 更新数据字典类型
     * @param dicType
     * @return
     * @throws DBException
     * @throws SysException
     */
    public int update(DicType dicType) throws  DBException, SysException;

    /**
     *@Author yangjixuan
     *@Description 查询数据字典类型信息
     *@Date 2019/9/18 11:03
     *@Param [dicCode]
     *@return com.zrd.wh.core.front.entity.system.DicType
     **/
    public DicType selectDicTypeInfo(String dicCode) throws DBException, SysException;

    /**
     *@Author yangjixuan
     *@Description 数据字典类型条件查询
     *@Date 2019/9/18 11:06
     *@Param [param]
     *@return java.util.List<com.zrd.wh.core.front.entity.system.DicType>
     **/
    public List<DicType> selectDicType(Map<String, String> param) throws DBException, SysException;

    /**
     *@Author yangjixuan
     *@Description 数据字典类型分页查询
     *@Date 2019/9/18 10:56
     *@Param [pageNum, pageSize, param]
     *@return com.github.pagehelper.PageInfo<com.zrd.wh.core.front.entity.system.DicType>
     **/
    public PageInfo<DicType> selectDicTypeAll(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException;
}
