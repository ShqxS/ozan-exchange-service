package com.ozan.exchange;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class OzanExchangeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OzanExchangeServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		log.info("OzanExchangeServiceApplication running in UTC timezone :" + LocalDateTime.now());
	}
}
