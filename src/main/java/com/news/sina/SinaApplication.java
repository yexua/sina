package com.news.sina;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.news.sina.dao")
public class SinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SinaApplication.class, args);
	}

}
