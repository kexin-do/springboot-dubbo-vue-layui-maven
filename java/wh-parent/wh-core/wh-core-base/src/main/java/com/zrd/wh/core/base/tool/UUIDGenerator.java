package com.zrd.wh.core.base.tool;

import java.util.UUID;

/**
 * 功能描述：ID生成类
 */
public class UUIDGenerator {
    public static String generate() {
    	return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
