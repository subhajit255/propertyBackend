package com.example.property.resource;

import com.example.property.model.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResource {
    public User user;
    public String token;
}
