package com.zrd.wh.front.web.config.interceptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.global.GlobalVariable;
import com.zrd.wh.core.base.tool.StringUtil;
import com.zrd.wh.core.front.entity.auth.User;
import com.zrd.wh.core.front.service.auth.IRoleService;
import com.zrd.wh.front.web.config.annotation.NotCheckedMethod;
import com.zrd.wh.front.web.front.login.entity.UserLoginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 * @author kexin
 * @date 2018/11/09
 */
//@Component
public class LoginInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//如果不是映射到方法直接放过
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		//禁止浏览器缓存
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
}
