package com.example.property.model;


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

