package com.evidencecare.employee.service.dataload;

import java.util.List;

import com.evidencecare.employee.model.Employee;

public interface DataLoadService {

	List<Employee> loadEmployeesFromFile();

}
