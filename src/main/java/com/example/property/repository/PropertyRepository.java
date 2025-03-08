package com.example.property.repository;

import com.example.property.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    @Query("SELECT p FROM Property p WHERE p.user.id = :userId")
    List<Property> findPropertyByUser(@Param("userId") UUID userId);
}
