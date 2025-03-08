package com.example.property.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})  // Avoid deleting the parent when deleting child
    @JoinColumn(name = "property_kind_id", referencedColumnName = "id")
    private PropertyKind propertyKind;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})  // Avoid deleting the parent when deleting child
    @JoinColumn(name = "property_type_id", referencedColumnName = "id")
    private PropertyType propertyType;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String latitude;
    private String longitude;
    @Column(name = "street_address_1", columnDefinition = "TEXT")
    private String streetAddress1;
    @Column(name = "street_address_2", columnDefinition = "TEXT")
    private String streetAddress2;
    private String city;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "acc_no")
    private String accNo;
    @Column(name = "routing_no")
    private String routingNo;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonManagedReference  // prevent infinite recursion
    private User user;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Building> buildings = new ArrayList<>();
    private boolean status = true;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate(){
        if(this.id == null){
            this.id = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    private void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
