package person.zone.dynamic.db.config.db.dynamic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import person.zone.dynamic.db.dao.pro.entity.TenantDbPro;
import person.zone.dynamic.db.dao.pro.repository.TenantDbProRepository;

@Order(-1)
@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = { "person.zone.dynamic.db.dao.tenant.repository" }) // 设置Repository所在位置
public class DataSourcesConfig {
	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private TenantDbProRepository tenantDbProRepository;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String hibernateDialect;

	private Map<Object, Object> dbSourceInstanceMap() {
		List<TenantDbPro> dbProList = tenantDbProRepository.findAll();
		for (TenantDbPro onePro : dbProList) {
			DataSource oneDS = DataSourceBuilder.create()
					.url(onePro.getJdbcUrl())
					.driverClassName(onePro.getDriverClassName())
					.username(onePro.getUsername())
					.password(onePro.getPassword())
					.build();
			DynamicDataSource.dataSourcesMap.put(onePro.getTenantId(), oneDS);
		}
		return DynamicDataSource.dataSourcesMap;
	}

	@Bean
	@Qualifier("dynamicDataSource")
	@DependsOn({ "springUtils", "tenantDbProDataSource" })
	public DynamicDataSource dynamicDataSource() {
		DynamicDataSource dds = new DynamicDataSource();
		dds.setTargetDataSources(dbSourceInstanceMap());
		Set<Object> keySet = dbSourceInstanceMap().keySet();
		DataSource defaultDataSource = null;
		for (Object one : keySet) {
			defaultDataSource = (DataSource) dbSourceInstanceMap().get(one);
			break;
		}
		dds.setDefaultTargetDataSource(defaultDataSource);
		return dds;
	}

	@Bean
	@Qualifier("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dynamicDataSource());
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabasePlatform(hibernateDialect);
		jpaVendorAdapter.setShowSql(true);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("person.zone.dynamic.db.dao.tenant.entity");
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

	@Bean
	@Qualifier("tenantIdSet")
	public Set<String> tenantIdSet() {
		List<TenantDbPro> dbProList = tenantDbProRepository.findAll();
		Set<String> tenantIdSet = new ConcurrentSkipListSet<String>();
		for (TenantDbPro onePro : dbProList) {
			tenantIdSet.add(onePro.getTenantId());
		}
		return tenantIdSet;
	}
}