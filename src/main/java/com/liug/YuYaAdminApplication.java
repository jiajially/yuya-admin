package com.liug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class YuYaAdminApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YuYaAdminApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(YuYaAdminApplication.class, args);
	}

}
