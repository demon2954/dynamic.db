//package person.zone.dynamic.db.config.multi;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class DataSourcesConfig {
//	@Bean(name = "tenantADataSource")
//	@Qualifier("tenantADataSource")
//	@Primary
//	@ConfigurationProperties(prefix = "spring.datasource.tenant-a")
//	public DataSource tenantADataSource() {
//		System.out.println("tenantA db built");
//		return DataSourceBuilder.create().build();
//	}
//
//	@Bean(name = "tenantBDataSource")
//	@Qualifier("tenantBDataSource")
//	@ConfigurationProperties(prefix = "spring.datasource.tenant-b")
//	public DataSource tenantBDataSource() {
//		System.out.println("tenantB db built");
//		return DataSourceBuilder.create().build();
//	}
//}