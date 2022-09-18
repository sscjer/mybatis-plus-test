package com.cj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cj.modular.mapper")
@SpringBootApplication
public class MPTestApplication {

	public static strictfp void main(String[] args) {
		SpringApplication.run(MPTestApplication.class, args);
	}

}
