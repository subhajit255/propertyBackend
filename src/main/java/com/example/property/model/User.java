package com.example.property.model;

import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;
    private LocalDateTime emailVerifiedAt;
    @Column(unique = true)
    private String phone;
    private LocalDateTime phoneVerifiedAt;
    private String image;
    private String attachment;
    private Short verificationCode;
    @Column(columnDefinition = "TEXT")
    private String address;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference  // prevent infinite recursion
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Value("${app.image.base-url}")
    @JsonIgnore  // Prevent this value from being serialized in JSON responses
    private String imageBaseUrl;

    @Value("${app.image.default}")
    @JsonIgnore
    private String defaultImage;

    // ✅ Dynamic Getter for Image URL
    public String getImageUrl() {
        return (image != null && !image.isEmpty()) ? imageBaseUrl : "";
    }

    // created at when data added
    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
    // on update the time
    @PreUpdate
    private void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}

