package com.example.property.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {
    @NotNull(message = "name is required")
    @Size(min = 3, max = 20, message = "name must be between 3 and 20 characters")
    public String name;
}
