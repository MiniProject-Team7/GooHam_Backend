package com.uplus.ureka;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.uplus.ureka.repository")
@SpringBootApplication
public class GooHamBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GooHamBackendApplication.class, args);
	}

}
