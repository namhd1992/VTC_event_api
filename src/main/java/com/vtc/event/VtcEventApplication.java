package com.vtc.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.vtc.*"})
@Configuration
@EnableJpaRepositories("com.vtc.*")
@EntityScan("com.vtc.*")
@ComponentScan("com.vtc*")
@PropertySource("classpath:application.properties")
@EnableScheduling
public class VtcEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(VtcEventApplication.class, args);
	}

}
