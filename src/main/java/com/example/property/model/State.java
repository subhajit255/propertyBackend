package com.example.property.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "states")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private boolean status = true;
}
