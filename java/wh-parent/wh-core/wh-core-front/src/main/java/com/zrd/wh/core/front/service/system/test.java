package com.zrd.wh.core.front.service.system;

import com.zrd.wh.core.front.entity.system.DicType;

/**
 * @ClassName test
 * @Description TODO
 * @Author yangjixuan
 * @Date 2019/9/18 10:03
 * @Version 1.0
 **/
public class test {

    public static void main(String[] args){
        DicType dicType = new DicType();
        dicType.setDicCode("0001");
        dicType.setDicName("国产0001");
        System.out.println(dicType.toString());
    }
}
