package com.codingbat.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    @NotNull(message = "worker name must not be empty")
    private String name;

    @NotNull(message = "worker phone number must not be empty")
    private String phoneNumber;

    @NotNull(message = "worker address must not be empty")
    private Integer addressId;

    @NotNull(message = "worker department must not be empty")
    private Integer departmentId;
}
