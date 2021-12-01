package com.record.travel;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //jpa 이용하면서 AuditionEntityListener 활성화위해 추가
public class TravelApplication {

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

	}

	public static void main(String[] args) {
		SpringApplication.run(TravelApplication.class, args);
	}

}
