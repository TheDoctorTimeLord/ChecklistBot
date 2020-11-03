package com.bot.checklistbot.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bot.checklistbot.serviceclasses.Constants.Bot;
import com.bot.checklistbot.serviceclasses.Constants.Package;

/**
 * Spring конфигурации для запуска приложения
 */
@Configuration
@PropertySource(Bot.BOT_PROPERTIES)
@ComponentScan(basePackages = { Package.BASE_PACKAGE })
@EnableJpaRepositories(Package.BASE_PACKAGE)
@EntityScan(Package.BASE_PACKAGE)
@EnableAutoConfiguration
public class ChecklistBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChecklistBotApplication.class, args);
	}
}