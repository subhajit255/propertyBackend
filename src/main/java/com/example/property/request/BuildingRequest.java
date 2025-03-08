package com.example.property.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequest {
    @NotNull(message = "Property ID is required")
    private UUID propertyId;
    @NotBlank(message = "building name is required")
    private String name;
}
