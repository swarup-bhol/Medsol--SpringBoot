package com.sm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.sm.util.Email;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties
@PropertySource("classpath:application.yml")
@Getter @Setter
public class EnvironmentConfig {	
	public Email email;
}
