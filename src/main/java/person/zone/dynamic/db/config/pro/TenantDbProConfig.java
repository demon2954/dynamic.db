package person.zone.dynamic.db.config.pro;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Order(-2)
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryTenantDbPro", 
		transactionManagerRef = "transactionManagerTenantDbPro", 
		basePackages = { "person.zone.dynamic.db.dao.pro.repository" }) // 设置Repository所在位置
public class TenantDbProConfig {

	@Autowired
	private JpaProperties jpaProperties;
	
    @Autowired
    private HibernateProperties hibernateProperties;

	@Autowired
	@Qualifier("tenantDbProDataSource")
	private DataSource tenantDbProDataSource;

	@Primary
	@Bean(name = "entityManagerTenantDbPro")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactoryTenantDbPro(builder).getObject().createEntityManager();
	}

	@Primary
	@Bean(name = "entityManagerFactoryTenantDbPro")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryTenantDbPro(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(tenantDbProDataSource).properties(getVendorProperties(tenantDbProDataSource))
				.packages("person.zone.dynamic.db.dao.pro.entity") // 设置实体类所在位置
				.persistenceUnit("tenantDbProPersistenceUnit").build();
	}

	private Map<String, Object> getVendorProperties(DataSource dataSource) {
		return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
	}

	@Primary
	@Bean(name = "transactionManagerTenantDbPro")
	public PlatformTransactionManager transactionManagerTenantDbPro(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactoryTenantDbPro(builder).getObject());
	}

}