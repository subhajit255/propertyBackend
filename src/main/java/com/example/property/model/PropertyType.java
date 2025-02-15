package com.example.property.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String image;
    private boolean status = true;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;


    @PrePersist
    private void onCreate(){
        if (this.id == null) { // Only set if id is null
            this.id = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }

}
