package com.wowItemsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.wowItemsAPI")
@ComponentScan("com.wowItemsAPI")
public class WowItemsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WowItemsApiApplication.class, args);
	}

}
