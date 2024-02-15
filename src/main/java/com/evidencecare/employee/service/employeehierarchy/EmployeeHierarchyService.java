package com.evidencecare.employee.service.employeehierarchy;

import java.util.List;

import com.evidencecare.employee.model.Employee;

public interface EmployeeHierarchyService {

	Employee findEmployeeByName(String name);

	List<String> getManagersHierarchy(Employee employee);

	int getTotalDirectAndIndirectReports(Employee employee);

	boolean isDuplicateNameEmployee(String name);

}
