package integration.com.mapledoum.pointscalc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.mapledoum.pointscalc.repository")
@EnableTransactionManagement
@ActiveProfiles("test")
public class H2JpaConfig {

}
