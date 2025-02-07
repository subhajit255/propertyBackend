package com.example.property.request;

import com.example.property.model.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegRequest {
    public Set<UUID> roleIds = new HashSet<>();
    @NotBlank(message = "name cannot be empty")
    @Size(min = 3, max = 50, message = "name should be within 3 to 50 characters")
    public String name;
    @NotBlank(message = "email cannot be blank")
    @Email(message = "Invalid email format")
    public String email;
    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    public String phone;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    public String password;
    @NotBlank(message = "Address cannot be blank")
    public String address;
}
