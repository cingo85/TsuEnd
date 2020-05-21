package com.leadtek.nuu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan
@EnableSwagger2
public class TsuApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TsuApplication.class, args);
	}

}
