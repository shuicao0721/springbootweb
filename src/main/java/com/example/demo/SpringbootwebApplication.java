package com.example.demo;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootwebApplication.class, args);
	}
	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        factory.setMaxFileSize("256KB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("512KB");
        // Sets the directory location where files will be stored.
        // factory.setLocation("路径地址");
        return factory.createMultipartConfig();
    }
}
