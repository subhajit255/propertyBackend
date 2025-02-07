package com.example.property.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UpdateUserRequest {

    @Size(min = 3, max = 50, message = "name should be within 3 to 50 characters")
    public String name;
    @Email(message = "Invalid email format")
    public String email;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    public String phone;
    public String address;
    public MultipartFile image;
}
