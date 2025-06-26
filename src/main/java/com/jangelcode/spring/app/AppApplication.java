package com.jangelcode.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	public CommandLineRunner initAdminUser(JdbcTemplate jdbcTemplate) {
		return args -> {
			Integer count = jdbcTemplate.queryForObject(
					"SELECT COUNT(*) FROM usuario WHERE username = 'admin'", Integer.class);
			if (count == null || count == 0) {
				jdbcTemplate.execute(
						"INSERT INTO usuario (id, username, password, email, rol) VALUES (1, 'admin', '$2a$12$VzAdD3x8QbzPIVxpoekCXuVv4GLvlYS.JbBH6aav9TAIO2FrZngqi', 'admin@email.com', 'ADMIN')");
			}
		};
	}
}
