package com.example.property.repository;

import com.example.property.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType, UUID> {
}
