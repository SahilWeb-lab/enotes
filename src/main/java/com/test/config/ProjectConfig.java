package com.test.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ProjectConfig {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public AuditorAware<Integer> getAuditorAware() {
		return new AuditorAwareConfig();
	}
	
}
