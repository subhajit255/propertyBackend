package com.example.property.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String slug;
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference  // Prevent recursion
    private Set<User> users = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (slug == null || slug.isEmpty()) {
            this.slug = name.toLowerCase()
                    .replaceAll("[^a-z0-9\\s]", "") // Remove special characters
                    .replaceAll("\\s+", "-")       // Replace spaces with hyphens
                    .trim();
        }
        this.createdAt = LocalDateTime.now();
    }

    // on updation update the time
    @PreUpdate
    private void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
