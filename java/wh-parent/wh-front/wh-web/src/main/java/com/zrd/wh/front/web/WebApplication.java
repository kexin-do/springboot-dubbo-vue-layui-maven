package com.zrd.wh.front.web;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableDubboConfiguration
@ServletComponentScan // 扫描监听器
@ComponentScan("com.zrd.wh.front.web.*")
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    /**
     * 文件上传配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置上传文件路径 为项目同级路径
        String location = System.getProperty("user.dir") + File.separator + "uploadTempFile";
        File filePath = new File(location);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        factory.setLocation(location);
        // 单个文件最大
        factory.setMaxFileSize("204800KB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("204800KB");
        return factory.createMultipartConfig();
    }

    /**
     * 使用Tomcat部署项目是必须继承SpringBootServletInitializer类并重写configure方法
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApplication.class);
    }
}
