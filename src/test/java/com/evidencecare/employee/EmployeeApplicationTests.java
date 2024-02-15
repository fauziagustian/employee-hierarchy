package com.evidencecare.employee;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = EmployeeApplication.class)
class EmployeeApplicationTests {

	@Test()
	void contextLoads() {
		log.info("context loads text");
	}

}
