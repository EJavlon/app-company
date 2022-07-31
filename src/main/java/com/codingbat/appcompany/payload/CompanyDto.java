package com.codingbat.appcompany.payload;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {

    @NotNull(message = "the company name must not be empty")
    private String corpName;

    @NotNull(message = "the director name name must not be empty")
    private String directorName;

    @NotNull(message = "the address name must not be empty")
    private Integer addressId;
}
