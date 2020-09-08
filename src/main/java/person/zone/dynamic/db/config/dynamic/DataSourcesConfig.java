package person.zone.dynamic.db.config.dynamic;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataSourcesConfig {
	@Autowired
	private JpaProperties jpaProperties;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.tenant-a")
	public DataSource tenantADataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.tenant-b")
	public DataSource tenantBDataSource() {
		return DataSourceBuilder.create().build();
	}

	private Map<Object, Object> dbSourceInstanceMap() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("tenantA", tenantADataSource());
		map.put("tenantB", tenantBDataSource());
		return map;
	}

	@Bean
	@Qualifier("dynamicDataSource")
	public DynamicDataSource dynamicDataSource() {
		DynamicDataSource dds = new DynamicDataSource();
		dds.setTargetDataSources(dbSourceInstanceMap());
		dds.setDefaultTargetDataSource(tenantADataSource());
		return dds;
	}

	@Bean
	@Qualifier("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dynamicDataSource());
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
		jpaVendorAdapter.setShowSql(true);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("person.zone.dynamic.db.dao.entity");
		entityManagerFactoryBean.setJpaPropertyMap(jpaProperties.getProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	@Qualifier("transactionManager")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
		return transactionManager;
	}
}