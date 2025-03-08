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
public class BuildingUpdateRequest {
    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotNull(message = "propery cannot be empty")
    private UUID propertyId;
}
