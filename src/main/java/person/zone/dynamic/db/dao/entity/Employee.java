package person.zone.dynamic.db.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "name", nullable = true, length = 20)
	private String name;

	@Column(name = "work_num", nullable = true, length = 20)
	private String workNum;

	@Column(name = "superior", nullable = true, length = 20)
	private String superior;

	@Column(name = "sex", nullable = true, length = 4)
	private int sex;
}
