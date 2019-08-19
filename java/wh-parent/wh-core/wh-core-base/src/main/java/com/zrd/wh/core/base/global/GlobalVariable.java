package com.zrd.wh.core.base.global;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局变量类
 */
public class GlobalVariable {
	
	public static ConcurrentHashMap<String, Object> loginMap;
	
	static {
		loginMap = new ConcurrentHashMap<String, Object>();
	}

}
