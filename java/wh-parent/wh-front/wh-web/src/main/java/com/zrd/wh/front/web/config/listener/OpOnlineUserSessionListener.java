package com.zrd.wh.front.web.config.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.zrd.wh.core.base.constant.Constant;
import com.zrd.wh.core.base.global.GlobalVariable;
import com.zrd.wh.core.front.entity.auth.User;

/**
 * 使用说明  需要在启动类上添加@ServletComponentScan注解，以扫描到该监听器
 * 用户监听器实现用于移除在线登录用户信息
 * @author kexin 2018/11/19
 *
 */
@WebListener//监听器注解
public class OpOnlineUserSessionListener implements HttpSessionListener {

	
	/**
	 * session被销毁时触发,如下情况
	 * 		1.主动调用session.invalidate()
	 * 		2.session超时
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		//获取当前用户信息
		User loginUser = (User)session.getAttribute(Constant.LOGIN_USER);
		Object userStatus = session.getAttribute(Constant.USER_STATUS);
		//移除在线用户 当用户登录状态为 null 时清除
		if(loginUser != null && userStatus == null) {
			GlobalVariable.loginMap.remove(loginUser.getUserId());
		}
	}
	
	//session被创建时触发
	@Override
	public void sessionCreated(HttpSessionEvent event) {}

}
