package com.bot.checklistbot.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring конфигурации для запуска приложения
 */
@Configuration
@PropertySource("classpath:bot.properties")
@ComponentScan(basePackages = {"com.bot.checklistbot"})
@EnableJpaRepositories("com.bot.checklistbot")
@EntityScan("com.bot.checklistbot")
@EnableAutoConfiguration
public class ChecklistBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChecklistBotApplication.class, args);
	}
}