package person.zone.dynamic.db.config.dynamic;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import person.zone.dynamic.db.dao.pro.entity.TenantDbPro;
import person.zone.dynamic.db.dao.pro.repository.TenantDbProRepository;
import person.zone.dynamic.db.utils.SpringUtils;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	public static Map<Object, Object> dataSourcesMap = new ConcurrentHashMap<>(10);
	
	@Override
	protected Object determineCurrentLookupKey() {
		Set<String> tenantIdSet = (Set<String>) SpringUtils.getBean("tenantIdSet");
		String dataSource = DataSourceHolder.getDataSource();
		if (dataSource != null && !tenantIdSet.contains(dataSource)) {
			TenantDbProRepository tenantDbProRepository = (TenantDbProRepository) SpringUtils.getBean("tenantDbProRepository");

			TenantDbPro dbPro = new TenantDbPro();
			dbPro.setTenantId(dataSource);
			Example<TenantDbPro> example = Example.of(dbPro);
			TenantDbPro newDbPro = tenantDbProRepository.findOne(example).get();

			DataSource oneDS = DataSourceBuilder.create()
					.url(newDbPro.getJdbcUrl())
					.driverClassName(newDbPro.getDriverClassName())
					.username(newDbPro.getUsername())
					.password(newDbPro.getPassword())
					.build();

			DynamicDataSource.dataSourcesMap.put(dataSource, oneDS);
			
			this.afterPropertiesSet();
			tenantIdSet.add(dataSource);
		}
		return dataSource;
	}
}