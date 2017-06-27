package com.liug;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class BlackEyeJspApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BlackEyeJspApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BlackEyeJspApplication.class, args);
	}

}
