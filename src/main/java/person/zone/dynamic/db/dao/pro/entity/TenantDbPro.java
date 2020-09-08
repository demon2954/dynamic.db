package person.zone.dynamic.db.dao.pro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tenant_db_pro")
public class TenantDbPro {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "tenant_id", nullable = true, length = 20)
	private String tenantId;

	@Column(name = "jdbc_url", nullable = true, length = 20)
	private String jdbcUrl;

	@Column(name = "username", nullable = true, length = 20)
	private String username;

	@Column(name = "password", nullable = true, length = 4)
	private String password;

	@Column(name = "driver_class_name", nullable = true, length = 4)
	private String driverClassName;

	@Column(name = "max_active", nullable = true, length = 4)
	private Integer maxActive;

	@Column(name = "max_idle", nullable = true, length = 4)
	private Integer maxIdle;

	@Column(name = "min_idle", nullable = true, length = 4)
	private Integer minIdle;
}
