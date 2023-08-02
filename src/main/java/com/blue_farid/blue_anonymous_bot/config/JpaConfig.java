package com.blue_farid.blue_anonymous_bot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.blue_farid.blue_anonymous_bot.repository")
public class JpaConfig {

}