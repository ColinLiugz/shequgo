package com.shequgo.shequgoserviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("entity.**")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.shequgo.shequgoserviceuser.repo")
public class ShequgoServiceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShequgoServiceUserApplication.class, args);
	}

}
