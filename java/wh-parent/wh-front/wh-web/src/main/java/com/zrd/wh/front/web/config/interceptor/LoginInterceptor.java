package com.zrd.wh.front.web.config.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.constant.MessageModel;
import com.zrd.wh.front.web.config.annotation.NotCheckedMethod;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
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

		HandlerMethod checkMethod = (HandlerMethod)handler;

		NotCheckedMethod methodAnno = checkMethod.getMethodAnnotation(NotCheckedMethod.class);

		if(null != methodAnno) {
			return true;
		}

		Object user = request.getSession().getAttribute(Constant.LOGIN_USER);
		if(user == null){
			MessageModel model = new MessageModel();
			model.setStatusCode(MessageModel.LOGIN_TIMEOUT_CODE);
			model.setStatusInfo(MessageModel.LOGIN_TIMEOUT_MSG);
			response.getWriter().write(new Gson().toJson(model));
			return false;
		}

		//禁止浏览器缓存
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
}
