package org.example.spring1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // created_at, updated_at update
@SpringBootApplication
public class Spring1Application {
	public static void main(String[] args) {
		SpringApplication.run(Spring1Application.class, args);
	}
}
