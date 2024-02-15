package com.evidencecare.employee.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ ManagerNotFoundException.class, NoHierarchyException.class, ManagerNotFoundException.class })
	public ResponseEntity<Object> handleEmployeeHierarchyException(Exception ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("error", "Employee Hierarchy Error");
		errorResponse.put("message", ex.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

}