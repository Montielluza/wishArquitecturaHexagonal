package com.wishstore.infrastructure.adapters.output.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "com.wishstore.infrastructure.adapters.output.persistence.repository")
@EntityScan(basePackages = "com.wishstore.infrastructure.adapters.output.persistence.entity")
public class PersistenceConfig {
}