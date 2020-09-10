package person.zone.dynamic.db.config.db.pro;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TenantDbProDataSourcesConfig {
	@Bean(name = "tenantDbProDataSource")
	@Qualifier("tenantDbProDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.tenant-pro")
	public DataSource tenantDbProDataSource() {
		System.out.println("tenantDbPro db built");
		return DataSourceBuilder.create().build();
	}

}