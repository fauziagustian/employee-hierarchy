package com.evidencecare.employee.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListEmployee {

	private List<Employee> employees;

	private int page;

	private int size;

	private int totalData;

}
