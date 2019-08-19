package com.zrd.wh.core.base.tool;

import java.io.IOException;
import java.util.Properties;

/**
 * @version 创建时间：2008-7-11 下午02:17:37
 * @function 读取properties文件工具类
 */
public final class PropertiesUtil {
	private static final String configfile = "config.properties"; // 读取/WEB-INF/calsses/config.properties属性文件
	private static Properties props = null;	//Properties工具类对象。
	static{
		try {
			props = new  Properties();
			props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(configfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SysLogger.error("=============发送tonglk信息-读取config.properties文件异常===============");
		}
	}
	/*
	 * 防止新建对象
	 */
	private PropertiesUtil(){
		
	}
	/**
	 * 返回配置文件key对应的值
	 * @param key
	 * @return key对应的值
	 */
	public static String getValueByKey(String key){
		return props.getProperty(key);
	}
	
	/**
	 * 返回配置文件key对应的值
	 * @param key
	 * @return key对应的值
	 */
	public static String getValueByKey(String proPath, String key){
		try {
			if (StringUtil.isEmpty(proPath)) {
				proPath = configfile;
			}
			props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(proPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SysLogger.error("=============发送tonglk信息-读取config.properties文件异常===============");
		}
		return props.getProperty(key);
	}
}