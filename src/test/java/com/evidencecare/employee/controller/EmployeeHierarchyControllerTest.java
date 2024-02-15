package com.evidencecare.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.evidencecare.employee.dto.Employee;
import com.evidencecare.employee.service.employeehierarchy.EmployeeHierarchyService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class EmployeeHierarchyControllerTest {

	private MockMvc mockMvc;

	@Mock
	private EmployeeHierarchyService employeeHierarchyService;

	@InjectMocks
	private EmployeeHierarchyController employeeHierarchyController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeHierarchyController).build();
	}

	@Test
	void testGetEmployeeHierarchy_Success() throws Exception {
		// Mocking employeeHierarchyService to return sample data
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1);
		mockEmployee.setName("John");
		mockEmployee.setManagerId(1);
		List<String> mockManagersHierarchy = Arrays.asList("Manager1", "Manager2");
		int mockTotalReports = 5;
		when(employeeHierarchyService.findEmployeeByName(anyString())).thenReturn(mockEmployee);
		when(employeeHierarchyService.getManagersHierarchy(any())).thenReturn(mockManagersHierarchy);
		when(employeeHierarchyService.getTotalDirectAndIndirectReports(any())).thenReturn(mockTotalReports);

		// Performing the request and verifying the response
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/John"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		verify(employeeHierarchyService).findEmployeeByName(anyString());

	}

	@Test
	void testGetEmployeeHierarchy_MultipleManagers() throws Exception {
		Employee mockEmployee = new Employee();
		mockEmployee.setId(1);
		mockEmployee.setName("John");
		mockEmployee.setManagerId(1);
		List<String> mockManagersHierarchy = Arrays.asList("Manager1", "Manager2");
		when(employeeHierarchyService.findEmployeeByName(anyString())).thenReturn(mockEmployee);
		when(employeeHierarchyService.getManagersHierarchy(any())).thenReturn(mockManagersHierarchy);
		when(employeeHierarchyService.isDuplicateNameEmployee(anyString())).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees/John"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Multiple Managers Found"))
			.andReturn();
	}

	@Test
	void testGetEmployeeHierarchy_nameNotfound() throws Exception {
		when(employeeHierarchyService.findEmployeeByName(anyString())).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/John"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee not found"))
			.andReturn();
	}

}