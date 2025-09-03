package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "dao")
@EntityScan(basePackages = "model")
@EnableTransactionManagement
public class DatabaseConfig {
    // No explicit DataSource bean needed if using Spring Boot starters and application.properties config
}
