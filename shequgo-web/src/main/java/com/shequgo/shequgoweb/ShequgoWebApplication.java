package com.shequgo.shequgoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import queue.ActiveMQConfig;
import redis.RedisConfig;
import redis.RedisService;

@SpringBootApplication
@Import({ActiveMQConfig.class, RedisConfig.class, RedisService.class})
public class ShequgoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShequgoWebApplication.class, args);
	}

}
