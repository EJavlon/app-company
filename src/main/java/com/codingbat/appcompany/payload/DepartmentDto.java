package com.codingbat.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {

    @NotNull(message = "department should not be empty")
    private String name;

    @NotNull(message = "company should not be empty")
    private Integer companyId;
}
