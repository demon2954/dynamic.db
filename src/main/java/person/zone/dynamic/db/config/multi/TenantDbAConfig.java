//package person.zone.dynamic.db.config.multi;
//
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryTenantA", 
//		transactionManagerRef = "transactionManagerTenantA", 
//		basePackages = { "person.zone.dynamic.db.dao.repository" }) // 设置Repository所在位置
//public class TenantDbAConfig {
//
//	@Autowired
//	private JpaProperties jpaProperties;
//	
//    @Autowired
//    private HibernateProperties hibernateProperties;
//
//	@Autowired
//	@Qualifier("tenantADataSource")
//	private DataSource tenantADataSource;
//
//	@Primary
//	@Bean(name = "entityManagerTenantA")
//	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//		return entityManagerFactoryTenantA(builder).getObject().createEntityManager();
//	}
//
//	@Primary
//	@Bean(name = "entityManagerFactoryTenantA")
//	public LocalContainerEntityManagerFactoryBean entityManagerFactoryTenantA(EntityManagerFactoryBuilder builder) {
//		return builder.dataSource(tenantADataSource).properties(getVendorProperties(tenantADataSource))
//				.packages("person.zone.dynamic.db.dao.entity") // 设置实体类所在位置
//				.persistenceUnit("tenantAPersistenceUnit").build();
//	}
//
//	private Map<String, Object> getVendorProperties(DataSource dataSource) {
//		return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
//	}
//
//	@Primary
//	@Bean(name = "transactionManagerTenantA")
//	public PlatformTransactionManager transactionManagerTenantA(EntityManagerFactoryBuilder builder) {
//		return new JpaTransactionManager(entityManagerFactoryTenantA(builder).getObject());
//	}
//
//}