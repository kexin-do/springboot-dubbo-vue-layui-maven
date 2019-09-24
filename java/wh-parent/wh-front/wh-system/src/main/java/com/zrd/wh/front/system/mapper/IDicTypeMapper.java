package com.zrd.wh.front.system.mapper;

import com.github.pagehelper.Page;
import com.zrd.wh.core.front.entity.system.DicType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@Author yangjixuan
 *@Description 数据字典类型Mapper
 *@Date 2019/9/18 13:23
 **/
public interface IDicTypeMapper {

    /**
     *@Author yangjixuan
     *@Description 新增数据字典类型
     *@Date 2019/9/18 13:33
     *@Param [dicType]
     *@return int
     **/
    public int insert(DicType dicType);

    /**
     *@Author yangjixuan
     *@Description 删除数据字典类型
     *@Date 2019/9/18 13:35
     *@Param [dicCode]
     *@return void
     **/
    public void delete(@Param("dicCode") String dicCode);

    /**
     *@Author yangjixuan
     *@Description //TODO
     *@Date 2019/9/18 13:36
     *@Param [dicType]
     *@return int
     **/
    public int updateDicTypeInfo(DicType dicType);

    /**
     *@Author yangjixuan
     *@Description 查询数据字典类型信息
     *@Date 2019/9/18 13:39
     *@Param [dicCode]
     *@return com.zrd.wh.core.front.entity.system.DicType
     **/
    public DicType selectDicTypeInfo(@Param("dicCode") String dicCode);

    /**
     *@Author yangjixuan
     *@Description 数据字典类型条件查询
     *@Date 2019/9/18 13:39
     *@Param [param]
     *@return java.util.List<com.zrd.wh.core.front.entity.system.DicType>
     **/
    public List<DicType> selectDicType(Map<String, String> param);

    /**
     *@Author yangjixuan
     *@Description 数据字典类型分页查询
     *@Date 2019/9/18 13:40
     *@Param [param]
     *@return com.github.pagehelper.Page<com.zrd.wh.core.front.entity.system.DicType>
     **/
    public Page<DicType> selectDicTypeAll(Map<String, String> param);
}
