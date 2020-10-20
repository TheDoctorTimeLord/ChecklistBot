package com.bot.checklistbot.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring конфигурации для запуска приложения
 */
@Configuration
@PropertySource("classpath:bot.properties")
@ComponentScan(basePackages = {"com.bot.checklistbot"})
@EnableAutoConfiguration
public class ChecklistBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChecklistBotApplication.class, args);
	}
}