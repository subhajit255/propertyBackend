package com.example.property.model;

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
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String iso;
    private String name;
    @Column(name = "nicename")
    private String niceName;
    private String iso3;
    @Column(name = "numcode")
    private int numCode;
    @Column(name = "phonecode")
    private int phoneCode;
    private boolean status = true;
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<State> states = new ArrayList<>();

}
