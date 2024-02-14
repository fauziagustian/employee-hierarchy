package com.evidencecare.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestTemplates {

	private int page;

	private int size;

	private String order;

	private String orderBy;

}
