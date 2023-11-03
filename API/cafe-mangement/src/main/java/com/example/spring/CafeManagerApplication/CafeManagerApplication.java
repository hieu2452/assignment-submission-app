package com.example.spring.CafeManagerApplication;

import com.example.spring.CafeManagerApplication.Utils.CafeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableSwagger2
public class CafeManagerApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CafeManagerApplication.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(CafeManagerApplication.class, args);

	}

}
