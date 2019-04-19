package com.shequgo.shequgoservicecommodity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("entity.**")
@EnableJpaRepositories(basePackages = "com.shequgo.shequgoservicecommodity.repo")
public class ShequgoServiceCommodityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShequgoServiceCommodityApplication.class, args);
	}

}
