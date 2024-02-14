package com.evidencecare.employee.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.evidencecare.employee.dto.Employee;
import com.evidencecare.employee.service.employeehierarchy.EmployeeHierarchyServiceImpl;
import com.evidencecare.employee.util.MultipleManagersException;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeHierarchyControllerTest {

	private final EmployeeHierarchyServiceImpl employeeHierarchyService = Mockito.mock(EmployeeHierarchyServiceImpl.class);

	@InjectMocks
	private EmployeeHierarchyController employeeHierarchyController;

	@SuppressWarnings("null")
	@Test
	public void testGetEmployeeHierarchy_Success() {
		// Test case for successful retrieval of employee hierarchy
		String name = "Evelina";
		Employee employee = new Employee();
		employee.setName(name);
		List<String> managersHierarchy = Arrays.asList("Eveleen", "Kacie", "Raelynn");
		int totalReports = 5; // Sample total reports count
		when(employeeHierarchyService.findEmployeeByName(name)).thenReturn(employee);
		when(employeeHierarchyService.getManagersHierarchy(employee)).thenReturn(managersHierarchy);
		when(employeeHierarchyService.getTotalDirectAndIndirectReports(employee)).thenReturn(totalReports);

		ResponseEntity<Map<String, Object>> responseEntity = employeeHierarchyController.getEmployeeHierarchy(name);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(name, responseEntity.getBody().get("employee"));
		assertEquals(managersHierarchy, responseEntity.getBody().get("managersHierarchy"));
		assertEquals(totalReports, responseEntity.getBody().get("totalReports"));
	}

	@Test
	public void testGetEmployeeHierarchy_NotFound() {
		// Test case for scenario when employee is not found
		String name = "NonExistingEmployee";
		when(employeeHierarchyService.findEmployeeByName(name)).thenReturn(null);

		ResponseEntity<Map<String, Object>> responseEntity = employeeHierarchyController.getEmployeeHierarchy(name);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}

	@Test
	public void testGetEmployeeHierarchy_MultipleManagersException() {
		// Test case for scenario when employee has multiple managers
		String name = "EmployeeWithMultipleManagers";
		Employee employee = new Employee();
		employee.setName(name);
		when(employeeHierarchyService.findEmployeeByName(name)).thenReturn(employee);
		when(employeeHierarchyService.isDuplicateNameEmployee(name)).thenReturn(true);

		assertThrows(MultipleManagersException.class, () -> {
			employeeHierarchyController.getEmployeeHierarchy(name);
		});
	}

}
