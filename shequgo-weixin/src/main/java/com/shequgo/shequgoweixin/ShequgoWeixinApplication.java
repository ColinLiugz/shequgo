package com.shequgo.shequgoweixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import queue.ActiveMQConfig;
import redis.RedisConfig;
import redis.RedisService;

@SpringBootApplication
@Import({ActiveMQConfig.class, RedisConfig.class, RedisService.class})
public class ShequgoWeixinApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShequgoWeixinApplication.class, args);
	}

}
