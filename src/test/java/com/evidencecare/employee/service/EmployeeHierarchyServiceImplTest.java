package com.evidencecare.employee.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.evidencecare.employee.dto.Employee;
import com.evidencecare.employee.service.dataload.DataLoadService;
import com.evidencecare.employee.service.employeehierarchy.EmployeeHierarchyServiceImpl;

@ExtendWith(SpringExtension.class)
public class EmployeeHierarchyServiceImplTest {

	private final DataLoadService dataLoadService = Mockito.mock(DataLoadService.class);

	private final EmployeeHierarchyServiceImpl employeeHierarchyServiceImpl = new EmployeeHierarchyServiceImpl(
			dataLoadService, null);

	@Test
	public void testLoadData() {
		// Test case for successful loading of data
		List<Employee> employees = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setName("Employee 1");
		employee1.setManagerId(null);
		employees.add(employee1);

		// Mocking the behavior of the dataLoadService to return the test data
		when(dataLoadService.loadEmployeesFromFile()).thenReturn(employees);

		// Call the loadData method
		employeeHierarchyServiceImpl.loadData();

		// Assert that the employees list is loaded correctly
		assertEquals(1, employeeHierarchyServiceImpl.getEmployees().size());
		assertEquals(employee1, employeeHierarchyServiceImpl.getEmployees().get(0));
	}

	@Test
	public void testFindEmployeeByName_Found() {
		// Test case for scenario when employee is found
		List<Employee> employees = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setName("Employee 1");
		employees.add(employee1);

		// Mocking the behavior of the dataLoadService to return the test data
		when(dataLoadService.loadEmployeesFromFile()).thenReturn(employees);

		// Call the loadData method
		employeeHierarchyServiceImpl.loadData();

		// Call the findEmployeeByName method
		Employee foundEmployee = employeeHierarchyServiceImpl.findEmployeeByName("Employee 1");

		// Assert that the correct employee is found
		assertEquals(employee1, foundEmployee);
	}

	@Test
	public void testFindEmployeeByName_NotFound() {
		// Test case for scenario when employee is not found
		List<Employee> employees = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setName("Employee 1");
		employees.add(employee1);

		// Mocking the behavior of the dataLoadService to return the test data
		when(dataLoadService.loadEmployeesFromFile()).thenReturn(employees);

		// Call the loadData method
		employeeHierarchyServiceImpl.loadData();

		// Call the findEmployeeByName method with a non-existing name
		Employee foundEmployee = employeeHierarchyServiceImpl.findEmployeeByName("NonExistingEmployee");

		// Assert that no employee is found
		assertNull(foundEmployee);
	}

}
