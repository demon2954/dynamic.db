package person.zone.dynamic.db.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import person.zone.dynamic.db.dao.tenant.entity.Employee;
import person.zone.dynamic.db.dao.tenant.repository.EmployeeRepository;

@RestController
public class DbAgentTestController {
	@Autowired
	EmployeeRepository employeeRepository;

	// 在子容器中 注入一个 WebApplicationContext 的实例
	@Autowired
	private WebApplicationContext webApplicationContext;

	@GetMapping("getEmployeeById")
	public String getEmployeeById(@RequestParam(required = true, value = "tenantType") String tenantType, @RequestParam(required = true, value = "employeeId") Integer employeeId) {
		Employee one = employeeRepository.getOne(employeeId);
		return one.toString();
	}

	@GetMapping("Ioc")
	public HashMap<String, String[]> allInIoc() {
		return new HashMap<String, String[]>() {
			{
				put("容器", webApplicationContext.getBeanDefinitionNames());
			}
		};
	}

}
