package com.example.property.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitAddRequest {
    @NotNull(message = "building is is needed")
    private UUID building_id;
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "details cannot be blank")
    private String details;
    @NotBlank(message = "enter no of beds")
    private int beds;
    @NotBlank(message = "enter no of bathrooms")
    private int baths;
    @NotBlank(message = "enter size")
    private int size;
    @NotBlank(message = "enter market rent")
    private int market_rent;
}
