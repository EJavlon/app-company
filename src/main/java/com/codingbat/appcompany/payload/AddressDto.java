package com.codingbat.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressDto {

    @NotNull(message = "street must not be empty")
    private String street;

    @NotNull(message = "house number must not be empty")
    private Integer hauseNumber;
}
