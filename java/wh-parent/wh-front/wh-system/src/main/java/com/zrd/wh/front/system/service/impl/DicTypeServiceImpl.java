package com.zrd.wh.front.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrd.wh.core.base.exception.DBException;
import com.zrd.wh.core.base.exception.SysException;
import com.zrd.wh.core.base.service.BaseService;
import com.zrd.wh.core.front.entity.system.DicType;
import com.zrd.wh.core.front.service.system.IDicTypeService;
import com.zrd.wh.front.system.mapper.IDicTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DicTypeService
 * @Description 数据字典类型接口实现
 * @Author yangjixuan
 * @Date 2019/9/18 11:12
 * @Version 1.0
 **/
@Service(interfaceClass = IDicTypeService.class,
        parameters = {"insert.timeout","5000",
                "delete.timeout","5000",
                "update.timeout","5000",
                "selectDicTypeInfo.timeout","5000",
                "selectDicType.timeout","5000",
                "selectDicTypeAll.timeout","5000"})
@Component
public class DicTypeServiceImpl extends BaseService implements IDicTypeService {

    private static final long serialVersionUID = 4229513645187908954L;

    @Resource
    private IDicTypeMapper dicTypeMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int insert(DicType dicType) throws DBException, SysException {
        int reNum = -9999;
        try{
            reNum = dicTypeMapper.insert(dicType);
        } catch (DataAccessException de){
            throw new DBException(de);
        } catch (Exception e){
            throw new SysException(e);
        }
        return reNum;
    }

    @Override
    public void delete(String dicCode) throws DBException, SysException {
        try{
            dicTypeMapper.delete(dicCode);
        } catch (DataAccessException de){
            throw new DBException(de);
        } catch (Exception e){
            throw new SysException(e);
        }
    }

    @Override
    public int update(DicType dicType) throws DBException, SysException {
        int reNum = -9999;
        try {
            reNum = dicTypeMapper.updateDicTypeInfo(dicType);
        } catch (DataAccessException de){
            throw new DBException(de);
        } catch (Exception e){
            throw new SysException(e);
        }
        return reNum;
    }

    @Override
    public DicType selectDicTypeInfo(String dicCode) throws DBException, SysException {
        DicType dicType = null;
        try {
            dicType = dicTypeMapper.selectDicTypeInfo(dicCode);
        } catch (DataAccessException de){
            throw new DBException(de);
        } catch (Exception e){
            throw new SysException(e);
        }
        return dicType;
    }

    @Override
    public List<DicType> selectDicType(Map<String, String> param) throws DBException, SysException {
        List<DicType> dicTypeList = null;
        try {
            dicTypeList = dicTypeMapper.selectDicType(param);
        } catch (DataAccessException de){
            throw new DBException(de);
        } catch (Exception e){
            throw new SysException(e);
        }
        return dicTypeList;
    }

    @Override
    public PageInfo<DicType> selectDicTypeAll(int pageNum, int pageSize, Map<String, String> param) throws DBException, SysException {
        List<DicType> list = null;
        PageInfo<DicType> dicTypePageInfo = null;
        try {
            Page<DicType> dicTypePage = PageHelper.startPage(pageNum, pageSize);
            dicTypeMapper.selectDicTypeAll(param);
            dicTypePageInfo = new PageInfo<>(dicTypePage.getResult());
        } catch (DataAccessException ex) {
            throw new DBException(ex);
        } catch (Exception ex) {
            throw new SysException(ex);
        }
        return dicTypePageInfo;
    }
}
