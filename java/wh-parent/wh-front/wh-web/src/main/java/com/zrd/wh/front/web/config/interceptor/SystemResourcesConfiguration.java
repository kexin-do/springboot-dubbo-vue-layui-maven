package com.zrd.wh.front.web.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/**
 * 系统的mvc配置
 * @author kexin
 * @date 2018/11/09
 */
@Configuration
public class SystemResourcesConfiguration extends WebMvcConfigurationSupport {

	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor())//添加自定义拦截器
				.addPathPatterns("/**")//拦截所有请求
				.excludePathPatterns("/static/**");//静态资源放行
		super.addInterceptors(registry);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		//静态资源放行
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/")
				.addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/resources/")
				.addResourceLocations("classpath:/public/");
		
		super.addResourceHandlers(registry);
	}

	//登录页面的controller
	/*@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/csmsp/toLogin").setViewName("login");
		super.addViewControllers(registry);
	}*/
	
}
