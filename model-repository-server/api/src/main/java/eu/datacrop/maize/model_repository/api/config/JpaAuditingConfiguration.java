package eu.datacrop.maize.model_repository.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**********************************************************************************************************************
 * This is a configuration class (concerns JPA profiles).
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@Profile("devmysql")
public class JpaAuditingConfiguration {
}
