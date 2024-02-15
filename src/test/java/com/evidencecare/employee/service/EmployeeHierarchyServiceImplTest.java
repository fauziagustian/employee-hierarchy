package com.evidencecare.employee.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.evidencecare.employee.model.Employee;
import com.evidencecare.employee.service.dataload.DataLoadService;
import com.evidencecare.employee.service.employeehierarchy.EmployeeHierarchyServiceImpl;
import com.evidencecare.employee.util.ManagerNotFoundException;
import com.evidencecare.employee.util.NoHierarchyException;

class EmployeeHierarchyServiceImplTest {

	@Mock
	private DataLoadService dataLoadService;

	private EmployeeHierarchyServiceImpl employeeHierarchyService;

	private List<Employee> testEmployees = new ArrayList<Employee>();

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		testEmployees = Arrays.asList(new Employee(1, "John", 2), new Employee(2, "Alice", 3),
				new Employee(3, "Bob", null));
		employeeHierarchyService = new EmployeeHierarchyServiceImpl(dataLoadService, testEmployees);
		employeeHierarchyService.loadData();
	}

	@Test
    void testLoadData() {
        when(dataLoadService.loadEmployeesFromFile()).thenReturn(testEmployees);
        employeeHierarchyService.loadData();
        assertEquals(testEmployees, employeeHierarchyService.getEmployees());
    }

	@Test
	void testFindEmployeeByName() {
		employeeHierarchyService.getEmployees().addAll(testEmployees);
		assertEquals(testEmployees.get(0), employeeHierarchyService.findEmployeeByName("John"));
		assertNull(employeeHierarchyService.findEmployeeByName("Unknown"));
	}

	@Test
	void testGetManagersHierarchy_success() {
		employeeHierarchyService.getEmployees().addAll(testEmployees);
		assertEquals(Arrays.asList("Alice", "Bob"),
				employeeHierarchyService.getManagersHierarchy(testEmployees.get(0)));
	}

	@Test
	void testGetManagersHierarchy_managerNotfound() {
		employeeHierarchyService.getEmployees().addAll(testEmployees);
		assertThrows(ManagerNotFoundException.class,
				() -> employeeHierarchyService.getManagersHierarchy(new Employee(4, "New", 5)));
	}

	@Test
	void testGetManagersHierarchy_noHierarchy() {
		employeeHierarchyService.getEmployees().addAll(testEmployees);
		assertThrows(NoHierarchyException.class,
				() -> employeeHierarchyService.getManagersHierarchy(testEmployees.get(2)));
	}

	@Test
	void testGetTotalDirectAndIndirectReports() {
		employeeHierarchyService.getEmployees().addAll(testEmployees);
		assertEquals(0, employeeHierarchyService.getTotalDirectAndIndirectReports(testEmployees.get(0)));
		assertEquals(1, employeeHierarchyService.getTotalDirectAndIndirectReports(testEmployees.get(1)));
		assertEquals(2, employeeHierarchyService.getTotalDirectAndIndirectReports(testEmployees.get(2)));
	}

	@Test
	void testIsDuplicateNameEmployee() {
		testEmployees = Arrays.asList(new Employee(1, "John", 2), new Employee(1, "John", 2));
		employeeHierarchyService.getEmployees().addAll(testEmployees);
		assertTrue(employeeHierarchyService.isDuplicateNameEmployee("John"));
		assertFalse(employeeHierarchyService.isDuplicateNameEmployee("Unknown"));
	}

}