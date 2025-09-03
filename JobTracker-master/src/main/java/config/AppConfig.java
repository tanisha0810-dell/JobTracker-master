package config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"dao", "model", "service"}) // scan your packages
@EnableJpaRepositories(basePackages = "dao")
@EntityScan(basePackages = "model")
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // or your driver
        dataSource.setUrl("jdbc:mysql://localhost:3306/job_application_tracker");
        dataSource.setUsername("root");
        dataSource.setPassword("tanishA@2005");
        return dataSource;
    }
}
