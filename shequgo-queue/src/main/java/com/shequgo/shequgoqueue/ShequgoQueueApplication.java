package com.shequgo.shequgoqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import queue.ActiveMQConfig;

@SpringBootApplication
@Import({ActiveMQConfig.class})
public class ShequgoQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShequgoQueueApplication.class, args);
	}

}
