package com.evidencecare.employee.service.dataload;

import java.util.List;

import com.evidencecare.employee.dto.Employee;

public interface DataLoadService {

	List<Employee> loadEmployeesFromFile();

}
