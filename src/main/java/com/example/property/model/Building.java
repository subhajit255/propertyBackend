package com.example.property.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "property_id")
    @JsonBackReference
    private Property property;
    private String name;
    private boolean status = true;
    @Column(nullable = false)

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Unit> units = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    private void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
