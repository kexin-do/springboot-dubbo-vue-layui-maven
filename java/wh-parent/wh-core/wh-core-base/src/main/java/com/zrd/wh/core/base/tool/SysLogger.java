package com.zrd.wh.core.base.tool;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysLogger {

	private static Map<String, Logger> loggerMap = null;

	static {
		loggerMap = new HashMap<>();
	}

	public static void debug(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		if (log.isDebugEnabled()) {
			log.debug(message.toString());
		}
	}

	public static void debug(String tag, Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		if (log.isDebugEnabled()) {
			log.debug(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
		}
	}

	public static void info(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		if (log.isInfoEnabled()) {
			log.info(message.toString());
		}
	}

	public static void info(String tag, Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		if (log.isInfoEnabled()) {
			log.info(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
		}
	}

	public static void warn(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.warn(message.toString());
	}

	public static void warn(String tag, Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.warn(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
	}

	public static void error(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.error(message.toString());
	}

	public static void error(String tag, Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.error(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
	}

	/**
	 * 根据类名获得logger对象
	 * 
	 * @param className
	 * @return
	 */
	private static Logger getLogger(String className) {
		Logger log = null;
		if (loggerMap.containsKey(className)) {
			log = loggerMap.get(className);
		} else {
			try {
				log = LoggerFactory.getLogger(Class.forName(className));
				loggerMap.put(className, log);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return log;
	}

	/**
	 * 获取最开始的调用者所在类
	 * 
	 * @return
	 */
	private static String getClassName() {
		Throwable th = new Throwable();
		StackTraceElement[] stes = th.getStackTrace();
		StackTraceElement ste = stes[2];
		return ste.getClassName();
	}

}
