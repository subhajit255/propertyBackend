package com.example.property.repository;

import com.example.property.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
}
