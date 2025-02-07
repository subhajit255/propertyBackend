package com.example.property.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginRequest {
    @NotBlank(message = "email cannot be blank")
    @Email(message = "invalid email id")
    private String email;
    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, max = 12, message = "password length should in between 8 to 12 characters")
    private String password;
}
