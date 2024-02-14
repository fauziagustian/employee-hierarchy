package com.evidencecare.employee.service.employeehierarchy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.evidencecare.employee.dto.Employee;
import com.evidencecare.employee.service.dataload.DataLoadService;
import com.evidencecare.employee.util.ManagerNotFoundException;
import com.evidencecare.employee.util.NoHierarchyException;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeHierarchyServiceImpl implements EmployeeHierarchyService {

	private final DataLoadService dataLoadService;

	private List<Employee> employees;

	@PostConstruct
	public void loadData() {
		employees = dataLoadService.loadEmployeesFromFile();
	}

	@Override
	public Employee findEmployeeByName(String name) {

		return employees.stream()
			.filter(employee -> employee.getName().equalsIgnoreCase(name))
			.findFirst()
			.orElse(null);
	}

	@Override
	public List<String> getManagersHierarchy(Employee employee) {
		List<String> managersHierarchy = new ArrayList<>();
		Integer managerId = employee.getManagerId();

		if (managerId == null) {
			throw new NoHierarchyException(
					"Unable to process employee hierarchy. " + employee.getName() + " does not have any hierarchy.");
		}
		while (managerId != null) {
			var currentManagerId = managerId;
			Employee manager = employees.stream()
				.filter(e -> e.getId().equals(currentManagerId))
				.findFirst()
				.orElse(null);
			if (manager != null) {
				managersHierarchy.add(manager.getName());
				managerId = manager.getManagerId();
			}
			else {
				throw new ManagerNotFoundException("Manager not found for employee: " + employee.getName());
			}
		}
		return managersHierarchy;
	}

	@Override
	public int getTotalDirectAndIndirectReports(Employee employee) {
		int totalReports = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(employee.getId());
		while (!queue.isEmpty()) {
			Integer currentId = queue.poll();
			totalReports++;
			List<Integer> directReports = employees.stream()
				.filter(e -> e.getManagerId() != null && e.getManagerId().equals(currentId))
				.map(Employee::getId)
				.collect(Collectors.toList());
			queue.addAll(directReports);
		}
		return totalReports - 1; // Exclude the employee itself
	}

	public boolean isDuplicateNameEmployee(String name) {
		AtomicInteger count = new AtomicInteger(0);
		employees.forEach(t -> {
			if (t.getName().equalsIgnoreCase(name)) {
				count.incrementAndGet();
			}
		});

		return count.get() > 1;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

}
