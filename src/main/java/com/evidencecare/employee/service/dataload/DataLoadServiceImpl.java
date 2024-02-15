package com.evidencecare.employee.service.dataload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.evidencecare.employee.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoadServiceImpl implements DataLoadService {

	public List<Employee> loadEmployeesFromFile() {
		ClassPathResource resourceOne = new ClassPathResource("/json/another-faulty-employees.json");
		ClassPathResource resourceTwo = new ClassPathResource("/json/correct-employees.json");
		ClassPathResource resourceThree = new ClassPathResource("/json/faulty-employees.json");
		List<Employee> items = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			items = objectMapper.readValue(resourceOne.getInputStream(), new TypeReference<List<Employee>>() {
			});
			items.addAll(objectMapper.readValue(resourceTwo.getInputStream(), new TypeReference<List<Employee>>() {
			}));
			items.addAll(objectMapper.readValue(resourceThree.getInputStream(), new TypeReference<List<Employee>>() {
			}));

			return items;
		}
		catch (IOException e) {
			log.error("error geting items : {}", e.fillInStackTrace());
			return Collections.emptyList();
		}
	}

}
