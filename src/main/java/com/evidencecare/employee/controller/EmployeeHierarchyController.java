package com.evidencecare.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evidencecare.employee.model.Employee;
import com.evidencecare.employee.service.employeehierarchy.EmployeeHierarchyService;
import com.evidencecare.employee.util.EmployeeNotFoundException;
import com.evidencecare.employee.util.MultipleManagersException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeHierarchyController {

	private final EmployeeHierarchyService employeeHierarchyService;

	@GetMapping("/{name}")
	public ResponseEntity<Map<String, Object>> getEmployeeHierarchy(@PathVariable String name) {
		try {
			Employee employee = employeeHierarchyService.findEmployeeByName(name);
			if (employee == null) {
				throw new EmployeeNotFoundException("the employee : " + name + " does not exist");
			}
			List<String> managersHierarchy = employeeHierarchyService.getManagersHierarchy(employee);
			if (employeeHierarchyService.isDuplicateNameEmployee(name)) {
				StringBuilder managerNames = new StringBuilder();
				AtomicInteger i = new AtomicInteger(1);
				managersHierarchy.forEach(value -> {
					if (i.get() != managersHierarchy.size()) {
						managerNames.append(value).append(", ");
					}
					else {
						managerNames.append(value);
					}
					i.incrementAndGet();
				});
				String result = managerNames.toString();
				throw new MultipleManagersException(
						"Unable to process employee tree. " + employee.getName() + " has multiple managers: " + result);
			}
			int totalReports = employeeHierarchyService.getTotalDirectAndIndirectReports(employee);
			Map<String, Object> response = new HashMap<>();
			response.put("employee", employee.getName());
			response.put("managersHierarchy", managersHierarchy);
			response.put("totalReports", totalReports);
			return ResponseEntity.ok(response);
		}
		catch (EmployeeNotFoundException e) {
			// Handle the exception and return an appropriate response
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("error", "Employee not found");
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
		catch (MultipleManagersException e) {
			// Handle the exception and return an appropriate response
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("error", "Multiple Managers Found");
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

}
