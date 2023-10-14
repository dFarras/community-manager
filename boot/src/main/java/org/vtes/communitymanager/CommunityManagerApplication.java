package org.vtes.communitymanager;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.vtes.communitymanager")
public class CommunityManagerApplication {
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CommunityManagerApplication.class, args);
	}

}
