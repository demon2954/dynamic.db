package person.zone.dynamic.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import person.zone.dynamic.db.dao.entity.Employee;
import person.zone.dynamic.db.dao.repository.EmployeeRepository;

@RestController
public class DbAgentTestController {
	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("getEmployeeById")
	public String getEmployeeById(@RequestParam(required = true, value = "tenantType") String tenantType,
			@RequestParam(required = true, value = "employeeId") Integer employeeId) {
		Employee one = employeeRepository.getOne(employeeId);
		return one.toString();
	}

}
